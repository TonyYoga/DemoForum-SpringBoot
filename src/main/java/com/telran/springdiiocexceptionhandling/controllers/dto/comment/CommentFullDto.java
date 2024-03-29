package com.telran.springdiiocexceptionhandling.controllers.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@ApiModel(value = "CommentFullDto",description = "Full Comment data transfer object extends CommentDto")

public class CommentFullDto extends CommentDto{

    @ApiModelProperty(notes = "Id of comment")
    private String id;

    @ApiModelProperty(notes = "Owner of comment")
    private String owner;

    @ApiModelProperty(notes = "Author of comment")
    private String author;

    @ApiModelProperty(notes = "Date and time of comment")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @Builder(builderMethodName = "fullCommentBuilder")
    public CommentFullDto(String message, String id, String author, String owner, LocalDateTime date) {
        super(message);
        this.owner = owner;
        this.id = id;
        this.date = date;
        this.author = author;
    }
}
