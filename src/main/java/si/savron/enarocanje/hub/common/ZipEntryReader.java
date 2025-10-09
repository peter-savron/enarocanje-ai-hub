package si.savron.enarocanje.hub.common;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;

public class ZipEntryReader implements Iterable<ZipEntry>, AutoCloseable {

        private final ZipArchiveInputStream zis;
        private ZipEntry nextEntry;
        private boolean finished;

        public ZipEntryReader(InputStream in) {
            // Always buffer the source InputStream for performance
            this.zis = new ZipArchiveInputStream(in, StandardCharsets.UTF_8.name());
        }

        @Override
        public Iterator<ZipEntry> iterator() {
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
                public ZipEntry next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    ZipEntry current = nextEntry;
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
}
