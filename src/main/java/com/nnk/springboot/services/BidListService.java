package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {
    void insertBidList(BidList bidList);
    Boolean updateBidList(BidList bidList);
    List<BidList> findAll();
    BidList findById(int id);
    Boolean deleteById(int id);

}
