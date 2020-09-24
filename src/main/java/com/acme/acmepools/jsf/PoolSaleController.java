/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.jsf;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.acme.acmepools.event.SaleEvent;
import com.acme.acmepools.qualifier.OnlineSale;


/**
 *
 * @author Juneau
 */
@Named
@ViewScoped
public class PoolSaleController implements java.io.Serializable {

    @Inject
    @OnlineSale
    private Event<SaleEvent> onlineSaleEvent;

    private SaleEvent currentEvent;

    public PoolSaleController() {

    }

    /**
     * Fires synchronous CDI event BookEvent.
     */
    public void onlineSaleAction() {
        List notifyList = new ArrayList();
        currentEvent.setNotifyList(notifyList);
        onlineSaleEvent.fire(currentEvent);
        currentEvent = null;
    }

    /**
     * Fires asynchronous CDI event BookEvent.
     */
    public void storeSaleAction() {
        List notifyList = new ArrayList();
        currentEvent.setNotifyList(notifyList);
        onlineSaleEvent.fireAsync(currentEvent)
                .whenComplete((event, throwable) -> {
                    if (throwable != null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, "FAIL", "Error has occurred " + throwable.getMessage()));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO, "SUCCESS", "Successful Brick-and-Mortor Store Sale Processing..."));
                        currentEvent = null;
                    }
                });
    }

    /**
     * @return the currentEvent
     */
    public SaleEvent getCurrentEvent() {
        if(currentEvent == null){
            currentEvent = new SaleEvent();
        }
        return currentEvent;
    }

    /**
     * @param currentEvent the currentEvent to set
     */
    public void setCurrentEvent(SaleEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

}
