/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import static org.junit.Assert.*;

import com.mycompany.clientwebservice.Client;
import org.junit.Test;
import com.mycompany.clientwebservice.service.ClientsREST;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.json.JSONObject;
import static org.mockito.Mockito.mock;

/**
 *
 * @author nuno
 */
public abstract class RestTest extends JerseyTest{
//    private HttpServer server;
//    private ResteasyWebTarget target;
//    private static final String BASE_URI = "http://localhost:8080/api/";
    
//    @Before
//    public void setUp() throws Exception {
//        //server =  GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
// 
//        ResteasyClient rest_client = new ResteasyClientBuilder().build();
//        
//        target = rest_client.target("http://localhost:8080/"
//                + "ClientWebService/webresources/clients/");
//    }
        
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        //enable(TestProperties.DUMP_ENTITY);
        
        return new ResourceConfig();
    }
    
    abstract protected String getRestClassName();
    
//    @Override
//    protected TestContainerFactory getTestContainerFactory() {
//        return new GrizzlyWebTestContainerFactory();
//    }
//    
//    @Override
//    protected DeploymentContext configureDeployment() {
//        return ServletDeploymentContext.forServlet(new ServletContainer(new ResourceConfig(ClientsREST.class))).build();
//    }
    
    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new TestContainerFactory() {
            @Override
            public TestContainer create(final URI baseUri, DeploymentContext deploymentContext) {
                return new TestContainer() {
                    private HttpServer server;

                    @Override
                    public ClientConfig getClientConfig() {
                        return null;
                    }

                    @Override
                    public URI getBaseUri() {
                        return baseUri;
                    }

                    @Override
                    public void start() {
                        try {
                            this.server = GrizzlyWebContainerFactory.create(
                                    baseUri, Collections.singletonMap(ServerProperties.PROVIDER_CLASSNAMES, getRestClassName())
                            );
                        } catch (ProcessingException | IOException e) {
                            throw new TestContainerException(e);
                        }
                    }

                    @Override
                    public void stop() {
                        this.server.shutdownNow();

                    }
                };
            }
        };
    }

    
    
    
}
