package si.savron.enarocanje.hub.utils;

import java.io.IOException;
import java.io.InputStream;

public class DocumentUtils {
    public static boolean isZipStream(InputStream in) throws IOException {
        in.mark(4);
        byte[] signature = new byte[4];
        int bytesRead = in.read(signature);
        in.reset();

        if (bytesRead < 4) return false;
        // ZIP files start with 'PK' (0x50 0x4B)
        return signature[0] == 0x50 && signature[1] == 0x4B;
    }
}
