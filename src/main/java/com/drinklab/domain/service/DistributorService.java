package com.drinklab.domain.service;

import com.drinklab.api.exceptions.customExceptions.BadRequestException;
import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.core.security.JwtUtils;
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

    @Autowired
    private JwtUtils jwtUtils;

    public Page<Distributor> getAllByUserId(Pageable pageable) {
        return this.distributorRepository.getAllByUserId(jwtUtils.getUserLoggedId(), pageable);
    }

    public Page<Distributor> findAll(Pageable pageable) {
        return this.distributorRepository.findAll(pageable);
    }

    public Distributor update(Distributor distributor) {
        return this.distributorRepository.save(distributor);
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

    public Distributor getDistributorByUserIdAndDistributorId(Long distributorId, Long userId) {
        return this.distributorRepository.getDistributorByUserIdAndDistributorId(distributorId, userId).orElseThrow(() -> new BadRequestException(
                String.format("Nenhuma distribuidora de ID: %d foi encontrada",distributorId)));
    }

    public Distributor getDistributorByUserId(Long userLoggedId) {
        return this.distributorRepository.getDistributorByUserIdAndDistributorId(userLoggedId).orElseThrow(() ->
                new BadRequestException("Você precisa esta associado a uma distribuidora para efeturar essa ação, entre em contato com o suporte"));

    }
}
