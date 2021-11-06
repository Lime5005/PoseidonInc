package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public void insertTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public Boolean updateTrade(int id, Trade trade) {
        boolean updated = false;
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        if (optionalTrade.isPresent()) {
            Trade newTrade = optionalTrade.get();
            newTrade.setAccount(trade.getAccount());
            newTrade.setType(trade.getType());
            newTrade.setBuyQuantity(trade.getBuyQuantity());
            tradeRepository.save(newTrade);
            updated = true;
        }
        return updated;
    }

    @Override
    public Trade findById(int id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        return optionalTrade.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        optionalTrade.ifPresent(tradeRepository::delete);
    }
}
