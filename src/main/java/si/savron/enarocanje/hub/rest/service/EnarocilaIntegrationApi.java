package si.savron.enarocanje.hub.rest.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import si.savron.enarocanje.hub.services.EnarocanjeReplicationService;

@Path("/integration/v1/narocila")
public class EnarocilaIntegrationApi {
    @Inject EnarocanjeReplicationService enarocanjeReplicationService;
    /**
     * Searches for a single narocilo first in DB then fetches data from online
     * @param narociloId
     * @return
     */
    @POST
    @Path("/{narociloId}")
    public Response postNarocilo(@PathParam("narociloId") Integer narociloId){
        return Response.ok(enarocanjeReplicationService.replicateNarocilo(narociloId)).build(); // get data, obdelaj data, store data, extra obdelaj z vektorizacijo chunke
    }

    /**
     * Searches new data from last search onward
     * @return
     */
    @POST
    public Response postNewNarocila(){
        return Response.ok().build(); // isto kot zgoraj samo za vec entitet hkrati
    }
}
