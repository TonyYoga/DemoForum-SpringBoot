package com.telran.springdiiocexceptionhandling.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CommentResponceDto {
    private String id;
    private String author;
    private String message;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
}
