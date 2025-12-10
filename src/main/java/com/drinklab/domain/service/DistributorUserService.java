package com.drinklab.domain.service;


import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorUserService {

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private UserService userService;

    @Transactional
    public void attach(Long distributorId, Long userId){

        Distributor distributor = this.distributorService.findById(distributorId);

        UserEntity user = this.userService.findById(userId);

        distributor.getUsers().add(user);

    }

    @Transactional
    public void detach(Long distributorId, Long userId){

        Distributor distributor = this.distributorService.findById(distributorId);

        UserEntity user = this.userService.findById(userId);

        distributorService.getDistributorByUserAndDistributorId(distributorId,userId);

        distributor.getUsers().remove(user);

    }

}
