package com.europe.internship.internship.controllers;

import com.europe.internship.internship.dtos.UniversityDTO;
import com.europe.internship.internship.services.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service/university")
@RequiredArgsConstructor
public class UniversityController {
    private final UniversityService universityService;

    @PostMapping("/")
    UniversityDTO save(@RequestBody  UniversityDTO universityDTO){
        return universityService.save(universityDTO);
    }

    @PutMapping("/")
    UniversityDTO update(@RequestBody UniversityDTO universityDTO) throws Exception {
        return universityService.update(universityDTO);
    };

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id) throws Exception {
        universityService.deleteById(id);
    };

    @GetMapping("/{id}")
    UniversityDTO getById(@PathVariable Long id) throws Exception {
        return universityService.getById(id);
    }
}
