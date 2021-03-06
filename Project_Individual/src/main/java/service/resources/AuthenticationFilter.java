package service.resources;

import io.jsonwebtoken.Claims;
import service.controller.ManagementController;
import service.controller.UserController;
import service.model.User;
import service.repository.DatabaseException;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    UserController controller = new UserController();

    @Override public void filter(ContainerRequestContext requestContext) {

        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        if (method.isAnnotationPresent(DenyAll.class))
        {
            Response response = Response.status(Response.Status.FORBIDDEN).build();
            requestContext.abortWith(response);
            return;
        }

        final String AUTHORIZATION_PROPERTY = "Authorization";
//        final String AUTHENTICATION_SCHEME = "Basic";
        //Get request headers
        // with each request it will check the headers, if header exist it will go futhure otherwise will give an error
        final MultivaluedMap<String, String> headers = requestContext.getHeaders(); //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY); //If no authorization information present: abort with UNAUTHORIZED and stop
        if (authorization == null || authorization.isEmpty())
        {
            Response response = Response.status(Response.Status.UNAUTHORIZED).
                    entity("Missing username and/or password.").build();
            requestContext.abortWith(response);
            return;
        } //Get encoded username and password
//        final String encodedCredentials = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//
//        //Decode username and password into one string
//        String credentials = new String(Base64.getDecoder().decode(encodedCredentials.getBytes()));
//        //Split username and password tokens in credentials
//        final StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
//        final String email = tokenizer.nextToken();
//        final String password = tokenizer.nextToken();

        //Check if username and password are valid (e.g., database) //If not valid: abort with UNAUTHORIED and stop
        final String token = authorization.get(0);
        Claims decoded = controller.decodeJWT(token);
        // If i can decode it it is valid
        String id = decoded.getId();
        User u = controller.getUser(parseInt(id));
        if(id.isEmpty()){
            System.out.println("Invalid user");
            Response response = Response.status(Response.Status.UNAUTHORIZED).
                    entity("Invalid email and/or password.").build();
            requestContext.abortWith(response);
            return;
        }
//        if (!isValidUser(email, password))
//        {
//            Response response = Response.status(Response.Status.UNAUTHORIZED).
//                    entity("Invalid username and/or password.").build();
//            requestContext.abortWith(response);
//            return;
//        }

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            // get allowed roles for this method
             RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
             Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value())); // roles getting form annotation

             /* isUserAllowed : implement this method to check if this user has any of the roles in the rolesSet if not isUserAllowed abort the requestContext with FORBIDDEN response*/
            if (!isUserAllowed(u.getEmail(), rolesSet))
            {
                Response response = Response.status(Response.Status.FORBIDDEN).build();
                requestContext.abortWith(response);
                return;
            }
        }
    }

    private boolean isValidUser(String email, String password){
        UserController userController = new UserController();
        User user = null;
        try {
            user = userController.getUser(email, password);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(user != null){
            return true;
        }
        else
        {
            return false;
        }

    }
    private boolean isUserAllowed(String email, Set<String> rolesSet)
    {
        UserController userController = new UserController();
        User user = null;
        try {
            user = userController.getUserByEmail(email);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //Here I am first converting userType to string then checking if the roles that comes from anotation contains
        //that. On the resources side if you have given access to different roles, here it will check by contains
        // and if the condition goes true then it will return true. e.g you asked to get all the patients which only
        // pharmacist can access. Since, I send email and password therefore it checks accordingly.
        String userType = user.getUserType().toString();
       if(rolesSet.contains(userType)){
           return true;
       }
       else {
           return false;
       }
    }

}