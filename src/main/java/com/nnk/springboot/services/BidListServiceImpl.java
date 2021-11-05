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
        return list.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        Optional<BidList> optionalBidList = bidListRepository.findById(id);
        optionalBidList.ifPresent(bidListRepository::delete);
    }

}
