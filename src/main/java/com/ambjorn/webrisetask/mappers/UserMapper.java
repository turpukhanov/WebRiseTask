package com.ambjorn.webrisetask.mappers;

import com.ambjorn.webrisetask.dto.UserDTO;
import com.ambjorn.webrisetask.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {SubscriptionMapper.class}, componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntitySave(UserDTO userDTO);

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void toEntityUpdate(UserDTO userDTO, @MappingTarget User user);
}
