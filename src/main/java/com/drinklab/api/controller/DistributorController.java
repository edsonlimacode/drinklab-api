package com.drinklab.api.controller;


import com.drinklab.api.dto.distributor.DistributorRequestDto;
import com.drinklab.api.dto.distributor.DistributorResponseDto;
import com.drinklab.api.mapper.DistributorMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("distributors")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private DistributorMapper mapper;


    @GetMapping
    public ResponseEntity<Page<DistributorResponseDto>> listAll(Pageable pageable) {

        Page<Distributor> distributorPage = this.distributorService.findAll(pageable);

        List<DistributorResponseDto> distributorResponseDtoList = this.mapper.toListDto(distributorPage.getContent());

        var distributorResponseDtos = new PageImpl<>(distributorResponseDtoList, pageable, distributorPage.getTotalElements());

        return ResponseEntity.ok().body(distributorResponseDtos);
    }

    @CheckAuthority.MasterOrAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody DistributorRequestDto distributorRequestDto) {

        Distributor distributor = this.mapper.toEntity(distributorRequestDto);

        this.distributorService.create(distributor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributorResponseDto> findById(@PathVariable Long id) {

        Distributor distributor = this.distributorService.findById(id);

        DistributorResponseDto distributorResponseDto = this.mapper.toDto(distributor);

        return ResponseEntity.ok(distributorResponseDto);

    }

    @PutMapping("/{id}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactive(@PathVariable Long id) {
        this.distributorService.inactive(id);
    }
}
