/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.observer;

import com.acme.acmepools.entity.Customer;
import com.acme.acmepools.event.UpperCaseEvent;
import javax.enterprise.event.Observes;

/**
 *
 * @author Juneau
 */
public class UpperCaseObserver {
    public String onEvent(@Observes Customer event, UpperCaseEvent upperCaseService) {
	        return upperCaseService.changeText(event.getState());
	    } 
}
