package com.codingshuttle.jpaTutorial.jpaTuts.repositories;

import com.codingshuttle.jpaTutorial.jpaTuts.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    //gives list of all products with the provided title and sorted wrt price asc
    List<ProductEntity> findByTitleOrderByPrice(String title);

    //gives list of all products along and sorting wrt  price asc
    List<ProductEntity> findByOrderByPrice();

    List<ProductEntity> findBy(Sort sort);
    Page<ProductEntity> findAll(Pageable page);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable page);
}
