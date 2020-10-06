/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.employeeevent.service;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author Juneau
 */
@Path("ssebroadcaster")
@Singleton
public class SseEventBroadcaster {

    @Context
    private Sse sse;

    private volatile SseBroadcaster sseBroadcaster;

    public SseEventBroadcaster() {
    }

    private synchronized SseBroadcaster getBroadcaster(Sse sse) {
        if (sseBroadcaster == null) {
            sseBroadcaster = sse.newBroadcaster();
        }
        return sseBroadcaster;
    }

    @GET
    @Path("register")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink eventSink) {
        eventSink.send(sse.newEvent("Thanks for registering!"));
        getBroadcaster(this.sse).
                register(eventSink);
    }

    @POST
    @Path("broadcast")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void broadcast(@FormParam("message") String message) {
        System.out.println("Received a message to broadcast: " + message);
        getBroadcaster(this.sse).broadcast(sse.newEventBuilder()
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .id(UUID.randomUUID().toString())
                .name("SSEEventBroadcaster Message")
                .data(String.class,message)
                .build()
        );
    }
}