package si.savron.enarocanje.hub.common.fetching;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;

public class ZipEntryReader implements Iterable<ZipArchiveEntry>, AutoCloseable {

        private final ZipArchiveInputStream zis;
        private ZipArchiveEntry nextEntry;
        private ByteArrayOutputStream currentFile;
        private boolean finished;

        public ZipEntryReader(InputStream in) {
            // Always buffer the source InputStream for performance
            this.zis = new ZipArchiveInputStream(in, StandardCharsets.UTF_8.name());
        }

        @Override
        public Iterator<ZipArchiveEntry> iterator() {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    if (finished) return false;
                    if (nextEntry != null) return true;

                    try {
                        // skip directories
                        do {
                            nextEntry = zis.getNextEntry();
                            if (nextEntry == null) {
                                finished = true;
                                zis.close();
                                return false;
                            }
                        } while (nextEntry.isDirectory());
                        return true;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }

                @Override
                public ZipArchiveEntry next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    ZipArchiveEntry current = nextEntry;
                    readCurrentFile();
                    nextEntry = null;
                    return current;
                }
            };
        }

    public Stream<ZipEntry> stream() {
        Spliterator<ZipEntry> spliterator =
                Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED | Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false)
                .onClose(() -> {
                    try {
                        close();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    @Override
    public void close() throws IOException {
        zis.close();
    }

    public ByteArrayOutputStream getCurrentFile(){
            return currentFile;
    }

    private void readCurrentFile() {
        currentFile = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;

        try {
            while ((len = zis.read(buffer)) > 0) {
                currentFile.write(buffer, 0, len);
            }
        } catch (IOException e) {
            currentFile = null;
            throw new RuntimeException(e);
        }
    }
}
