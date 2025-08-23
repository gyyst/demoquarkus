package com.gyyst.demo.mapper;

import com.gyyst.demo.model.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    // put your custom logic here as instance methods

    public Person findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Person> findAlive() {
        return list("status", Person.Status.Alive);
    }

    public void deleteStefs() {
        delete("name", "Stef");
    }

    public Person findByAge() {
        return find("age", 20).firstResult();
    }
}
