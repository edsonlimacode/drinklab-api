package com.drinklab.domain.service;

import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.Distributor;
import com.drinklab.domain.repository.DistributorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DistributorService {

    @Autowired
    private DistributorRepository distributorRepository;

    public Page<Distributor> findAll(Pageable pageable) {
        return this.distributorRepository.findAll(pageable);
    }

    public void create(Distributor distributor) {
        this.distributorRepository.save(distributor);
    }

    public Distributor findById(Long id) {
        return this.distributorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Distribuidora de id: %d não foi encontrado", id)));
    }

    @Transactional
    public void active(Long distributorId) {
        Distributor distributor = this.findById(distributorId);
        distributor.setActive(true);
    }

    @Transactional
    public void inactive(Long distributorId) {
        Distributor distributor = this.findById(distributorId);
        distributor.setActive(false);
    }

    public Distributor getDistributorByUserId(Long userId) {
        return this.distributorRepository.getDistributorByUserId(userId).orElseThrow(() -> new BadRequestException(
                String.format("Nenhuma distribuidora encontrada para o usuario de ID: %d", userId)));
    }

}
