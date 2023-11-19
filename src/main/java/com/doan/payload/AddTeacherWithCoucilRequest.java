package com.doan.payload;

import lombok.Data;

@Data
public class AddTeacherWithCoucilRequest {
    private Long teacherId;
    private Long coucilId;
}
