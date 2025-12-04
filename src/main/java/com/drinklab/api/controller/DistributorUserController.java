package com.drinklab.api.controller;


import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.service.DistributorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distributors/{id}/user/{userId}")
public class DistributorUserController {

    @Autowired
    private DistributorUserService distributorUserService;

    @CheckAuthority.Master
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@PathVariable Long id,@PathVariable Long userId){
        this.distributorUserService.attach(id, userId);
    }

    @CheckAuthority.Master
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@PathVariable Long id,@PathVariable Long userId){
        this.distributorUserService.detach(id, userId);
    }
}
