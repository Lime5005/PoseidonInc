package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService{

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public void insertBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    @Override
    public Boolean updateBidList(BidList bidList) {
        boolean updated = false;
        Integer bidListId = bidList.getBidListId();
        Optional<BidList> optionalBidList = bidListRepository.findById(bidListId);
        if (optionalBidList.isPresent()) {
            BidList oldBidList = optionalBidList.get();
            oldBidList.setAccount(bidList.getAccount());
            oldBidList.setType(bidList.getType());
            oldBidList.setBidQuantity(bidList.getBidQuantity());
            updated = true;
        }
        return updated;
    }


    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(int id) {
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        return optionalBidList.orElse(null);
    }

    @Override
    public Boolean deleteById(int id) {
        boolean deleted = false;
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        if (optionalBidList.isPresent()) {
            bidListRepository.delete(optionalBidList.get());
            deleted = true;
        }
        return deleted;
    }

}
