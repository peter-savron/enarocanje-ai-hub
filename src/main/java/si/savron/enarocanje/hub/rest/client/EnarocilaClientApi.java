package si.savron.enarocanje.hub.rest.client;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.common.services.DocumentFetchService;
import si.savron.enarocanje.hub.dtos.rest.NarocilaQueryRecord;
import si.savron.enarocanje.hub.services.EnarocanjeRestService;

@Path("client/v1")
@Consumes(MediaType.APPLICATION_JSON)
public class EnarocilaClientApi {
    @Inject
    EnarocanjeRestService enarocanjeRestService;
    @Inject
    DocumentFetchService documentFetchService;

    @POST
    @Path("/narocila")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryNarocila(NarocilaQueryRecord narocilaQueryRecord){
        return Response.ok(enarocanjeRestService.queryNarocila(narocilaQueryRecord)).build();
    }

    @POST
    @IfBuildProfile("dev")
    @Path("/documents")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getDocuments(String url) throws Exception{
        return Response.ok(documentFetchService.fetchZipStream(url).getZipFolderName()).build();
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
