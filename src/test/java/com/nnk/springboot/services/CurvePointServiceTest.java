package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurvePointServiceTest {
    private CurvePoint curvePoint = new CurvePoint();

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;


    @AfterAll
    public void cleanCurvePoint() {
        curvePointRepository.deleteAll();
    }

    @BeforeAll
    public void init() {
        curvePoint.setCurveId(9);
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);
        curvePointService.insertCurvePoint(curvePoint);
    }

    @Test
    public void test1_FindAllCurvePoint() {
        List<CurvePoint> curvePoints = curvePointService.findAll();
        assertTrue( curvePoints.size() > 0);
    }

    @Test
    public void test2_UpdatedCurvePoint() {
        Integer id = curvePoint.getId();
        CurvePoint foundCurvePoint = curvePointService.findById(id);
        foundCurvePoint.setCurveId(2);
        foundCurvePoint.setTerm(20.0);
        foundCurvePoint.setValue(20.0);
        Boolean updated = curvePointService.updateCurvePoint(id, foundCurvePoint);
        assertTrue(updated);
    }

    @Test
    public void test3_GetCurvePointById() {
        Integer id = curvePoint.getId();
        CurvePoint foundCurvePoint = curvePointService.findById(id);
        assertNotNull(foundCurvePoint);
    }

    @Test
    public void test4_DeleteById() {
        Integer id = curvePoint.getId();
        curvePointService.deleteById(id);
        CurvePoint byId = curvePointService.findById(id);
        assertNull(byId);
    }

}
