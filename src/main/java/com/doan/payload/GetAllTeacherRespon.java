package com.doan.payload;

import com.doan.dto.TeacherDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetAllTeacherRespon {
    private int code;
    private String message;
    private int totalRecords;
    private List<TeacherDTO> teachers;
}
