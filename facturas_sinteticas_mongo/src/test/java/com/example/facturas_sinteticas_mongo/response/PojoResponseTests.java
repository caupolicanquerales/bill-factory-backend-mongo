package com.example.facturas_sinteticas_mongo.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PojoResponseTests {

    @Test
    void allGlobalDefectResponse_gettersSetters() {
        AllGlobalDefectResponse resp = new AllGlobalDefectResponse();
        resp.setDefects(List.of(new GlobalDefectResponse()));
        assertEquals(1, resp.getDefects().size());
    }

    @Test
    void allPromptGenerationResponse_gettersSetters() {
        AllPromptGenerationResponse resp = new AllPromptGenerationResponse();
        resp.setPrompts(List.of(new PromptGenerationResponse()));
        assertEquals(1, resp.getPrompts().size());
    }

    @Test
    void allSyntheticDataGenerationResponse_gettersSetters() {
        AllSyntheticDataGenerationResponse resp = new AllSyntheticDataGenerationResponse();
        resp.setSynthetics(List.of(new SyntheticDataGenerationResponse()));
        assertEquals(1, resp.getSynthetics().size());
    }

    @Test
    void dataMessage_gettersSetters() {
        DataMessage msg = new DataMessage();
        msg.setMessage("m");
        assertEquals("m", msg.getMessage());
    }
}
