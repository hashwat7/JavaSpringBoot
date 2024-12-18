package com.codingshuttle.jpaTutorial.jpaTuts.controller;

import com.codingshuttle.jpaTutorial.jpaTuts.entities.ProductEntity;
import com.codingshuttle.jpaTutorial.jpaTuts.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final int PAGESIZE = 5;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ){
//        return productRepository.findByTitleOrderByPrice("Mazza");

//        return productRepository.findByOrderByPrice();

//        return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy,"price"));

//        return productRepository.findBy(Sort.by(
//                Sort.Order.desc(sortBy),
//                Sort.Order.asc("price")
//        ));

//        Pageable page = PageRequest.of(
//                pageNumber,
//                PAGESIZE,
//                Sort.by(sortBy));
//        return productRepository.findAll(page);

//        combining filter pagination and sorting
          return productRepository.findByTitleContainingIgnoreCase(
                  title,
                  PageRequest.of(
                          pageNumber,
                          PAGESIZE,
                          Sort.by(sortBy))
                  );

    }


}
