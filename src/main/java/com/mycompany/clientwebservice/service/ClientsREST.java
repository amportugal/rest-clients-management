/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientwebservice.service;

import com.mycompany.clientwebservice.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author nuno
 */
@Stateless
@Path("clients")
public class ClientsREST{

    @PersistenceContext(unitName = "com.mycompany_ClientWebService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ClientsREST() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createClient(Client entity) {
        getEntityManager().persist(entity);
    }

    @PUT
    @Path("{nif}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void editClient(@PathParam("nif") Integer id, Client entity) {
        getEntityManager().merge(entity);
    }

    @DELETE
    @Path("{nif}")
    public void removeClient(@PathParam("nif") Integer id) {
        getEntityManager().remove(getEntityManager().merge(getEntityManager().find(Client.class, id)));
    }
    
    @GET
    @Path("find/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client find(@PathParam("id") Integer id) {
        return getEntityManager().find(Client.class, id);
    }

    @GET
    @Path("{nif}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Client> getClients(@PathParam("nif") Integer id) {
        Query q = getEntityManager().createNamedQuery("listAllClients");
        List<Client> clients = q.getResultList();
        
        return clients;
    }

    @GET
    @Path("getClientFromNIF/{nif}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Client getClientFromNIF(@PathParam("nif") Integer id) {
        Query q = getEntityManager().createNamedQuery("getClientFromNIF").setParameter(1, id);
        Client client = (Client) q.getResultList().get(0);
        
        return client;
    }

    @GET
    @Path("getClientFromName/{name}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Client> getClientsFromName(@PathParam("name") String name) {
        Query q = getEntityManager().createNamedQuery("getClientsFromName").setParameter(1, name);
        List<Client> clients = q.getResultList();
        
        return clients;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<Client> rt = cq.from(Client.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getSingleResult().toString();
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}
