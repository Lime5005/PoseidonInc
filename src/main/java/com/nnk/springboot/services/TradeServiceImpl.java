package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {
    Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

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
        logger.info("New Trade " + trade + " is created!");
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
            logger.info("Trade with id " + id + " is updated as " + newTrade);
        } else {
            logger.error("Failed to update Trade with id " + id + " as " + trade);
        }
        return updated;
    }

    @Override
    public Trade findById(int id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        if (optionalTrade.isPresent()) {
            logger.info("Query to find Trade with id " + id);
            return optionalTrade.get();
        } else {
            logger.error("Failed to find Trade with id " + id);
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        if (optionalTrade.isPresent()) {
            tradeRepository.delete(optionalTrade.get());
            logger.info("Trade with id " + id + " is deleted!");
        } else {
            logger.error("Failed to delete Trade with id " + id);
        }
    }
}
