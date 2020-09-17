package fontys.service.resources;


import fontys.service.model.Patients;
import fontys.service.repository.FakeData;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;


@Path("/patients")
public class PatientResources {
    private final FakeData fakeData = FakeData.getInstance();
    @Context
    private UriInfo uriInfo;

    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(){
        List<Patients> patients = fakeData.getPatientsList();

        GenericEntity<List<Patients>> entity = new GenericEntity<>(patients) {  };
        return Response.ok(entity).build();
    }

    @DELETE //DELETE at http://localhost:XXXX/patients/John
    @Path("{id}")
    public Response deletePatient(@PathParam("id") int patientId) {
        fakeData.deletePatient(patientId);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).
        return Response.noContent().build();
    }

   /* @POST //POST at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(){

        String result = "It works";
        return Response.ok().build();
    }*/
    @POST //POST at http://localhost:XXXX/students/
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudent(Patients p) {
        if (!fakeData.addPatient(p)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Patient with the given number " + p.getPatientId() + " already exists.";
           // throw new Exception(Response.Status.CONFLICT, "This topic already exists");
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + p.getPatientId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }
}
