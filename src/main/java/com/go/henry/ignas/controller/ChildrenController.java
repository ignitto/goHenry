package com.go.henry.ignas.controller;

import com.go.henry.ignas.exception.ResourceNotFoundException;
import com.go.henry.ignas.model.Children;
import com.go.henry.ignas.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildrenController {

    @Autowired
    private ChildrenService childrenService;

    @PutMapping(path = "children/{id}")
    public ResponseEntity<Children> updateChildren(@PathVariable Long id, @RequestBody Children children) throws ResourceNotFoundException {
        Children updateChildren = childrenService.updateChildren(id, children);
        return new ResponseEntity<>(updateChildren, HttpStatus.OK);
    }
}
