package com.europe.internship.internship.services.impl;

import com.europe.internship.internship.dtos.FacultyDTO;
import com.europe.internship.internship.dtos.UniversityDTO;
import com.europe.internship.internship.entities.Faculty;
import com.europe.internship.internship.entities.University;
import com.europe.internship.internship.repositories.UniversityRepository;
import com.europe.internship.internship.services.UniversityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository repository;

    private final ModelMapper modelMapper;

    @Override
    public UniversityDTO save(UniversityDTO universityDTO) {
        University university = modelMapper.map(universityDTO, University.class);

        University savedUniversity = repository.save(university);

        return modelMapper.map(savedUniversity, UniversityDTO.class);
    }

    @Override
    public UniversityDTO update(UniversityDTO universityDTO) throws Exception {
        Optional<University> universityOptional =  repository.findById(universityDTO.getId());

        if(universityOptional.isEmpty()) {
            throw new Exception("This university does not exist");
        }

        University university = universityOptional.get();

        University savedUniversity = repository.save(modelMapper.map(universityDTO, University.class));
        return modelMapper.map(savedUniversity, UniversityDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        Optional<University> university = repository.findById(id);
        if (university.isPresent()){
            repository.deleteById(id);
        }
    }

    @Override
    public UniversityDTO getById(Long id) throws Exception {
        return repository.findById(id)
                .map(val ->modelMapper.map(val, UniversityDTO.class))
                .orElseThrow(()->new Exception(String.format("Error on getting university by id %s", id)));
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
