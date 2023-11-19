package com.doan.payload;

import lombok.Data;

@Data
public class EditScoreRequest {
    private Long masterDetailId;
    private Double score;
}
