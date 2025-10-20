package si.savron.enarocanje.hub.clients.enarocanje;

import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import si.savron.enarocanje.hub.dtos.enarocila.NarocilaQueryRequestDto;
import si.savron.enarocanje.hub.dtos.enarocila.NarocilaQueryResponseDto;
import si.savron.enarocanje.hub.dtos.enarocila.SifObrazecDto;

import java.io.InputStream;

@RegisterRestClient(configKey = "enarocanje")
public interface EnarocanjeClient {

    @POST
    @Path("/api/obrazec/objava/obrazecGetGrid")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    NarocilaQueryResponseDto queryNarocila(NarocilaQueryRequestDto narocilaQueryRequestDto, @HeaderParam("Content-Type") String contentType);

    /**
     * Unused because using directly connection
     * @param documentId
     * @return
     */
    @GET
    @Path("/api/datoteka/get")
    InputStream getDocument(@QueryParam("id") String documentId);

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
