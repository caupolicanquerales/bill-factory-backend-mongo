package com.example.facturas_sinteticas_mongo.service.utils;

import com.example.facturas_sinteticas_mongo.response.DataMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.ServerSentEvent;

import static org.junit.jupiter.api.Assertions.*;

class ConverterUtilTest {

    @Test
    void setDataMessage_setsMessage() {
        DataMessage msg = ConverterUtil.setDataMessage("hello");
        assertEquals("hello", msg.getMessage());
    }

    @Test
    void setServerSentEvent_buildsEventWithData() {
        DataMessage msg = new DataMessage();
        msg.setMessage("payload");
        ServerSentEvent<DataMessage> sse = ConverterUtil.setServerSentEvent(msg, "my-event");

        assertEquals("my-event", sse.event());
        assertEquals("1", sse.id());
        assertNotNull(sse.data());
        assertEquals("payload", sse.data().getMessage());
    }
}
