package com.example.homework2.services;

import com.example.homework2.dtos.DepartmentDTO;
import com.example.homework2.entities.DepartmentEntity;
import com.example.homework2.exceptions.ResourceNotFoundException;
import com.example.homework2.repositories.DepartmentRepositories;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServices{

  private final DepartmentRepositories departmentRepositories;
  private final ModelMapper modelMapper;

    public DepartmentServices(DepartmentRepositories departmentRepositories, ModelMapper modelMapper) {
        this.departmentRepositories = departmentRepositories;
        this.modelMapper = modelMapper;
    }


    public void isExistById(Long id){
        boolean exists = departmentRepositories.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Department with id "+id+" doesn't exist");
   }

   public DepartmentDTO getDepartmentById(Long id){
        isExistById(id);
       DepartmentEntity department = departmentRepositories.findById(id).orElse(null);
        return modelMapper.map(department, DepartmentDTO.class);
   }

   public List<DepartmentDTO> getAllDepartments(){
        List<DepartmentEntity> departments = departmentRepositories.findAll();
        return departments
                .stream()
                .map(department  -> modelMapper.map(department,DepartmentDTO.class))
                .collect(Collectors.toList());
   }

   public DepartmentDTO createNewDepartment(DepartmentDTO department){
        DepartmentEntity departmentEntity = modelMapper.map(department,DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentRepositories.save(departmentEntity);
        return modelMapper.map(savedDepartment,DepartmentDTO.class);
   }

   public DepartmentDTO updateDepartmentById(Long id, DepartmentDTO department){

       DepartmentEntity existingDepartmentEntity = departmentRepositories.findById(id)
               .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

       modelMapper.typeMap(DepartmentDTO.class, DepartmentEntity.class)
               .addMappings(mapper -> mapper.skip(DepartmentEntity::setId));
       modelMapper.map(department, existingDepartmentEntity);

       DepartmentEntity updatedDepartment = departmentRepositories.save(existingDepartmentEntity);
       return modelMapper.map(updatedDepartment, DepartmentDTO.class);
   }

   public Boolean deleteDepartmentById(Long id){
        isExistById(id);
        departmentRepositories.deleteById(id);
        return true;
   }

}
