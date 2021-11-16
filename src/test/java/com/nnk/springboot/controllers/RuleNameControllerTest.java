package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeAll
    public void init() {
        RuleName ruleName = new RuleName("n", "desc", "json", "template", "sql", "sqlp");
        ruleNameService.insertRuleName(ruleName);
        for (RuleName rule : ruleNameService.findAll()) {
            if (rule.getName().equals("n")) {
                id = rule.getId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        ruleNameRepository.deleteAll();
    }

    @Test
    public void testRuleNameController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());

        // Add
        this.mockMvc.perform(post("/ruleName/validate")
                        .param("name", "new name")
                        .param("description", "desc")
                        .param("json", "json")
                        .param("template", "temp")
                        .param("sqlStr", "sql")
                        .param("sqlPart", "part")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Get update form
        this.mockMvc.perform(get("/ruleName/update/{id}", id)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());

        // Update
        this.mockMvc.perform(post("/ruleName/update/{id}", id)
                        .param("name", "update name")
                        .param("description", "update desc")
                        .param("json", "json")
                        .param("template", "temp")
                        .param("sqlStr", "sql")
                        .param("sqlPart", "part")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Delete
        this.mockMvc.perform(get("/ruleName/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
