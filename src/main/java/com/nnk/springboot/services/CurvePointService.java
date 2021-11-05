package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurvePointService {
    List<CurvePoint> findAll();
    void insertCurvePoint(CurvePoint curvePoint);
    Boolean updateCurvePoint(int id, CurvePoint curvePoint);
    CurvePoint findById(int id);
    void deleteById(int id);

}
