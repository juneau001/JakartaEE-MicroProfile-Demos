/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.employeeevent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author Juneau
 */
@ApplicationScoped
public class AcmeEmployeeConfig {
    
    @Inject
    @ConfigProperty(name="service.host.name", defaultValue="localhost")
    private String serviceHost;
    
    @Inject
    @ConfigProperty(name="service.port")
    private String servicePort;
    
    @Inject
    @ConfigProperty(name="service.name")
    private String employeeService;
    
    @Inject
    @ConfigProperty(name="event.service")
    private String eventService;

    /**
     * @return the serviceHost
     */
    public String getServiceHost() {
        return serviceHost;
    }

    /**
     * @param serviceHost the serviceHost to set
     */
    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    /**
     * @return the servicePort
     */
    public String getServicePort() {
        return servicePort;
    }

    /**
     * @param servicePort the servicePort to set
     */
    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    /**
     * @return the employeeService
     */
    public String getEmployeeService() {
        return employeeService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(String employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @return the eventService
     */
    public String getEventService() {
        return eventService;
    }

    /**
     * @param eventService the eventService to set
     */
    public void setEventService(String eventService) {
        this.eventService = eventService;
    }
    
}
