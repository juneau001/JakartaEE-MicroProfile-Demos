/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import org.simple.employeeclient.constants.ApplicationConstants;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;

@Named("sseClient")
@RequestScoped
public class SseClient {

    @Inject
    AcmeEmployeeConfig config;
    
    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }


    public void listen() {
        String eventService = "http://" + config.getServiceHost() + ":" + config.getServicePort() + "/" + config.getEventService();
        WebTarget target = client.target(eventService).path("sse/employees");
        try (SseEventSource source = SseEventSource.target(target).build()) {
            source.register(System.out::println);
            source.open();
            Thread.sleep(250); // Consume events for 250 ms
            source.close();
        } catch (InterruptedException e) {
            
        }
    }
}