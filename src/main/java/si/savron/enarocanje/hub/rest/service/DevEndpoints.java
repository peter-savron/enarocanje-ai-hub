package si.savron.enarocanje.hub.rest.service;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.common.deprecated.ChunkingService;
import si.savron.enarocanje.hub.common.fetching.FileStorageService;

@Path("/dev")
@IfBuildProfile("dev")
public class DevEndpoints {
    @Inject FileStorageService fileStorageService;
    @Inject ChunkingService chunkingService;

    @GET
    @Path("/s3/buckets")
    public Response getBuckets(){
        return  Response.ok(fileStorageService.getBuckets()).build();
    }

    @POST
    @Path(("/chunk"))
    @Consumes(MediaType.TEXT_PLAIN)
    public Response split(String text) {
        return Response.ok(chunkingService.simpleChunking(text)).build();
    }
}
