/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.mycompany.clientwebservice.service.ClientsREST;
import static java.lang.System.out;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nuno
 */
public class Tests extends JerseyTest{
    
    protected static EntityManagerFactory emf;

    protected EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("com.mycompany_ClientWebService_war_1.0-SNAPSHOTPU");
    }
    
    @Before
    public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }
    
    @After
    public void rollbackTransaction() {   
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

        if (em.isOpen()) {
            em.close();
        }
    }
    
//    static final String BASE_URI = "http://localhost:9998/ClientWebService/webresources/";
//    static HttpServer server;
//    static Client client;
    

//    @Override
//    protected String getRestClassName() {
//        return "com.mycompany.clientwebservice.service.ClientsREST";
//    }
//    private static HttpServer startServer() {
//        final ResourceConfig resourceConfig = new ResourceConfig()
//                    .packages("com.mycompany.clientwebservice.service")
//                    .register(ClientsREST.class);
//            return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//        server = startServer();
//        client = ClientBuilder.newClient();
//        
//        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
//        l.setLevel(Level.FINE);
//        l.setUseParentHandlers(false);
//        ConsoleHandler ch = new ConsoleHandler();
//        ch.setLevel(Level.ALL);
//        l.addHandler(ch);
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        server.shutdown();
//    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
 
        return new ResourceConfig(ClientsREST.class);
    }

    @Test
    public void testResponsesForClientOperations(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("nif", 555555555);
        jsonObj.put("name", "Andreia");
        jsonObj.put("address", "Avenida 25 de Abril");
        jsonObj.put("phone_number", 935554477);
        

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
