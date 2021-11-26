package com.instogramm.instogramm.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDTO {
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String message;
}