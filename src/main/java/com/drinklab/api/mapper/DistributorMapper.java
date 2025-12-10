package com.drinklab.api.mapper;

import com.drinklab.api.dto.distributor.DistributorRequestDto;
import com.drinklab.api.dto.distributor.DistributorResponseDto;
import com.drinklab.domain.model.Distributor;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorMapper {

    Distributor toEntity(DistributorRequestDto distributorRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Distributor copyPropertiesToEntyity(DistributorRequestDto source, @MappingTarget Distributor target);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyProperties(DistributorRequestDto source, @MappingTarget Distributor target);

    DistributorResponseDto toDto(Distributor distributor);

    List<DistributorResponseDto> toListDto(List<Distributor> distributorList);

}
