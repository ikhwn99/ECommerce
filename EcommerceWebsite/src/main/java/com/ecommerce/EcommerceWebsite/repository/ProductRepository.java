package com.ecommerce.EcommerceWebsite.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import com.ecommerce.EcommerceWebsite.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategory(String category);


//    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT * FROM product_table " +
            "WHERE MATCH(title, description, category) " +
            "AGAINST(:searchTerm IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Product> searchProduct(@Param("searchTerm") String searchParam, Pageable pageable);

	List<Product> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2);
}
