package com.europe.internship.internship.services;

import com.europe.internship.internship.dtos.UniversityDTO;

public interface UniversityService {
    UniversityDTO save(UniversityDTO universityDTO);

    UniversityDTO update(UniversityDTO universityDTO) throws Exception;

    void deleteById(Long id);

    UniversityDTO getById(Long id) throws Exception;

    void deleteAll();
}
