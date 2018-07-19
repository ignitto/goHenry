package com.go.henry.ignas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go.henry.ignas.model.Children;
import com.go.henry.ignas.model.Parent;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ParentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Parent parent;

    @BeforeClass
    public static void init() throws IOException, ClassNotFoundException {
        InputStream is = ParentControllerTest.class.getResourceAsStream("/parentTest.json");
        parent = new ObjectMapper().readValue(is, Parent.class);
    }

    @Test
    public void createParent() throws Exception {
         mockMvc.perform(post("/parents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parent)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getParent() throws Exception {
        mockMvc.perform(get("/parents/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Jane")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.emailAddress", is("jane.doe@gohenry.co.uk")))
                .andExpect(jsonPath("$.dateOfBirth", is("1990-06-03")))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.secondName", is("")))
                .andExpect(jsonPath("$.children", hasSize(2)));
    }

    @Test
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/parents/{id}", 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateParent() throws Exception {
        parent.setSecondName("second");
        mockMvc.perform(put("/parents/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secondName", is("second")));
    }

    @Test
    public void updateChildren() throws Exception {
        Set<Children> childrenList = parent.getChildren();
        Optional<Children> children = childrenList.stream().findFirst();
        if(!children.isPresent()) {
            Assert.fail();
        }

        Children foundChildren = children.get();
        foundChildren.setSecondName("secondChildrenName");
        mockMvc.perform(put("/children/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foundChildren)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.secondName", is("secondChildrenName")));
    }


}