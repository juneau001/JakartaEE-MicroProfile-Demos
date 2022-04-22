
package org.employeeservice;

import org.employeeservice.entity.AcmeEmployee;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // Find max CUSTOMER_ID and add one
        AcmeEmployee maxEmp =  Collections.max(findAll(), Comparator.comparing(c -> c.getId()));
        AcmeEmployee emp = new AcmeEmployee();
        try {
            
            emp.setId(maxEmp.getId() + 1);
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
        System.out.println("the current employee count: " + empCount);
        return empCount.intValue();
    }
    
    public List<AcmeEmployee> performFindByEmployeeStream(AcmeEmployee employee){
        Stream<AcmeEmployee> empStream = em.createQuery("select object(o) from AcmeEmployee o")
                    .getResultStream();
                     
        return empStream.filter(
            emp -> employee.equals(emp.getId()))
	   .collect(Collectors.toList());
        
    }

}
