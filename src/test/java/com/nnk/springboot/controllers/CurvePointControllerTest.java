package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
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
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurvePointService curvePointService;

    @Test
    public void testCurvePointController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());

        // Add
        CurvePoint curvePoint = new CurvePoint(999, 22.0, 33.0);
        this.mockMvc.perform(post("/curvePoint/validate")
                        .content(objectMapper.writeValueAsString(curvePoint))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Update
        CurvePoint newCurvePoint = new CurvePoint(999, 33.0, 33.0);
        this.mockMvc.perform(post("/curvePoint/update/999")
                        .content(objectMapper.writeValueAsString(newCurvePoint))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        CurvePoint curvePointToDelete = new CurvePoint(111, 1.0, 1.0);
        curvePointService.insertCurvePoint(curvePointToDelete);
        Integer curveId = 0;
        for (CurvePoint curve : curvePointService.findAll()) {
            if (curve.getCurveId() == 111) {
                curveId = curve.getId();
                break;
            }
        }
        this.mockMvc.perform(get("/curvePoint/delete/{id}", curveId))
                .andDo(print())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
