package com.ecommerce.EcommerceWebsite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.EcommerceWebsite.model.Product;
import com.ecommerce.EcommerceWebsite.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    public Product getProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        Product product = null;
        if(optional.isPresent()) {
            product = optional.get();
        } else {
            throw new RuntimeException("Product not found for id : " + id);
        }
        return product;
    }

    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> getAllActiveProducts(String category) {
		List<Product> products = null;
		if (ObjectUtils.isEmpty(category)) {
			products = productRepository.findAll();
		} else {
			products = productRepository.findByCategory(category);
		}

		return products;
	}

	public List<Product> searchProduct(String ch) {
		return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
	}

}
