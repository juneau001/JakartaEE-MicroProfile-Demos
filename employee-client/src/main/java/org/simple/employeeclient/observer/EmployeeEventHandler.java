/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.simple.employeeclient.observer;

import jakarta.enterprise.event.ObservesAsync;
import org.simple.employeeclient.EmployeeEvent;

/**
 *
 * @author Juneau
 */
public interface EmployeeEventHandler {
    
    public void notifyManager ( EmployeeEvent event);

}
