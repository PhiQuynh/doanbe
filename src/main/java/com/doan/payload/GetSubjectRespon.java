package com.doan.payload;

import com.doan.entity.Subject;
import lombok.Data;

import java.util.List;

@Data
public class GetSubjectRespon {
    private int code;
    private String message;
    private int totalSubject;
    private List<Subject> subjects;
}
