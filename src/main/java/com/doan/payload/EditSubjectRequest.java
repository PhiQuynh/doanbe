package com.doan.payload;

import lombok.Data;

@Data
public class EditSubjectRequest {
    private Long subjectId;
    private String subjectName;
}
