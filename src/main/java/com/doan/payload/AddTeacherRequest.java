package com.doan.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class AddTeacherRequest {
    private String teacherName;
    private String email;
    private String phone;
    private Long subjectId;
    private Long userId;
    private String researchDirection;
    private String sex;
    private String image;
}
