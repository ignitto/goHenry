package com.go.henry.ignas.controller;

import com.go.henry.ignas.exception.ResourceNotFoundException;
import com.go.henry.ignas.model.Parent;
import com.go.henry.ignas.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParentController {

    @Autowired
    private ParentService parentService;

    @PostMapping(path = "parents")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent){
        parentService.createParent(parent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "parents/{id}")
    public ResponseEntity<Parent> getParent(@PathVariable Long id) throws ResourceNotFoundException {
       Parent parent = parentService.getParentById(id);
       return new ResponseEntity<>(parent, HttpStatus.FOUND);
    }

    @PutMapping(path = "parents/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long id, @RequestBody Parent parent) throws ResourceNotFoundException {
        Parent updatedParent = parentService.updateParent(id, parent);
        return new ResponseEntity<>(updatedParent, HttpStatus.OK);
    }

}
