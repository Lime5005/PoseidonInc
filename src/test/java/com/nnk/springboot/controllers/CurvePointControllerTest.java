package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
@WithMockUser(username = "Foo", authorities = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeAll
    public void init() {
        CurvePoint curvePoint = new CurvePoint(111, 1.0, 1.0);
        curvePointService.insertCurvePoint(curvePoint);
        for (CurvePoint curve : curvePointService.findAll()) {
            if (curve.getCurveId() == 111) {
                id = curve.getId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        curvePointRepository.deleteAll();
    }

    @Test
    public void testCurvePointController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());

        // Add
        this.mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "999")
                        .param("term", "22.0")
                        .param("value", "33.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Get update form
        this.mockMvc.perform(get("/curvePoint/update/{id}", id)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());


        // Update
        this.mockMvc.perform(post("/curvePoint/update/{id}", id)
                        .param("curveId", "888")
                        .param("term", "33.0")
                        .param("value", "44.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Delete
        this.mockMvc.perform(get("/curvePoint/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
