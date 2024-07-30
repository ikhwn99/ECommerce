package com.ecommerce.EcommerceWebsite.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ecommerce.EcommerceWebsite.model.Product;
import com.ecommerce.EcommerceWebsite.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    // @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    public String showProducts(Model model) {
        List<Product> products = productRepository.findAll(); // Retrieve products from the database
        model.addAttribute("products", products);
        return "product"; // Thymeleaf template name
    }

    @GetMapping("/product/{productId}")
    public String viewProductDetails(@PathVariable int productId, Model model) {
        Product product = productRepository.findById(productId).orElse(null);
        model.addAttribute("product", product);
        return "view_product"; // Thymeleaf template name
    }

}
