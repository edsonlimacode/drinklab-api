package com.drinklab.api.controller;


import com.drinklab.api.dto.distributor.DistributorRequestDto;
import com.drinklab.api.mapper.DistributorMapper;
import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("distributors")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private DistributorMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody DistributorRequestDto distributorRequestDto){

        Distributor distributor = this.mapper.toEntity(distributorRequestDto);

        this.distributorService.create(distributor);

    }
}
