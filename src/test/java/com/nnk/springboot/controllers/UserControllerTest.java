package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Bar", authorities = {"ADMIN"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeAll
    public void init() {
        User user = new User("username", "aaaBBB8*", "fullname", "USER");
        userService.save(user);
        for (User user1 : userService.findAll()) {
            if (user1.getUsername().equals("username")) {
                id = user1.getId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        User jim = userService.findByUsername("Jim");
        userService.delete(jim);
    }

    @Test
    public void testUserController() throws Exception {
        // FindAll
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());

        // Get add form
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());

        // Add
        mockMvc.perform(post("/user/validate")
                        .param("fullname", "Jim")
                        .param("password", "aaaBBB8*")
                        .param("username", "Jim")
                        .param("role", "USER"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(status().isFound())
                .andReturn();


        // Get update form
        mockMvc.perform(get("/user/update/{id}", id)
                        .accept(MediaType.ALL))
                        .andExpect(status().isOk());

        // Update
        mockMvc.perform(post("/user/update/{id}", id)
                .param("fullname", "John")
                .param("password", "aaaBBB8*")
                .param("username", "John")
                .param("role", "USER"))
                        .andExpect(redirectedUrl("/user/list"))
                                .andExpect(status().isFound())
                                        .andReturn();

        // Delete
        mockMvc.perform(get("/user/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
