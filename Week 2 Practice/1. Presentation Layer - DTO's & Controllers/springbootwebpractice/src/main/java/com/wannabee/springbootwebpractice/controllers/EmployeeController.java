package com.wannabee.springbootwebpractice.controllers;

import com.wannabee.springbootwebpractice.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees") // this path will be mandatory after this the rest of the url paths will follow.
public class EmployeeController {
    @GetMapping(path="/getSecretMessage")
    public String getMySuperSecretMessage(){
        return "Secret message: Mummy Kaisi Hain?!";
    }
    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id){
       return new EmployeeDTO(id,"Shashwat","hashwat7@gmail.com", 25, LocalDate.of(2024,1,2), true);
    }

    @GetMapping
    public String getAllEmployees(@RequestParam(required = false,name = "inputAge") Integer age,@RequestParam(required = false)String sortBy){
            return "Hi age "+age+" "+sortBy;
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        inputEmployee.setId(1L);
        return inputEmployee;
    }

    @PutMapping
    public String updateTheEmployee(){
        return "This is the put mapping";
    }

}
