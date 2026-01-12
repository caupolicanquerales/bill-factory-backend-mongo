package com.example.facturas_sinteticas_mongo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PojoModelTests {

    @Test
    void publicityDataGeneration_gettersSetters() {
        PublicityDataGeneration m = new PublicityDataGeneration();
        m.setId("1");
        m.setData("d");
        m.setName("n");
        assertEquals("1", m.getId());
        assertEquals("d", m.getData());
        assertEquals("n", m.getName());
    }

    @Test
    void promptGenerationBill_gettersSetters() {
        PromptGenerationBill m = new PromptGenerationBill();
        m.setId("1");
        m.setPrompt("p");
        m.setName("n");
        assertEquals("1", m.getId());
        assertEquals("p", m.getPrompt());
        assertEquals("n", m.getName());
    }

    @Test
    void promptGlobalDefect_gettersSetters() {
        PromptGlobalDefect m = new PromptGlobalDefect();
        m.setId("1");
        m.setPrompt("p");
        m.setName("n");
        assertEquals("1", m.getId());
        assertEquals("p", m.getPrompt());
        assertEquals("n", m.getName());
    }
}
