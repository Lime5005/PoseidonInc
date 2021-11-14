package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuleNameService ruleNameService;

    @Test
    public void testRuleNameController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());

        // Add
        RuleName ruleName = new RuleName(111, "name", "desc", "json", "template", "sql", "sqlp");
        this.mockMvc.perform(post("/ruleName/validate")
                        .content(objectMapper.writeValueAsString(ruleName))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Update
        RuleName newRuleName = new RuleName(111, "new name", "desc", "json", "template", "sql", "sqlp");
        this.mockMvc.perform(post("/ruleName/update/111")
                        .content(objectMapper.writeValueAsString(newRuleName))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        RuleName ruleNameToDelete = new RuleName(111, "n", "desc", "json", "template", "sql", "sqlp");
        ruleNameService.insertRuleName(ruleNameToDelete);
        Integer ruleId = 0;
        for (RuleName rule : ruleNameService.findAll()) {
            if (rule.getName().equals("n")) {
                ruleId = rule.getId();
                break;
            }
        }
        this.mockMvc.perform(get("/ruleName/delete/{id}", ruleId))
                .andDo(print())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
