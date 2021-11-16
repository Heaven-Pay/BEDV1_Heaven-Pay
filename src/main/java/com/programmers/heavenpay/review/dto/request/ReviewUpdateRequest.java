package com.programmers.heavenpay.review.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReviewUpdateRequest {
    @NotBlank(message = "content는 null 이거나 empty일 수 없습니다.")
    private String content;

    @Min(value = 1, message = "score는 1 이상 4이하이어야 합니다.")
    @Max(value = 4, message = "score는 1 이상 4이하이어야 합니다.")
    private int score;

    public ReviewUpdateRequest(String content, int score) {
        this.content = content;
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }
}
