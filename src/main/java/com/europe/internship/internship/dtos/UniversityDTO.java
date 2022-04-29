package com.europe.internship.internship.dtos;

import com.europe.internship.internship.entities.Faculty;
import com.europe.internship.internship.entities.University;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class UniversityDTO {
    private Long id;
    private String name;
    private String shortName;
    private Integer foundationYear;
    private List<FacultyDTO> faculties;
}
