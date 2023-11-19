package com.doan.payload;

import com.doan.dto.TeacherDTO;
import com.doan.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTeacherByIdResponse {
    private int code;
    private String message;
    private TeacherDTO teacher;
}
