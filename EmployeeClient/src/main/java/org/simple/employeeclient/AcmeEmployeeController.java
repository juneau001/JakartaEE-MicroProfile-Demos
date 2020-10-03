package org.simple.employeeclient;

import org.simple.employeeclient.util.JsfUtil;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.simple.employeeclient.constants.ApplicationConstants;
import org.simple.employeeclient.observer.NewHire;

@Named("acmeEmployeeController")
@ViewScoped
public class AcmeEmployeeController implements java.io.Serializable {
    
//    @Inject
//    @ConfigProperty(name="SERVICE_HOST_NAME", defaultValue="localhost")
//    private String serviceHost;
//    
//    @Inject
//    @ConfigProperty(name="SERVICE_PORT")
//    private String servicePort;
//    
//    @Inject
//    @ConfigProperty(name="SERVICE_NAME")
//    private String employeeService;
    
    @Inject
    @NewHire
    private Event<EmployeeEvent> newHireEvent;
    
    private EmployeeEvent empEvent;
    
    private List<AcmeEmployee> items = null;
    private AcmeEmployee selected;
    
    private String returnMessage;
    private String returnMessage2;
    
    private String empJson;
    private String employeeServicePath;

    public AcmeEmployeeController() {
    }
    
    @PostConstruct
    public void init(){
        //employeeServicePath = "http://" + serviceHost + ":" + servicePort + "/" + employeeService + "/acmeEmployeeService";
        employeeServicePath = ApplicationConstants.REST_PATH + "acmeEmployeeService";
        Client client = ClientBuilder.newClient();
        items = client.target(employeeServicePath)
                .request("application/json")
                .get(new GenericType<List<AcmeEmployee>>() {
                }
                );
    }

    /**
     * Example of what the reactive client API looks like.
     * 
     * Incorporates with other reactive frameworks such as Guava, RxJava, etc.
     */
    public void initReactive(){
        CompletionStage<Response> response = ClientBuilder.newClient()
                .target(ApplicationConstants.REST_PATH + "acmeEmployeeService")
                .request().rx().get();
    }
    
    public void obtainEmployee(){
        Client client = ClientBuilder.newClient();
        items = client.target(ApplicationConstants.REST_PATH + "acmeEmployeeService")
                .request("application/json")
                .get(new GenericType<List<AcmeEmployee>>() {
                }
                );
    }
    
    public void loadCustomersToJSON(){
        // Implement using JSON-B
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(items);
        setReturnMessage(result);
    }
    
    
    /**
     * Uses JSON-B to convert employee record to a Java Object
     */
    public void convertEmployee(){
        System.out.println("converting to java object: " + empJson);
        AcmeEmployee emp = null;
        if(empJson != null){
            // Create employee object from empJson
            Jsonb jsonb = JsonbBuilder.create();
         
            emp = (AcmeEmployee) jsonb.fromJson(empJson, AcmeEmployee.class);
        }
        if(emp != null)
        setReturnMessage2(emp.getLastName() + " - " + emp.getFirstName());
        else 
            setReturnMessage2("You have failed");
    }
    
    /**
     * @return the items
     */
    public List<AcmeEmployee> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<AcmeEmployee> items) {
        this.items = items;
    }

    /**
     * @return the selected
     */
    public AcmeEmployee getSelected() {
        if(selected == null){
            selected = new AcmeEmployee();
        }
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(AcmeEmployee selected) {
        this.selected = selected;
    }
    
    
    public void create(){
        System.out.println("Submitting..." + selected.getLastName());
        WebTarget resource = ClientBuilder.newClient()
                .target(ApplicationConstants.REST_PATH + "acmeEmployeeService")
                .path("createEmployee");
 
            String empStart = selected.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            Form form = new Form();
            form.param("firstName", selected.getFirstName());
            form.param("lastName",selected.getLastName());
            form.param("age", selected.getAge().toString());
            form.param("startDate", selected.getStartDate().toString());
          
            Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_XML);
              //      .cookie(HttpHeaders.AUTHORIZATION, authenticationController.getSessionToken());
            Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
            if (response.getStatus() == Response.Status.CREATED.getStatusCode() || response.getStatus() == Response.Status.OK.getStatusCode()) {
                // send success message
               
                empEvent = new EmployeeEvent();
                empEvent.setFirstName(selected.getFirstName());
                empEvent.setLastName(selected.getLastName());
                newHireEvent.fireAsync(empEvent)
                    .whenComplete((event, throwable) -> {
                        if (throwable != null) {
                            System.out.println("there is an issue");
                        } else {
                             System.out.println("new employee created");
                             JsfUtil.addSuccessMessage("Successfully Created Employee");
                             init();
                        }
                    });
                        
                items = null;
                init();
            } else {
                JsfUtil.addErrorMessage("You've messed something up...employee NOT created");
            }
    }

    /**
     * @return the returnMessage
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * @param returnMessage the returnMessage to set
     */
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * @return the empJson
     */
    public String getEmpJson() {
        return empJson;
    }

    /**
     * @param empJson the empJson to set
     */
    public void setEmpJson(String empJson) {
        this.empJson = empJson;
    }

    /**
     * @return the returnMessage2
     */
    public String getReturnMessage2() {
        return returnMessage2;
    }

    /**
     * @param returnMessage2 the returnMessage2 to set
     */
    public void setReturnMessage2(String returnMessage2) {
        this.returnMessage2 = returnMessage2;
    }

    

}
