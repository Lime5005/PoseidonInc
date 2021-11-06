package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    List<Trade> findAll();
    void insertTrade(Trade trade);
    Boolean updateTrade(int id, Trade trade);
    Trade findById(int id);
    void deleteById(int id);
}
