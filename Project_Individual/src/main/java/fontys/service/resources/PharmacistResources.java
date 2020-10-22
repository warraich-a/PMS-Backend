package fontys.service.resources;


import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.model.Pharmacist;
import fontys.service.repository.FakeData;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Path("pharmacist")
public class PharmacistResources {
    private final FakeData fakeData = FakeData.getInstance();
    @Context
    private UriInfo uriInfo;


    //to get all the pharmacists
    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPharmacists(){
        List<Pharmacist> pharmacists = fakeData.getPharmacistList();

        GenericEntity<List<Pharmacist>> entity = new GenericEntity<>(pharmacists) {  };
        return Response.ok(entity).build();
    }

    //get
    @GET //GET at http://localhost:XXXX/pharmacist/medicines
    @Path("managements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getManagements(){
        List<Management> m = fakeData.getManagements();

        GenericEntity<List<Management>> entity = new GenericEntity<>(m) {  };
        return Response.ok(entity).build();
    }

    //Patients
    //To get all the patients
    @GET //GET at http://localhost:XXXX/patients/
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patients")
    public Response sayHello(){
        List<Patient> patients = fakeData.getPatientsList();

        GenericEntity<List<Patient>> entity = new GenericEntity<>(patients) {  };
        return Response.ok(entity).build();
    }

    //to add a new patient
    @POST //POST at http://localhost:XXXX/patient/
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("patient")
    public Response createStudent(Patient p) {
        if (!fakeData.addPatient(p)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Patient with the given number " + p.getId() + " already exists.";
            // throw new Exception(Response.Status.CONFLICT, "This topic already exists");
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + p.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }
    // to search a patient
    @GET //GET at http://localhost:XXXX/pharmacist/3
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patient/{id}")
    public Response getPatientById(@PathParam("id") int patientId) {
        //fakeData.getPatientById(patientId);x
        Patient p = fakeData.getPatientById(patientId);//studentsRepository.get(stNr);
        List<Patient> list = new ArrayList<>();
        list.add(p);
        GenericEntity<List<Patient>> entity = new GenericEntity<>(list){};

        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid student number.").build();
        } else {
            return Response.ok(entity).build();
        }
    }

    //to delete a patient
    @DELETE //DELETE at http://localhost:XXXX/pharmacist/3
    @Path("patient/{id}/delete")
    public Response deletePatient(@PathParam("id") int patientId) {
        fakeData.deletePatient(patientId);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).

        return Response.noContent().build();
    }


    // to update a patient
    @PUT //PUT at http://localhost:XXXX/users/profile/experience/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/patient/{id}")
    public Response updatePatient(@PathParam("id") int id, Patient p) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeData.updatePatient(id, p)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid experience.").build();
        }
    }

    //Medicines

    //get
    @GET //GET at http://localhost:XXXX/pharmacist/medicines
    @Path("medicines")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicines(){
        List<Medicine> m = fakeData.getMedicineList();

        GenericEntity<List<Medicine>> entity = new GenericEntity<>(m) {  };
        return Response.ok(entity).build();
    }
    //add
    @POST //http://localhost:XXXX/pharmacist/medicine
    @Path("medicine")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddMedicine(Medicine m) {
        if (!fakeData.addMedicines(m)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Medicine with the given number " + m.getId() + " already exists.";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + m.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    //get a medicine by id
    @GET //GET at http://localhost:XXXX/pharmacist/medicine/2
    @Path("medicine/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicinesById(@PathParam("id") int medID){

        Medicine p = fakeData.getMedicineById(medID);
        List<Medicine> list = new ArrayList<>();
        list.add(p);
        GenericEntity<List<Medicine>> entity = new GenericEntity<>(list){};

        if (p == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid medicine id.").build();
        } else {
            return Response.ok(entity).build();
        }
    }




    //delete
    //to delete a patient
    @DELETE //DELETE at http://localhost:XXXX/pharmacist/medicine/2/delete
    @Path("medicine/{id}/delete")
    public Response deleteMedicine(@PathParam("id") int medId) {
        fakeData.deleteMedicine(medId);
        // Idempotent method. Always return the same response (even if the resource has already been deleted before).

        return Response.noContent().build();
    }

    // update
    @PUT //PUT at http://localhost:XXXX/users/profile/experience/id
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/medicine/{id}")
    public Response updateMedicine(@PathParam("id") int id, Medicine m) {
        // Idempotent method. Always update (even if the resource has already been updated before).
        if (fakeData.updateMedcine(id, m))
        {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid experience.").build();
        }
    }


    @GET //GET at http://localhost:XXXX/pharmacist/medicine/2
    @Path("medicines/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicinesByPatientId(@PathParam("patientId") int medID){

        List<Medicine> list = fakeData.getMedicinesByPatientId(medID);
        GenericEntity<List<Medicine>> entity = new GenericEntity<>(list){};

        if (list == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid medicine id.").build();
        } else {
            return Response.ok(entity).build();
        }
    }
    //add medicine to a patient
    //add
    @POST //http://localhost:XXXX/pharmacist/medicine
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("patient/medicine")
    public Response addMedicineToPatient(Management m) {


        if (!fakeData.addMedicineToPatient(m)) // In this addPatient it adds the new object in this if statement and return true or false since that method is boolean
        {
            String entity =  "Something is wrong";
            return Response.status(Response.Status.CONFLICT).entity(entity).build();
        } else {
            String url = uriInfo.getAbsolutePath() + "/" + m.getId(); // url of the created student
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }
}
