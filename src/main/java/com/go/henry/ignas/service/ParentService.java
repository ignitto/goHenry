package com.go.henry.ignas.service;

import com.go.henry.ignas.dao.ParentRepository;
import com.go.henry.ignas.exception.ResourceNotFoundException;
import com.go.henry.ignas.model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    public void createParent(Parent parent){
        parentRepository.save(parent);
    }

    public Parent getParentById(Long id) throws ResourceNotFoundException {
        Optional<Parent> parent =  parentRepository.findById(id);
        return parent.orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
    }

    public Parent updateParent(Long id, Parent parent) throws ResourceNotFoundException {
        Parent existingParent = getParentById(id);
        parent.setId(existingParent.getId());
        parent.setChildren(existingParent.getChildren());
        return parentRepository.save(parent);
    }
}
