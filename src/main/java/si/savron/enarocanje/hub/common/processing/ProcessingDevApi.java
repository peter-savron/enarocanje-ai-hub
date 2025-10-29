package si.savron.enarocanje.hub.common.processing;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@IfBuildProfile("dev")
@Path("/dev/processing")
public class ProcessingDevApi {
    @Inject TextSplittingService textSplittingService;

    @POST
    @Path("/split-plaintext")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response splitPlaintext(String plaintext) {
        return Response.ok(
                textSplittingService.splitPlaintext(plaintext)
        ).build();
    }

}
