package com.lazaropower.iwa.backend.controller;

import com.lazaropower.iwa.backend.model.Course;
import com.lazaropower.iwa.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: Solucionar añadir asignaturas autorizaciones
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/restApi/courses")
public class CourseRESTController {

    private CourseRepository courseRepository;

    @Autowired
    public CourseRESTController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Course> findAllCourses(){
        return courseRepository.findAll();
    }

    //TODO: Alumno y profesor encontrar las asignaturas de las que formen parte


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {

            courseRepository.save(course);
            return new ResponseEntity<Course>(course, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") long id) {
        Course course = courseRepository.findById(id).get();
        if (course == null) {
            System.out.println("Course not found!");
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }

        courseRepository.deleteById(id);
        return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("id") long id){
        course.setId(id);
        courseRepository.save(course);
        return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
    }
}
