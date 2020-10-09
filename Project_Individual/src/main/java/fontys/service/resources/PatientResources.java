package fontys.service.resources;


import fontys.service.model.Patient;
import fontys.service.repository.FakeData;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


@Path("patients")
public class PatientResources {
    private final FakeData fakeData = FakeData.getInstance();
    @Context
    private UriInfo uriInfo;

    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(){

        List<Patient> patients = fakeData.getPatientsList();

        GenericEntity<List<Patient>> entity = new GenericEntity<>(patients) {  };

        return Response.ok(entity).build();
    }
}
