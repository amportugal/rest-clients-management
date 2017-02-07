/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientwebservice.service;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author nuno
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        BeanConfig conf = new BeanConfig();
        conf.setTitle("Clients API");
        conf.setDescription("Client management web service");
        conf.setVersion("1.0.0");
        conf.setHost("localhost:8181");
        conf.setBasePath("/ClientWebService/webresources");
        conf.setSchemes(new String[] { "https" });
        conf.setResourcePackage("com.mycompany.clientwebservice.service");
        conf.setScan(true);
    }
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "com.mycompany.clientwebservice.service");
        return properties;
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        
        
        //Swagger classes
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.clientwebservice.service.ClientsREST.class);
    }
    
}
