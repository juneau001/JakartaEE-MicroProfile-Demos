package org.simple.employeeclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("acmeEmployeeService")
@Consumes("application/json")
@Dependent
@RegisterRestClient
public interface AcmeEmployeeServiceClient {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    List<AcmeEmployee> findAll();
}
