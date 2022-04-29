package com.europe.internship.internship.dtos;

import com.europe.internship.internship.entities.University;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
public class FacultyDTO {
    private Long id;
    private String name;
    private String shortName;
    private Long universityId;
}
