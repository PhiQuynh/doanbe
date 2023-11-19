package com.doan.payload;

import com.doan.entity.Teacher;
import lombok.Data;

@Data
public class AssignCriticismRequest {
    private Long masterDetailId;
    private Long teacherPBId;
}
