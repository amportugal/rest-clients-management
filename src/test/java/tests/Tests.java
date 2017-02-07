/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.mycompany.clientwebservice.Client;
import static java.lang.System.out;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nuno
 */
public class Tests extends RestTest {
    
    private static final Logger LOG = LoggerFactory.getLogger("TestLog");

    @Override
    protected String getRestClassName() {
        return "com.mycompany.clientwebservice.service.ClientsREST";
    }


    //@Test
    public void testResponsesForClientOperations(){
        Client client = new Client(555555555, "Andreia", "Avenida 25 de Abril", 935554477);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("nif", 555555555);
        jsonObj.put("name", "Andreia");
        jsonObj.put("address", "Avenida 25 de Abril");
        jsonObj.put("phone_number", 935554477);
        
//        ResteasyClient rest_client = new ResteasyClientBuilder().build();
//        ResteasyWebTarget target = rest_client.target("http://localhost:8080/"
//                + "ClientWebService/webresources/clients/");
        out.println(target("clients").getUri().toString());
        Response response = target("clients").request().post(Entity.json(jsonObj.toString()));
        out.println(response.getStatus());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//        
//        
//        target = rest_client.target("http://localhost:8080/"
//                + "ClientWebService/webresources/clients/getClientFromNIF/" + client.getNif());
//        response = target.request().buildGet().invoke();
//        
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        
//        
//        target = rest_client.target("http://localhost:8080/"
//                + "ClientWebService/webresources/clients/" + client.getNif());
//        response = target.request().buildDelete().invoke();
//        
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//        
//        
//        target = rest_client.target("http://localhost:8080/"
//                + "ClientWebService/webresources/clients/getClientFromNIF/" + client.getNif());
//        response = target.request().buildGet().invoke();
//        
//        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
