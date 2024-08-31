package com.ecommerce.EcommerceWebsite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import com.ecommerce.EcommerceWebsite.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Page<Product> findByCategory(String category,Pageable pageable);

    @Query(value = "SELECT * FROM product_table " +
            "WHERE MATCH(title, description, category) " +
            "AGAINST(:searchTerm IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Product> searchProduct(@Param("searchTerm") String searchParam, Pageable pageable);
}
