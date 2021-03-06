/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import org.simple.employeeclient.constants.ApplicationConstants;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

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