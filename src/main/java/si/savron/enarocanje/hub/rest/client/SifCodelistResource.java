package si.savron.enarocanje.hub.rest.client;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.services.SifCodelistService;

@Path("client/v1/codelists")
public class SifCodelistResource {
    @Inject SifCodelistService sifCodelistService;

    @Path("/cpv")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBaseCpvs(){
        return Response.ok(sifCodelistService.getBaseCpvCodelist()).build();
    }

    @Path("/cpv/{cpvId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChildren(@PathParam("cpvId") Integer cpvId){
        return Response.ok(sifCodelistService.getCpvCodelistChildren(cpvId)).build();
    }

    @Path("/narociloVrsta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBaseNarociloVrsta(){
        return Response.ok(sifCodelistService.getNarociloVrstaCodelist()).build();
    }

    @Path("/postopekFaza")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBasePostopekFaza(){
        return Response.ok(sifCodelistService.getPostopekFazaCodelist()).build();
    }
}
