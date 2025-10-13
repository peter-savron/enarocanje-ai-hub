package si.savron.enarocanje.hub.utils;

import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Contains methods to convert files from byte formats like PDF, DOCX etc.
 * to text formats like MD, CSV etc.
 */
public class FileConversionUtil {
    private static final Tika tika = new Tika();

    public static StringWriter fileToText (InputStream fileContent) {
        try {
            StringWriter writer = new StringWriter();
            tika.parse(fileContent).transferTo(writer);
            return writer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
