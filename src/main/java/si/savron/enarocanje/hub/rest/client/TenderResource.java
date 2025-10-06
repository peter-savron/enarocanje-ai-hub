package si.savron.enarocanje.hub.rest.client;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.services.EnarocanjeRestService;

@Path("client/v1")
public class TenderResource {
    @Inject
    EnarocanjeRestService enarocanjeRestService;

    @GET
    @IfBuildProfile("dev")
    @Path("/razpisi")
    public Response getRazpisById(
            @QueryParam("obrazecId") Integer obrazecId
    ){
        return Response.ok(enarocanjeRestService.testIntegration(obrazecId)).build();
    }

    @GET
    @IfBuildProfile("dev")
    @Path("/razpisi/processed")
    public Response getProcessedRazpisById(
            @QueryParam("obrazecId") Integer obrazecId
    ){
        return Response.ok(enarocanjeRestService.processSifObrazec(obrazecId)).build();
    }
}
