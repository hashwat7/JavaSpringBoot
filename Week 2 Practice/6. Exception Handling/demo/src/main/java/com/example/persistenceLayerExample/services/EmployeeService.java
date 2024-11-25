package com.example.persistenceLayerExample.services;

import com.example.persistenceLayerExample.dto.EmployeeDTO;
import com.example.persistenceLayerExample.entities.EmployeeEntity;
import com.example.persistenceLayerExample.exceptions.ResourceNotFoundException;
import com.example.persistenceLayerExample.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public void isExistByEmployeeId(Long id){
        boolean exist = employeeRepository.existsById(id);
        if(!exist) throw new ResourceNotFoundException("Employee with id "+id+" doesn't exist");
    }
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        isExistByEmployeeId(id);
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity,EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();
        return allEmployees
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
       EmployeeEntity employeeEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
       EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
       return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeID, EmployeeDTO employeeDTO) {
        isExistByEmployeeId(employeeID);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
         isExistByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public boolean patchEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExistByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field,value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });
        employeeRepository.save(employeeEntity);
        return true;
    }
}
