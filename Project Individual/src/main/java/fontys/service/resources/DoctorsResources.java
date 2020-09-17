package fontys.service.resources;

import fontys.service.model.Doctors;
import fontys.service.repository.FakeData;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/data")
public class DoctorsResources {
    private final FakeData fakeData = FakeData.getInstance();
    
    @GET //GET at http://localhost:XXXX/doctors/
    @Path("/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(){
        List<Doctors> doctors = fakeData.getDoctorsList();

        GenericEntity<List<Doctors>> entity = new GenericEntity<>(doctors) {  };
        return Response.ok(entity).build();
    }

    
}
