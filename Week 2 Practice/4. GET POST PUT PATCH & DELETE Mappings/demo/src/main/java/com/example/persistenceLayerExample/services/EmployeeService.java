package com.example.persistenceLayerExample.services;

import com.example.persistenceLayerExample.dto.EmployeeDTO;
import com.example.persistenceLayerExample.entities.EmployeeEntity;
import com.example.persistenceLayerExample.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public boolean isExistByEmployeeId(Long id){
        return employeeRepository.existsById(id);
    }
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if(employeeEntity == null) return null;
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
        boolean exist = isExistByEmployeeId(employeeID);
        if(!exist) return null;
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeID);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);

    }

    public boolean deleteEmployeeById(Long employeeId) {
        boolean exist = isExistByEmployeeId(employeeId);
        if(!exist) return false;
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public boolean patchEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean exist = isExistByEmployeeId(employeeId);
        if(!exist) return false;
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
