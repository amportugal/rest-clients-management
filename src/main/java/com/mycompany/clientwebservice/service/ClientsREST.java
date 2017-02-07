/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientwebservice.service;

import com.mycompany.clientwebservice.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import static java.lang.System.out;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author nuno
 */
@Stateless
@Api
@Path("/clients")
public class ClientsREST{
    
    @Context 
    HttpServletResponse response;

    @PersistenceContext(unitName = "com.mycompany_ClientWebService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ClientsREST() {
    }

    @ApiOperation(
        value = "Creates a new client on the database", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=204,
          message="New client created"), 
        @ApiResponse(
          code=500,
          message="New client not created, due to a wrong formatted payload or "
                  + "invalid values"), })
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createClient(
            @ApiParam(
                    value="Client",
                    name="entity",
                    required=true)
            Client entity) {
        getEntityManager().persist(entity);
    }

    @ApiOperation(
        value = "Edits an existing client on the database", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=204,
          message="Client information updated"), 
        @ApiResponse(
          code=500,
          message="Client information not updated, due to a wrong formatted payload, "
                  + "invalid values or an id to a non-existing client"), })
    @PUT
    @Path("{nif}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void editClient(
            @ApiParam(
                    value="Integer",
                    name="nif",
                    required=true)
            @PathParam("nif") Integer id, 
            @ApiParam(
                    value="Client",
                    name="entity",
                    required=true)
            Client entity) {
        Client entity2 = getEntityManager().find(Client.class, id);
        getEntityManager().merge(entity2);
        getEntityManager().merge(entity);
    }

    @ApiOperation(
        value = "Removes an existing client from the database", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=204,
          message="Client removed from the database"), 
        @ApiResponse(
          code=500,
          message="Client not deleted, due to an id to a non-existing client"), })
    @DELETE
    @Path("{nif}")
    public void removeClient(
            @ApiParam(
                    value="Integer",
                    name="nif",
                    required=true)
            @PathParam("nif") Integer id ) {
        getEntityManager().remove(getEntityManager().merge(getEntityManager().find(Client.class, id)));
    }
    
//    @GET
//    @Path("find/{id}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Client find(@PathParam("id") Integer id) {
//        return getEntityManager().find(Client.class, id);
//    }

    @ApiOperation(
        value = "Gets all the clients currently on the database", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=200,
          message="Returns a list of clients",
          response=Client.class,
          responseContainer="List")})
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Client> getClients() {
        Query q = getEntityManager().createNamedQuery("listAllClients");
        List<Client> clients = q.getResultList();
        
        return clients;
    }

    @ApiOperation(
        value = "Gets the client with a certain NIF", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=200,
          message="Returns the client",
          response=Client.class),
        @ApiResponse(
          code=500,
          message="Client has not been found with the provided NIF")})
    @GET
    @Path("getClientFromNIF/{nif}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client getClientFromNIF(
            @ApiParam(
                    value="Integer",
                    name="nif",
                    required=true)
            @PathParam("nif") Integer id) {
        Query q = getEntityManager().createNamedQuery("getClientFromNIF").setParameter(1, id);
        Client client = (Client) q.getResultList().get(0);
        
        return client;
    }

    @ApiOperation(
        value = "Gets all the clients that have a certain name", 
        consumes = MediaType.APPLICATION_JSON + "," + MediaType.APPLICATION_XML)
    @ApiResponses(value = {
        @ApiResponse(
          code=200,
          message="Returns all the clients that match the criteria",
          response=Client.class),
        @ApiResponse(
          code=500,
          message="No client has been found with the provided name")})
    @GET
    @Path("getClientFromName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Client> getClientsFromName(
            @ApiParam(
                    value="String",
                    name="name",
                    required=true)
            @PathParam("name") String name) {
        Query q = getEntityManager().createNamedQuery("getClientsFromName").setParameter(1, name);
        List<Client> clients = q.getResultList();
        
        return clients;
    }

//    @GET
//    @Path("count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String countREST() {
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<Client> rt = cq.from(Client.class);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        javax.persistence.Query q = getEntityManager().createQuery(cq);
//        return q.getSingleResult().toString();
//    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
