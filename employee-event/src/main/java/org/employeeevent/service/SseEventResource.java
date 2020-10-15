/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.employeeevent.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
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
