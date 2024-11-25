package com.example.homework2.repositories;

import com.example.homework2.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositories extends JpaRepository<DepartmentEntity, Long> {
}
