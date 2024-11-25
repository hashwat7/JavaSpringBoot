package com.example.homework2.controllers;

import com.example.homework2.dtos.DepartmentDTO;
import com.example.homework2.exceptions.ResourceNotFoundException;
import com.example.homework2.services.DepartmentServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentServices departmentServices;

    public DepartmentController(DepartmentServices departmentServices) {
        this.departmentServices = departmentServices;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(){
        return ResponseEntity.ok(departmentServices.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id){
        DepartmentDTO department = departmentServices.getDepartmentById(id);
        if(department == null) throw new ResourceNotFoundException("Department with id "+id+" "+"doesn't exist");
        return ResponseEntity.ok(department);
    }

    @PostMapping("/")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO department){
        DepartmentDTO createdDepartment = departmentServices.createNewDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestBody @Valid DepartmentDTO updates, @PathVariable Long id){
        DepartmentDTO departmentDTO = departmentServices.updateDepartmentById(id,updates);
        return ResponseEntity.ok(departmentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long id){
        boolean deleted = departmentServices.deleteDepartmentById(id);
        return ResponseEntity.ok(true);
    }


}
