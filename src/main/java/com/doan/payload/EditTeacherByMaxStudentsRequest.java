package com.doan.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditTeacherByMaxStudentsRequest {
    private Long teacherId;
    private Integer maxStudentsHD;
    private Integer maxStudentsPB;
}
