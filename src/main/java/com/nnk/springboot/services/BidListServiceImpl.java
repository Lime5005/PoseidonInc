package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService{
    Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public void insertBidList(BidList bidList) {
        bidListRepository.save(bidList);
        logger.info("New BidList " + bidList + " is created!");
    }

    @Override
    public Boolean updateBidList(int id, BidList bidList) {
        boolean updated = false;
        Optional<BidList> list = bidListRepository.findById(id);
        if (list.isPresent()) {
            BidList newBidList = list.get();
            newBidList.setAccount(bidList.getAccount());
            newBidList.setType(bidList.getType());
            newBidList.setBidQuantity(bidList.getBidQuantity());
            bidListRepository.save(newBidList);
            updated = true;
            logger.info("BidList with id " + id + " is updated as " + newBidList);
        } else {
            logger.error("Failed to update BidList with id " + id + " as " + bidList);
        }
        return updated;
    }


    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(int id) {
        Optional<BidList> list = bidListRepository.findById(id);
        if (list.isPresent()) {
            logger.info("Query to find BidList with id " + id);
            return list.get();
        } else {
            logger.error("Failed to find BidList with id " + id);
            return null;
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        if (optionalBidList.isPresent()) {
            bidListRepository.delete(optionalBidList.get());
            logger.info("BidList with id " + id + " is deleted!");
        } else {
            logger.error("Failed to delete BidList with id " + id);
        }
    }

}
