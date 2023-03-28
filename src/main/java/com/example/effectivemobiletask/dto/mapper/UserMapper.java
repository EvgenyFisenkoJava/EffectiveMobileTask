package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.RegisterReq;
import com.example.effectivemobiletask.dto.UserDto;
import com.example.effectivemobiletask.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserProfile registerReqToUser(RegisterReq registerReq);
    @Mapping(source = "active", target = "active")
    UserDto userToUserDto(UserProfile userProfile);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
