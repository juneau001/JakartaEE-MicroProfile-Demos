/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.session;

import com.acme.acmepools.entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Juneau
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {
    @PersistenceContext(unitName = "com.acme_AcmePools_war_AcmePools-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
       /**
     * Utilize an entity graph to load the discountCode attribute of the Customer entity.
     * @return 
     */
     public List<Customer> listCustomerDiscounts(){
         EntityGraph eg = em.getEntityGraph("customerWithDiscount");
        return em.createQuery("select o from Customer o")
                .setHint("javax.persistence.fetchgraph", eg)
                .getResultList();
    }
    
}
