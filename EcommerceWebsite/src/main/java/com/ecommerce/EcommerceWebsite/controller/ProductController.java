package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ecommerce.EcommerceWebsite.model.Product;
import com.ecommerce.EcommerceWebsite.service.ProductService;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserDetailsService userDetailsService;

    // @GetMapping("/product") 
    // public String viewProductPage(Model model, Principal principal) {
    //     model.addAttribute("listProducts", productService.getAllProducts());
    //     UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
    //     model.addAttribute("userdetail", userDetails);
    //     return "product";
    // }

    @GetMapping("/product")
	public String products(Model model, Principal principal,@RequestParam(value = "category", defaultValue = "") String category) {
        //model.addAttribute("listProducts", productService.getAllProducts());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
		List<Product> listProducts = productService.getAllActiveProducts(category);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("paramValue", category);
		return "product";
	}

    @GetMapping("/search")
	public String searchProduct(@RequestParam String ch, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
		List<Product> listProducts = productService.searchProduct(ch);
		model.addAttribute("listProducts", listProducts);
		return "product";

	}

    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        // create model attribute to bind form data
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/new_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable(value = "id") long id, Model model, Principal principal) {

        Product product = productService.getProductById(id);

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);

        model.addAttribute("product", product);
        return "view_product";
    }

    // @GetMapping("/deleteProduct/{id}")
    // public String deleteProduct(@PathVariable(value = "id") long id) {

    //     return "redirect:/product";
    // }
    
}
