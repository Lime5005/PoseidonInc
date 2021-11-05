package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {
    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public void insertCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    @Override
    public Boolean updateCurvePoint(int id, CurvePoint curvePoint) {
        boolean updated = false;
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        if (optionalCurvePoint.isPresent()) {
            CurvePoint newCurvePoint = optionalCurvePoint.get();
            newCurvePoint.setCurveId(curvePoint.getCurveId());
            newCurvePoint.setTerm(curvePoint.getTerm());
            newCurvePoint.setValue(curvePoint.getValue());
            curvePointRepository.save(newCurvePoint);
            updated = true;
        }
        return updated;
    }

    @Override
    public CurvePoint findById(int id) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        return optionalCurvePoint.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        curvePoint.ifPresent(curvePointRepository::delete);
    }
}
