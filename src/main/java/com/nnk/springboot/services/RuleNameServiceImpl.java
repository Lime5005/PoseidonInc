package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {
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
        }
        return updated;
    }

    @Override
    public RuleName findById(int id) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        return optionalRuleName.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
        optionalRuleName.ifPresent(ruleNameRepository::delete);
    }
}
