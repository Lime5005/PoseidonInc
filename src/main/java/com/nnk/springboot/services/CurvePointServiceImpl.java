package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService {
    Logger logger = LoggerFactory.getLogger(CurvePointServiceImpl.class);

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
        logger.info("New CurvePoint " + curvePoint + " is created!");
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
            logger.info("CurvePoint with id " + id + " is updated as " + newCurvePoint);
        } else {
            logger.error("Failed to updated CurvePoint with id " + id + " as " + curvePoint);
        }
        return updated;
    }

    @Override
    public CurvePoint findById(int id) {
        Optional<CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        if (optionalCurvePoint.isPresent()) {
            logger.info("Query to find CurvePoint with id " + id);
            return optionalCurvePoint.get();
        } else {
            logger.error("Failed to find CurvePoint with id " + id);
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            curvePointRepository.delete(curvePoint.get());
            logger.info("CurvePoint with id " + id + " is deleted!");
        } else {
            logger.error("Failed to delete CurvePoint with id " + id);
        }
    }
}
