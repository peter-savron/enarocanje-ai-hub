package si.savron.enarocanje.hub.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderUtils {
    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    public static String filenameFromContentDisposition(String contentDisposition){
        if (contentDisposition == null) {
            return null;
        }

        // Pattern matches: filename*= [charset] '' [value]
        Pattern filenameStarPattern = Pattern.compile("filename\\*=([a-z0-9-]+)''(.+?)(?:;|$)", Pattern.CASE_INSENSITIVE);
        Matcher filenameStarMatcher = filenameStarPattern.matcher(contentDisposition);

        if (filenameStarMatcher.find()) {
            String charsetName = filenameStarMatcher.group(1).trim();
            String urlEncodedFilename = filenameStarMatcher.group(2);
            return URLDecoder.decode(urlEncodedFilename, StandardCharsets.UTF_8);
        }

        Pattern filenamePattern = Pattern.compile("filename=\"?([^\"]+)\"?(?:;|$)", Pattern.CASE_INSENSITIVE);
        Matcher filenameMatcher = filenamePattern.matcher(contentDisposition);

        if (filenameMatcher.find()) {
            return filenameMatcher.group(1).trim();
        }

        return null;
    }
}
