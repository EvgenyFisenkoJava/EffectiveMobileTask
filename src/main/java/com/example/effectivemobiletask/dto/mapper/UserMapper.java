package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.RegisterReq;
import com.example.effectivemobiletask.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserProfile registerReqToUser(RegisterReq registerReq);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
