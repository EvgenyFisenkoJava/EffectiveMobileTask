package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.NotificationDto;
import com.example.effectivemobiletask.model.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    NotificationDto notifToNotifDto(Notification notification);

    List<NotificationDto> listToDtoList(List<Notification> notificationList);

}
