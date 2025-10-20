package si.savron.enarocanje.hub.rest.service;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.common.services.FileStorageService;

@Path("/dev")
@IfBuildProfile("dev")
public class DevEndpoints {
    @Inject FileStorageService fileStorageService;

    @GET
    @Path("/s3/buckets")
    public Response getBuckets(){
        return  Response.ok(fileStorageService.getBuckets()).build();
    }
}
