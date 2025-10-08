package si.savron.enarocanje.hub.rest.client;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.dtos.rest.NarocilaQueryRecord;
import si.savron.enarocanje.hub.services.EnarocanjeRestService;

@Path("client/v1")
public class TenderResource {
    @Inject
    EnarocanjeRestService enarocanjeRestService;

    @POST
    @Path("/narocila")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryNarocila(NarocilaQueryRecord narocilaQueryRecord){
        return Response.ok(enarocanjeRestService.queryNarocila(narocilaQueryRecord)).build();
    }

    @GET
    @IfBuildProfile("dev")
    @Path("/narocila/{obrazecId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRazpisById(
            @PathParam("obrazecId") Integer obrazecId
    ){
        return Response.ok(enarocanjeRestService.testIntegration(obrazecId)).build();
    }

    @GET
    @IfBuildProfile("dev")
    @Path("/razpisi/processed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProcessedRazpisById(
            @QueryParam("obrazecId") Integer obrazecId
    ){
        return Response.ok(enarocanjeRestService.processSifObrazec(obrazecId)).build();
    }
}
