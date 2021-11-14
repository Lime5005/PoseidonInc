package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RuleNameServiceTest {

    @Autowired
    private RuleNameServiceImpl ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void testRuleNameService() {
        RuleName ruleName = new RuleName("rule1", "desc1", "json1", "template1", "sql1", "sqlP1");

        // Save
        ruleNameService.insertRuleName(ruleName);
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        int size = ruleNames.size();
        assertTrue(size > 0);

        // Update
        Integer id = ruleName.getId();
        ruleName.setName("rule2");
        ruleName.setSqlPart("sqlP2");
        ruleNameService.updateRuleName(id, ruleName);
        assertEquals("sqlP2", ruleName.getSqlPart());

        // Find
        RuleName byId = ruleNameService.findById(id);
        assertNotNull(byId);

        // Delete
        ruleNameService.deleteById(id);
        RuleName ruleName1 = ruleNameService.findById(id);
        assertNull(ruleName1);

    }
}
