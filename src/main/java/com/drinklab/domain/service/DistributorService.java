package com.drinklab.domain.service;


import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorService {

    @Autowired
    private DistributorRepository distributorRepository;

    public void create(Distributor distributor){
        this.distributorRepository.save(distributor);
    }
}
