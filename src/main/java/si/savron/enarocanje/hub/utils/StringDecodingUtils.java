package si.savron.enarocanje.hub.utils;

import static java.util.Optional.*;

import org.jboss.logging.Logger;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringDecodingUtils {
    private static final UniversalDetector universalDetector = new UniversalDetector();
    private static final Logger LOG = Logger.getLogger(StringDecodingUtils.class);

    public static String fixNonUtf8Encoding(String utf8String){
        byte[] originalBytes = utf8String.getBytes(StandardCharsets.UTF_8);

        universalDetector.handleData(originalBytes);
        universalDetector.dataEnd();
        String detectedCharset = universalDetector.getDetectedCharset();
        LOG.info("Detected charset: " + detectedCharset);
        universalDetector.reset();

        try {
            return new String(originalBytes, detectedCharset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fixNonUtf8Encoding(byte[] rawString){
        universalDetector.handleData(rawString);
        universalDetector.dataEnd();
        String detectedCharset = universalDetector.getDetectedCharset();
        LOG.info("Detected charset: " + detectedCharset);
        universalDetector.reset();

        try {
            return new String(rawString,
                    ofNullable(detectedCharset).orElse(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
