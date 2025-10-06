package si.savron.enarocanje.hub.clients.enarocanje;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import si.savron.enarocanje.hub.dtos.enarocila.SifObrazecDto;

@RegisterRestClient(configKey = "enarocanje")
public interface EnarocanjeClient {
    @GET
    @Path("/api/obrazec/objava/obrazecGet")
    @Produces(MediaType.APPLICATION_JSON)
    // read as plain json to simplify mapping to sifObrazec
        // (data inside seems susceptible to change)
    JsonObject obrazecGet(@QueryParam("id") Integer obrazecId);

    @GET
    @Path("/api/sifObrazec/sifObrazecGet")
    @Produces(MediaType.APPLICATION_JSON)
    SifObrazecDto sifObrazecGet(@QueryParam("idObrazec") Integer obrazecId);
}
