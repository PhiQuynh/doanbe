package com.doan.payload;

import com.doan.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSubjectByIdRespon {
    private int code;
    private String message;
    private Subject subject;
}
