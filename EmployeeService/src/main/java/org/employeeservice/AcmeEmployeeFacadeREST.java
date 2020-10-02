/*
 * Fermi National Accelerator Laboratory
 * Services, GIS & Real Property
 * www.fnal.gov
 */
package org.employeeservice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.employeeservice.entity.AcmeEmployee;

/**
 *
 * @author Josh Juneau
 */
@Stateless
@Path("acmeEmployeeService")
public class AcmeEmployeeFacadeREST {

    @PersistenceContext(unitName = "EmployeeService_1.0PU")
    private EntityManager em;

    @GET
    @Path("findByLast/{lastName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public AcmeEmployee findByLast(@PathParam("lastName") String lastName) {
        AcmeEmployee emp = null;
        try {
            emp = (AcmeEmployee) em.createQuery("select object(o) from AcmeEmployee o "
                    + "where o.lastName = :lastName")
                    .setParameter("lastName", lastName)
                    .getSingleResult();

        } catch (NoResultException ex) {
            System.out.println("Error: " + ex);
        }

        return emp;

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AcmeEmployee> findAll() {
        List<AcmeEmployee> employees = null;
        try {
            employees = em.createQuery("select object(o) from AcmeEmployee o")
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("Error: " + ex);
        }
        return employees;

    }
 
    @POST
    @Path("createEmployee")
    @Produces(MediaType.APPLICATION_XML)
    public Response createEmployee(@FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("age") String age,
            @FormParam("startDate") String startDate) {
        boolean success = false;
        AcmeEmployee emp = new AcmeEmployee();
        try {
            emp.setId(count() + 1);
            emp.setFirstName(firstName.toUpperCase());
            emp.setLastName(lastName.toUpperCase());
            emp.setAge(Integer.valueOf(age));
            emp.setStatus("NEW HIRE");
            LocalDate empStart = LocalDate.parse(startDate);
            emp.setStartDate(empStart);
            em.persist(emp);
            success = true;
        } catch (Exception e) {
            System.out.println("AcmeEmployeeFacadeREST ERROR: " + e);
        }
        if(success){
            return Response.ok().entity("SUCCESS").build();
        } else {
            return Response.serverError().entity("ERROR").build();
        }
        
    }
    
    private int count(){
        Long empCount =  (Long) em.createQuery("select count(o) from AcmeEmployee o").getSingleResult();
        return empCount.intValue();
    }

}
