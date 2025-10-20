package si.savron.enarocanje.hub.common.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import si.savron.enarocanje.hub.dtos.enarocila.ZipDocumentation;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import static si.savron.enarocanje.hub.utils.HeaderUtils.CONTENT_DISPOSITION;
import static si.savron.enarocanje.hub.utils.HeaderUtils.filenameFromContentDisposition;

// Rename to data fetch service and adapt to any type of data (ZIP, TEXT, AUD, IMG, VID)

@ApplicationScoped
public class DocumentFetchService {
    private final Logger LOG = Logger.getLogger(DocumentFetchService.class);

    // TODO add documents integration with unzipping etc. apache tika
    /**
     * Sends a GET request to the specified URL and returns the response body as an InputStream.
     * This is the "get returned zip folder as input stream" logic.
     * * @param zipUrl The full URL of the service returning the ZIP file.
     * @return A Uni that emits the InputStream containing the ZIP data.
     */
    // TODO move metodto common
    public ZipDocumentation fetchZipStream(String zipUrl) {
        try {
            URL url = new URI(zipUrl).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10_000);
            connection.setReadTimeout(30_000);

            if (Response.Status.Family.familyOf(connection.getResponseCode()) == Response.Status.Family.SUCCESSFUL){
                ZipDocumentation documentation = new ZipDocumentation();
                documentation.setZipFolderName(
                        filenameFromContentDisposition(
                                connection.getHeaderField(CONTENT_DISPOSITION)
                        )
                );
                documentation.setZipStream(connection.getInputStream());
                documentation.setSource(zipUrl);
                return documentation;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            LOG.info("Error occurred");
            throw new RuntimeException(e);
        }
    }
}
