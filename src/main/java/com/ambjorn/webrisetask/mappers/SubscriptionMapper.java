package com.ambjorn.webrisetask.mappers;

import com.ambjorn.webrisetask.dto.SubscriptionDTO;
import com.ambjorn.webrisetask.models.Subscription;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    Subscription toEntitySave(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO toDTO(Subscription subscription);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void toEntityUpdate(SubscriptionDTO subscriptionDTO, @MappingTarget Subscription subscription);
}
