package com.gyyst.demo.controller;

import com.gyyst.demo.mapper.PersonRepository;
import com.gyyst.demo.model.Person;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    PersonRepository repository;

    @GET
    public List<Person> list() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    @Transactional
    public Response create(Person person) {
        repository.persist(person);
        return Response.created(URI.create("/persons/" + person.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Person update(@PathParam("id") Long id, Person person) {
        Person entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        // map all fields from the person parameter to the existing entity

        entity.setName(person.getName());
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Person entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }

    @GET
    @Path("/search/{name}")
    public Person search(@PathParam("name") String name) {
        return repository.findByName(name);
    }

    @GET
    @Path("/count")
    public Long count() {
        return repository.count();
    }
}
