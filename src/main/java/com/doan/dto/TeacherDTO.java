package com.doan.dto;

import com.doan.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO implements Serializable {
    private Long teacherId;
    private String teacherName;
    private String email;
    private String phone;
    private String researchDirection;
    private Long subjectId;
    private String subjectName;
    private Integer maxStudentsHD;
    private Integer maxStudentsPB;
    private String sex;
}
