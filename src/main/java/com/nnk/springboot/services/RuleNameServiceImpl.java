package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    Logger logger = LoggerFactory.getLogger(RuleNameServiceImpl.class);

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public void insertRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
        logger.info("New RuleName " + ruleName + " is created!");
    }

    @Override
    public Boolean updateRuleName(int id, RuleName ruleName) {
        boolean updated = false;
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            RuleName newRuleName = optionalRuleName.get();
            newRuleName.setName(ruleName.getName());
            newRuleName.setDescription(ruleName.getDescription());
            newRuleName.setJson(ruleName.getJson());
            newRuleName.setTemplate(ruleName.getTemplate());
            newRuleName.setSqlStr(ruleName.getSqlStr());
            newRuleName.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(newRuleName);
            updated = true;
            logger.info("RuleName with id " + id + " is updated as " + newRuleName);
        } else {
            logger.error("Failed to updated RuleName with id " + id + " as " + ruleName);
        }
        return updated;
    }

    @Override
    public RuleName findById(int id) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            logger.info("Query to find RuleName with id " + id);
            return optionalRuleName.get();
        } else {
            logger.error("Failed to find RuleName with id " + id);
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        if (optionalRuleName.isPresent()) {
            ruleNameRepository.delete(optionalRuleName.get());
            logger.info("RuleName with id " + id + " is deleted!");
        } else {
            logger.error("Failed to delete RuleName with id " + id);
        }
    }
}
