package com.lazaropower.iwa.backend.controller;

import com.lazaropower.iwa.backend.model.Course;
import com.lazaropower.iwa.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/restApi/courses")
public class CourseRESTController {

    private CourseRepository courseRepository;

    @Autowired
    public CourseRESTController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Course> findAllCourses(){
        return courseRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return new ResponseEntity<Course>(course, HttpStatus.CREATED);
    }

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

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("id") long id){
        course.setId(id);
        courseRepository.save(course);
        return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
    }
}