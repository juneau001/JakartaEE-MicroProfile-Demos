/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.employeeevent.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import org.employeeevent.AcmeEmployeeConfig;
import org.employeeevent.constants.ApplicationConstants;
import org.employeeevent.model.AcmeEmployee;

/**
 *
 * @author Juneau
 */
@Path("sse")
@RequestScoped
public class SseEventResource {
    
    @Inject
    AcmeEmployeeConfig config;

    @Resource(name = "DefaultManagedExecutorService")
    ManagedExecutorService executor;

    private List<AcmeEmployee> items = null;
    private String employeeServicePath;

    public SseEventResource() {

    }

    @PostConstruct
    public void init() {
        //employeeServicePath = "http://" + serviceHost + ":" + servicePort + "/" + employeeService + "/acmeEmployeeService";
        employeeServicePath = "http://" + config.getServiceHost() + ":" + config.getServicePort() + "/" + config.getEmployeeService() + "/acmeEmployeeService";
        Client client = ClientBuilder.newClient();
        items = client.target(employeeServicePath)
                .request("application/json")
                .get(new GenericType<List<AcmeEmployee>>() {
                }
                );
    }

    @GET
    @Path("employees")
    @Produces("text/event-stream")
    public void send(@Context SseEventSink eventSink,
            @Context Sse sse) {

        for (AcmeEmployee emp : items) {
            CompletableFuture
                    .runAsync(() -> {
                        System.out.println("Sending event:" + emp.getFirstName() + " " + emp.getLastName());
                        OutboundSseEvent evt = sse.newEventBuilder()
                                .comment("Employee Name: " + emp.getFirstName() + " " + emp.getLastName())
                                .name("Employee")
                                .data(emp)
                                .build();
                        eventSink.send(evt);
                    })
                    .whenComplete((r, ex) -> eventSink.close());

        }
    }

}
