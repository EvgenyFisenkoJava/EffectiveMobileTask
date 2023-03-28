package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.model.Feedback;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    FeedbackDto feedbackToFeedbackDto(Feedback feedback);
    Feedback feedbackDtoToFeedback(FeedbackDto feedbackDto);
    List<FeedbackDto> feedBackListToFeedbackDtoList(List<Feedback> feedbackList);
}
