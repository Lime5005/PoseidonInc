package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeServiceImpl tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void testTradeService() {
        Trade trade = new Trade("acc1", "type1", 10.0);

        // Save
        tradeService.insertTrade(trade);
        List<Trade> trades = tradeService.findAll();
        int size = trades.size();
        assertTrue(size > 0);

        // Update
        Integer id = trade.getTradeId();
        trade.setAccount("new acc");
        trade.setType("new type");
        trade.setBuyQuantity(20.0);
        tradeService.updateTrade(id, trade);
        assertEquals("new acc", trade.getAccount());
        assertEquals("new type", trade.getType());
        assertEquals(20.0, trade.getBuyQuantity(), 0.01);

        // Find
        Trade byId = tradeService.findById(id);
        assertNotNull(byId);

        // Delete
        tradeService.deleteById(id);
        List<Trade> list = tradeService.findAll();
        assertEquals(0, list.size());

    }

}
