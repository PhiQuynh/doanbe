package com.doan.payload;

import lombok.Data;

@Data
public class AddSubjectRequest {
    private String subjectName;

    private Long departmentId;

    private Long userId;
}
