package fontys.service.resources;


import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.repository.DatabaseException;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import org.glassfish.grizzly.http.server.Request;

@Path("pharmacist")
public class PharmacistResources {
    @Context
    private UriInfo uriInfo;



    //to get all the pharmacists
    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@QueryParam("email") String email, @QueryParam("password") String password) throws DatabaseException, SQLException {
        PersistenceController persistenceController = new PersistenceController();
        Patient user = persistenceController.getUsers(email, password);
        if(user != null){
            return Response.ok(user).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid username and password.").build();
        }
    }


    @POST //POST at http://localhost:XXXX/users/
    @Path("login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context Request request, String body) throws DatabaseException, SQLException {

        final StringTokenizer tokenizer = new StringTokenizer(body, ":");
        final String email = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        PersistenceController persistenceController = new PersistenceController();
        Patient user = persistenceController.getUsers(email, password);
        if(user != null){
            request.getSession(true);
            request.setAttribute("email", email);
            System.out.println("session is below");
            System.out.println(request.getAttribute("email"));
            return Response.ok(user).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid username and password.").build();
        }
    }

    //Patients
    //To get all the patients
    @RolesAllowed("Pharmacist")
    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patients")
    public Response sayHello(){
        PersistenceController persistenceController = new PersistenceController();
        List<Patient> patients = persistenceController.getPatients();

        GenericEntity<List<Patient>> entity = new GenericEntity<>(patients) {  };
        return Response.ok(entity).build();
    }

    //to add a new patient
    @PermitAll
    @POST //POST at http://localhost:XXXX/patient/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("patient")
    public Response addPatient(Patient p) {
        PersistenceController persistenceController = new PersistenceController();

        if (!persistenceController.addPatient(p)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Patient with the given name " + p.getFirstName()+" "+p.getLastName() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + p.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.ok(p).build();
        }
    }
    // to search a patient
    @RolesAllowed({"Pharmacist", "Patient"})
    @GET //GET at http://localhost:XXXX/pharmacist/3
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patient/{id}")
    public Response getPatientById(@PathParam("id") int patientId) {

        PersistenceController persistenceController = new PersistenceController();

        Patient p = persistenceController.getPatientById(patientId);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid student number.").build();
        } else {
            return Response.ok(p).build();
        }
    }

    //to delete a patient
    @RolesAllowed("Pharmacist")
    @DELETE //DELETE at http://localhost:XXXX/pharmacist/3
    @Path("patient/{id}/delete")
    public Response deletePatient(@PathParam("id") int patientId) {
        PersistenceController persistenceController = new PersistenceController();

        if(persistenceController.deletePatient(patientId))
        {
            return Response.noContent().build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    // to update a patient
    @RolesAllowed("Pharmacist")
    @PUT //PUT at http://localhost:XXXX/users/profile/experience/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/patient")
    public Response updatePatient(Patient p) {
        PersistenceController persistenceController = new PersistenceController();

        if (persistenceController.updatePatient(p)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid id.").build();
        }
    }

    //Medicines

    //get
    @RolesAllowed("Pharmacist")
    @GET //GET at http://localhost:XXXX/pharmacist/medicines
    @Path("medicines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicines(){
        PersistenceController persistenceController = new PersistenceController();
        List<Medicine> m = persistenceController.getMedicines();

        GenericEntity<List<Medicine>> entity = new GenericEntity<>(m) {  };
        return Response.ok(entity).build();
    }
    //add
    @RolesAllowed("Pharmacist")
    @POST //http://localhost:XXXX/pharmacist/medicine
    @Path("medicine")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddMedicine(Medicine m) {
        PersistenceController persistenceController = new PersistenceController();


        if (!persistenceController.addMedicine(m)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Medicine with the given number " + m.getMedName() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + m.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    //get a medicine by id
    @RolesAllowed("Pharmacist")
    @GET //GET at http://localhost:XXXX/pharmacist/medicine/2
    @Path("medicine/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicinesById(@PathParam("id") int medID){
        PersistenceController persistenceController = new PersistenceController();

        Medicine p = persistenceController.getMedicineById(medID);

        if (p == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Medicine with number " + medID + " cannot be found").build();
        } else {
            return Response.ok(p).build();
        }
    }




    //delete
    //to delete a patient
    @RolesAllowed("Pharmacist")
    @DELETE //DELETE at http://localhost:XXXX/pharmacist/medicine/2/delete
    @Path("medicine/{id}/delete")
    public Response deleteMedicine(@PathParam("id") int medId) {
        PersistenceController persistenceController = new PersistenceController();

        if(persistenceController.deleteMedicine(medId))
        {
            return Response.noContent().build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    // update
    @RolesAllowed("Pharmacist")
    @PUT //PUT at http://localhost:XXXX/users/profile/experience/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("medicine")
    public Response updateMedicine(Medicine m) {

        PersistenceController persistenceController = new PersistenceController();

        if (persistenceController.updateMedicine(m))
        {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid experience.").build();
        }
    }

    @RolesAllowed({"Pharmacist"})
    @GET //GET at http://localhost:XXXX/pharmacist/medicine/2
    @Path("medicines/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicinesByPatientId(@PathParam("patientId") int medID){
        PersistenceController persistenceController = new PersistenceController();

        List<Medicine> list = persistenceController.getMedicineByPatientId(medID);
        GenericEntity<List<Medicine>> entity = new GenericEntity<>(list){};

        if (list == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid medicine id.").build();
        } else {
            return Response.ok(entity).build();
        }
    }

    @RolesAllowed({"Patient"})
    @GET //GET at http://localhost:XXXX/pharmacist/medicine/2
    @Path("client/medicine/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicinesByPatientIdClient(@PathParam("patientId") int medID){
        PersistenceController persistenceController = new PersistenceController();

        List<Medicine> list = persistenceController.getMedicineByPatientId(medID);
        GenericEntity<List<Medicine>> entity = new GenericEntity<>(list){};

        if (list == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid medicine id.").build();
        } else {
            return Response.ok(entity).build();
        }
    }

    //add medicine to a patient
    //add
    @RolesAllowed("Pharmacist")
    @POST //http://localhost:XXXX/pharmacist/medicine
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("patient/medicine")
    public Response addMedicineToPatient(Management m) {
        PersistenceController persistenceController = new PersistenceController();

        if (!persistenceController.addMedicineToPatient(m))
        {
            String entity =  "Medicine already exist";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + m.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    //get
    @GET //GET at http://localhost:XXXX/pharmacist/medicines
    @Path("managements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getManagements(){
        PersistenceController persistenceController = new PersistenceController();

        List<Management> m = persistenceController.getManagements();

        GenericEntity<List<Management>> entity = new GenericEntity<>(m) {  };
        return Response.ok(entity).build();
    }

    @RolesAllowed("Pharmacist")
    @PUT //PUT at http://localhost:XXXX/users/profile/experience/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("delete/patient/{patientId}/medicine/{medicineId}")
    public Response deleteMedicinePatient(@PathParam("patientId") int patientId,
                                          @PathParam("medicineId") int medicineId) throws DatabaseException, SQLException {
        PersistenceController persistenceController = new PersistenceController();

        if(persistenceController.deleteMedicinePatient(patientId, medicineId))
        {
            return Response.noContent().build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
