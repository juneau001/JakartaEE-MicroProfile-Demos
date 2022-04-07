package org.simple.employeeclient;

import org.simple.employeeclient.constants.ApplicationConstants;
import org.simple.employeeclient.observer.NewHire;
import org.simple.employeeclient.util.JsfUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonPointer;
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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Named("acmeEmployeeController")
@ViewScoped
public class AcmeEmployeeController implements java.io.Serializable {

    @Inject
    AcmeEmployeeConfig config;

    @Inject
    @NewHire
    private Event<EmployeeEvent> employeeEvent;

    private List<AcmeEmployee> items = null;
    private AcmeEmployee selected;

    private String returnMessage;
    private String returnMessage2;

    private String empJson;
    private String employeeServicePath;
    
    private String lastSearchText;
    private String replacementString;
    private String searchResult;

//    @Inject
//    @RestClient
//    private AcmeEmployeeServiceClient acmeEmployeeServiceClient;

    public AcmeEmployeeController() {
    }

    @PostConstruct
    public void init() {
        employeeServicePath = "http://" + config.getServiceHost() + ":" + config.getServicePort() + "/" + config.getEmployeeService() + "/acmeEmployeeService";
        //employeeServicePath = ApplicationConstants.REST_PATH + "acmeEmployeeService";
        Client client = ClientBuilder.newClient();
        items = client.target(employeeServicePath)
                .request("application/json")
                .get(new GenericType<List<AcmeEmployee>>() {
                }
                );
//        items = acmeEmployeeServiceClient.findAll();
    }

    /**
     * Example of what the reactive client API looks like.
     *
     * Incorporates with other reactive frameworks such as Guava, RxJava, etc.
     */
    public void initReactive() {
        CompletionStage<Response> response = ClientBuilder.newClient()
                .target(ApplicationConstants.REST_PATH + "acmeEmployeeService")
                .request().rx().get();
    }

    public void obtainEmployee() {
        Client client = ClientBuilder.newClient();
        items = client.target(ApplicationConstants.REST_PATH + "acmeEmployeeService")
                .request("application/json")
                .get(new GenericType<List<AcmeEmployee>>() {
                }
                );
    }

    public void loadCustomersToJSON() {
        // Implement using JSON-B
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(items);
        setReturnMessage(result);
    }

    /**
     * Uses JSON-B to convert employee record to a Java Object
     */
    public void convertEmployee() {
        System.out.println("converting to java object: " + empJson);
        AcmeEmployee emp = null;
        if (empJson != null) {
            // Create employee object from empJson
            Jsonb jsonb = JsonbBuilder.create();

            emp = jsonb.fromJson(empJson, AcmeEmployee.class);
        }
        if (emp != null) {
            setReturnMessage2(emp.getLastName() + " - " + emp.getFirstName());
        } else {
            setReturnMessage2("You have failed");
        }
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
        if (selected == null) {
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

    public void create() {
        System.out.println("Submitting..." + selected.getLastName());
        WebTarget resource = ClientBuilder.newClient()
                .target(employeeServicePath)
                .path("createEmployee");

        Form form = new Form();
        form.param("firstName", selected.getFirstName());
        form.param("lastName", selected.getLastName());
        form.param("age", selected.getAge().toString());
        form.param("startDate", selected.getStartDate().toString());

        Invocation.Builder invocationBuilder = resource.request(MediaType.APPLICATION_XML);
        //      .cookie(HttpHeaders.AUTHORIZATION, authenticationController.getSessionToken());
        Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode() || response.getStatus() == Response.Status.OK.getStatusCode()) {
            // send success message

            EmployeeEvent payload = new EmployeeEvent();
            payload.setFirstName(selected.getFirstName());
            payload.setLastName(selected.getLastName());
            // In the circumstance where you did not wish to use injection of qualifier:
            //  Event<EmployeeEvent> newHireEvent = this.employeeEvent.select(new AnnotationLiteral<NewHire>(){});
            employeeEvent.fireAsync(payload)
                    .whenCompleteAsync((event, throwable) -> {
                        if (throwable != null) {
                            System.out.println("there is an issue");
                        } else {
                            System.out.println("new employee created");
                            init();
                        }
                    });
            JsfUtil.addSuccessMessage("Successfully Created Employee");
            items = null;
            init();
        } else {
            JsfUtil.addErrorMessage("You've messed something up...employee NOT created");
        }
    }

    /**
     * Creates a JSON object using a JsonObjectBuilder
     *
     * @return
     */
    public JsonArray buildEmployeesJson() {
        List<AcmeEmployee> employees = items;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArray results = null;
        
        for (AcmeEmployee employee : employees) {
            builder.add(
                    Json.createObjectBuilder()
                        .add("id", employee.getId())
                        .add("firstName", employee.getFirstName())
                        .add("lastName", employee.getLastName())
                        .add("age", employee.getAge())
                        .add("jobid", employee.getJobId()!=null?employee.getJobId():0)
                        .add("startDate", employee.getStartDate().toString())
                        .add("status", employee.getStatus()));
            

        }
        results = builder.build();
        return results;
    }

    /**
     * Create a JSON document from a JsonObject.
     */
    public void writeJson() {
        try {
            JsonArray jsonObjects = buildEmployeesJson();

            try (javax.json.JsonWriter jsonWriter = Json.createWriter(new FileWriter("Employees.json"))) {
                jsonWriter.writeArray(jsonObjects);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "JSON Built",
                    "JSON Built"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Utilizes the text in the lastSearchText field to 
     * create a JsonPointer.  Once obtained, replaces the
     * found last name with an arbitrary value.
     */
    public void findEmployeeByLast() {
        setSearchResult(null);
        String text = "/" + this.lastSearchText;
        JsonObject json = Json.createObjectBuilder().build();
        JsonArray empArray = buildEmployeesJson();
        if (lastSearchText != null && empArray != null) {
            JsonPointer pointer = Json.createPointer(text);
            // Replace a value
            JsonArray array = (JsonArray) pointer.replace(empArray, Json.createValue(replacementString != null?replacementString:"JsonMaster"));
            setSearchResult(array.toString());
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

    /**
     * @return the lastSearchText
     */
    public String getLastSearchText() {
        return lastSearchText;
    }

    /**
     * @param lastSearchText the lastSearchText to set
     */
    public void setLastSearchText(String lastSearchText) {
        this.lastSearchText = lastSearchText;
    }

    /**
     * @return the searchResult
     */
    public String getSearchResult() {
        return searchResult;
    }

    /**
     * @param searchResult the searchResult to set
     */
    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }

    /**
     * @return the replacementString
     */
    public String getReplacementString() {
        return replacementString;
    }

    /**
     * @param replacementString the replacementString to set
     */
    public void setReplacementString(String replacementString) {
        this.replacementString = replacementString;
    }

}
