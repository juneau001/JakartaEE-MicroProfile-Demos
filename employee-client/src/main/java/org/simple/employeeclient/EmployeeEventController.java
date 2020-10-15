/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.simple.employeeclient.constants.ApplicationConstants;

/**
 *
 * @author Juneau
 */
@Named
@ViewScoped
public class EmployeeEventController implements java.io.Serializable {
    
    @Inject
    AcmeEmployeeConfig config;
    
    private Client client;
    
    private String eventResponse;
    
    private String message;

    @PostConstruct
    public void init(){
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    public void destroy(){
        client.close();
    }
    
    
    public void sendEvent() throws URISyntaxException, InterruptedException, ExecutionException {
        String eventService = "http://" + config.getServiceHost() + ":" + config.getServicePort() + "/" + config.getEventService();
        WebTarget target = client
                .target(eventService)
                .path("ssebroadcaster/broadcast");
        System.out.println("UrL:" + target.getUri());
        
        Form form = new Form();
        form.param("message", message);
        
        Future<String> response = 
        target.request(MediaType.APPLICATION_FORM_URLENCODED)
           .accept(MediaType.TEXT_PLAIN)
           .buildPost(Entity.form(form)).submit(String.class);
       
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sse Endpoint: " + 
                response.get()));
        
        message = null;

        
    }
    
    public void register() throws URISyntaxException, InterruptedException {
        WebTarget target = client
                .target(ApplicationConstants.EVENT_REST_PATH)
                .path("ssebroadcaster/register");
        
        Response response = 
        target.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get();
        System.out.println(response.getStatus());
        System.out.println(target.getUri());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Sse Endpoint: " + 
                message));
        
        message = null;

        
    }

    /**
     * @return the eventResponse
     */
    public String getEventResponse() {
        return eventResponse;
    }

    /**
     * @param eventResponse the eventResponse to set
     */
    public void setEventResponse(String eventResponse) {
        this.eventResponse = eventResponse;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
