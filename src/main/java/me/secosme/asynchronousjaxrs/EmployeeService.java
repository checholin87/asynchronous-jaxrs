package me.secosme.asynchronousjaxrs;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeService {

    @Inject
    private EmployeeBusiness business;

    @Inject
    private TaskBusiness tasks;

    @Path("/async")
    @GET
    public void async(@Suspended AsyncResponse response) {
        System.out.println("--- before async ---");
        business.list(response::resume);
        System.out.println("--- after async ---");
    }

    @Path("/sync")
    @GET
    public List<EmployeeDTO> sync() {
        return business.sync();
    }

    @Path("/task-async")
    @GET
    public String task() {
        tasks.execute();
        return "OK!";
    }
}
