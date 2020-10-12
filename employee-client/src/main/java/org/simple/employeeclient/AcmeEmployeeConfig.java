/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Juneau
 */
@ApplicationScoped
public class AcmeEmployeeConfig {
    
    @Inject
    @ConfigProperty(name="server.host.name", defaultValue="localhost")
    private String serviceHost;
    
    @Inject
    @ConfigProperty(name="service.port")
    private String servicePort;
    
    @Inject
    @ConfigProperty(name="service.name")
    private String employeeService;

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
    
}
