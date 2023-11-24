package com.doan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListTeacherByCouncli {
    private Long teacherId;
    private String teacherName;
    private String email;
    private String phone;
    private String researchDirection;
}
