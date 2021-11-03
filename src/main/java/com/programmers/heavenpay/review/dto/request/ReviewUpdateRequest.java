package com.programmers.heavenpay.review.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ReviewUpdateRequest {
    @NotBlank
    private String content;

    @Min(value = 1)
    @Max(value = 4)
    private int score;
}
