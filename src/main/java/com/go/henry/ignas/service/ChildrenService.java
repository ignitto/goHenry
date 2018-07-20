package com.go.henry.ignas.service;

import com.go.henry.ignas.dao.ChildrenRepository;
import com.go.henry.ignas.exception.ResourceNotFoundException;
import com.go.henry.ignas.model.Children;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChildrenService {

    @Autowired
    private ChildrenRepository childrenRepository;

    public Children updateChildren(Long id, Children children) throws ResourceNotFoundException {
        Children existingChildren = getChildrenById(id);
        children.setId(existingChildren.getId());
        return childrenRepository.save(children);
    }

    public Children getChildrenById(Long id) throws ResourceNotFoundException {
        Optional<Children> children = childrenRepository.findById(id);
        return children.orElseThrow(() -> new ResourceNotFoundException("Parent not found"));
    }
}
