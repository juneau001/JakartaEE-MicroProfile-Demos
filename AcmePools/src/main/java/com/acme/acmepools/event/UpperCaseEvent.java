/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.event;

/**
 * Event to change text to uppercase.
 * @author Juneau
 */
public class UpperCaseEvent {
    public String changeText(String text){
        return text.toUpperCase();
    }
}
