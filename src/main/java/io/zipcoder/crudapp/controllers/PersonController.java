package io.zipcoder.crudapp.controllers;

import io.zipcoder.crudapp.entities.Person;
import io.zipcoder.crudapp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
public class PersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person p){
        return new ResponseEntity<>(this.personRepository.save(p), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> getPersonList(){
        return new ResponseEntity<>(this.personRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable(value="id") Integer id){
        return new ResponseEntity<>(this.personRepository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "id") Integer id, @RequestBody Person p){
        if(this.personRepository.findById(id).isPresent()){
            Person personToUpdate = this.personRepository.findById(id).get();
            personToUpdate.setFirstName(p.getFirstName());
            personToUpdate.setLastName(p.getLastName());
            return new ResponseEntity<>(this.personRepository.save(personToUpdate), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.personRepository.save(p), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deletePerson(Integer id){
        Person personToDelete = this.personRepository.findById(id).get();
        this.personRepository.delete(personToDelete);
    }




}
