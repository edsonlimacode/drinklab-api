package com.drinklab.domain.service;

import com.drinklab.api.exceptions.customExceptions.ForbiddenException;
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
    private JwtUtils jwtUtil;

    public Page<Distributor> findAll(Pageable pageable) {
        return this.distributorRepository.findAll(pageable);
    }

    public void create(Distributor distributor) {
        this.distributorRepository.save(distributor);
    }

    public Distributor findById(Long id) {
        return this.distributorRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Recurso de id: %d não foi encontrado", id)));
    }

    @Transactional
    public void inactive(Long distributorId) {
        Long userId = this.jwtUtil.getUserId();

        Distributor distributor = this.findById(distributorId);

        boolean user = distributor.getUsers()
                .stream().anyMatch(u -> u.getId().equals(userId));

        if (!user) {
            throw new ForbiddenException(String.format("Você não tem permissão para acessar o recurso de ID: %d", distributorId));
        }

        distributor.setActive(false);
    }

}
