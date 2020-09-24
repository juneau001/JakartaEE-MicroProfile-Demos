/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.session;

import com.acme.acmepools.entity.Customer;
import com.acme.acmepools.entity.Pool;
import com.acme.acmepools.entity.PoolCustomerObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juneau
 */
@Stateless
public class PoolFacade extends AbstractFacade<Pool> {

    @PersistenceContext(unitName = "com.acme_AcmePools_war_AcmePools-1.0-SNAPSHOTPU")
    private EntityManager em;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.acme_AcmePools_war_AcmePools-1.0-SNAPSHOTPU");

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PoolFacade() {
        super(Pool.class);
    }

    /**
     * Returns a List of Pool objects for a specified customer by calling upon a
     * named stored procedure query, and passing a customer ID.
     *
     * @param customerId
     * @return
     */
    public List<Pool> obtainCustomerPools(Integer customerId) {

        return em.createNamedStoredProcedureQuery("ObtainCustomerPoolInformation")
                .setParameter("customerId", customerId).getResultList();
    }

    /**
     * Returns a List of Pool objects for a given customer.
     *
     * @param customerId
     * @return List
     */
    public List obtainCustomerPoolsUnnamed(Integer customerId) {
        StoredProcedureQuery spq = em.createStoredProcedureQuery("OBTAIN_CUSTOMER_POOL_INFORMATION");
        spq.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        spq.setParameter(1, customerId);
        return spq.getResultList();
    }

    /**
     * Return a Pool object given a shape string.
     *
     * @param shape
     * @return List
     */
    public List<Pool> obtainPoolByShape(String shape) {
        Query qry = em.createQuery("select p from Pool p "
                + "where p.shape = :shape");
        qry.setParameter("shape", shape);
        emf.addNamedQuery("obtainPoolByShape", qry);

        // In the named query, we need to set the parameter again
        return em.createNamedQuery("obtainPoolByShape")
                .setParameter("shape", shape).getResultList();
    }

    
    
    /**
     * Make use of downcasting to return all pools that are above ground.
     * @return 
     */
    public  Long obtainAboveGroundCounts() {
        return (Long) em.createQuery("select count(current) from Customer c join treat(c.pool as AboveGround) current")
            .getSingleResult();
}
    

}
