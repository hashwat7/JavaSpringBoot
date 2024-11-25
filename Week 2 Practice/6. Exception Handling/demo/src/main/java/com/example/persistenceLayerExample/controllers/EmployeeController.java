package com.example.persistenceLayerExample.controllers;

import com.example.persistenceLayerExample.dto.EmployeeDTO;
import com.example.persistenceLayerExample.entities.EmployeeEntity;
import com.example.persistenceLayerExample.exceptions.ResourceNotFoundException;
import com.example.persistenceLayerExample.repositories.EmployeeRepository;
import com.example.persistenceLayerExample.services.EmployeeService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){

        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        if(employeeDTO == null) throw  new ResourceNotFoundException("Employee not found with the id :"+id);
        return ResponseEntity.ok(employeeDTO);
   }




   @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
   }

   @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
   }

   @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO1 = employeeService.updateEmployeeById(employeeId,employeeDTO);
        if(employeeDTO1 == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO1);
   }

   @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
   }

   @PatchMapping(path = "{employeeId}")
   public ResponseEntity<Boolean> patchEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        boolean patchedSuccessfully = employeeService.patchEmployeeById(employeeId,updates);
        if(patchedSuccessfully) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
   }

}
