package com.drinklab.api.mapper;


import com.drinklab.api.dto.distributor.DistributorRequestDto;
import com.drinklab.api.dto.distributor.DistributorResponseDto;
import com.drinklab.domain.model.Distributor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DistributorMapper {

    Distributor toEntity(DistributorRequestDto distributorRequestDto);

    DistributorResponseDto toDto(Distributor distributor);

    List<DistributorResponseDto> toListDto(List<Distributor> distributorList);

}
