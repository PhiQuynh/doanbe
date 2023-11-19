package com.doan.payload;

import lombok.Data;

@Data
public class EditTeacherRequest {
    private Long teacherId;
    private String teacherName;
    private String email;
    private String phone;
    private String researchDirection;
    private Long subjectId;
}
