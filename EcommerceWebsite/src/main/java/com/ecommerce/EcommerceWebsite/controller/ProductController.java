package com.ecommerce.EcommerceWebsite.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.ecommerce.EcommerceWebsite.config.CustomUserDetails;
import com.ecommerce.EcommerceWebsite.model.Cart;
import com.ecommerce.EcommerceWebsite.service.CartService;
import jakarta.mail.search.SearchTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private CartService cartService;

    // @GetMapping("/product") 
    // public String viewProductPage(Model model, Principal principal) {
    //     model.addAttribute("listProducts", productService.getAllProducts());
    //     UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
    //     model.addAttribute("userdetail", userDetails);
    //     return "product";
    // }

//    @GetMapping("/product")
//	public String products(Model model, Principal principal, @RequestParam(value = "category", defaultValue = "") String category) {
//        //model.addAttribute("listProducts", productService.getAllProducts());
//        if(principal != null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//            model.addAttribute("userdetail", userDetails);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                throw new UsernameNotFoundException("User not authenticated");
//            }
//
//            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//            Long userId = customUserDetails.getId();
//
//            List<Cart> carts = cartService.getCartByUserId(userId);
//
//            model.addAttribute("carts_length", carts.size());
//        } else model.addAttribute("userdetail", null);
//		List<Product> listProducts = productService.getAllActiveProducts(category);
//		model.addAttribute("listProducts", listProducts);
//		model.addAttribute("paramValue", category);
//
//		return "product";
//	}

//    @GetMapping("/searc")
//	public String searchProduct(@RequestParam String ch, Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("userdetail", userDetails);
//		List<Product> listProducts = productService.searchProduct(ch);
//		model.addAttribute("listProducts", listProducts);
//		return "product";
//	}


//    @GetMapping("/searchunauthenticated")
//    public String searchProduct(@RequestParam String ch,Model model) {
//        List<Product> listProducts = productService.searchProduct(ch);
//        model.addAttribute("listProducts", listProducts);
//        return "product";
//    }

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

        if(principal!=null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("userdetail", userDetails);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UsernameNotFoundException("User not authenticated");
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = customUserDetails.getId();

            List<Cart> carts = cartService.getCartByUserId(userId);

            model.addAttribute("carts_length", carts.size());
        } else model.addAttribute("userdetail", null);

        model.addAttribute("product", product);
        
        return "view_product";
    }

    @GetMapping("/product")
    public String listProducts(Principal principal,
                               @RequestParam("term") Optional<String> term,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("sortField") Optional<String> sortField,
                               @RequestParam("sortDir") Optional<String> sortDir,
                               Model model) {

        if(principal != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("userdetail", userDetails);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UsernameNotFoundException("User not authenticated");
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = customUserDetails.getId();

            List<Cart> carts = cartService.getCartByUserId(userId);

            model.addAttribute("carts_length", carts.size());
        } else model.addAttribute("userdetail", null);

        String searchTerm = term.orElse("");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String currentSortField = sortField.orElse("title"); // Default sort field
        String currentSortDir = sortDir.orElse("asc"); // Default sort direction
        Page<Product> productPage = null;

        if(!searchTerm.equals("")){
            productPage = productService.searchPaginated(searchTerm,
                    PageRequest.of(currentPage - 1, pageSize, Sort.Direction.fromString(currentSortDir), currentSortField));
        }else {
            productPage = productService.findPaginated(
                    PageRequest.of(currentPage - 1, pageSize, Sort.Direction.fromString(currentSortDir), currentSortField));
        }

        model.addAttribute("listProducts", productPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sortField", currentSortField);
        model.addAttribute("sortDir", currentSortDir);
        model.addAttribute("reverseSortDir", currentSortDir.equals("asc") ? "desc" : "asc");

        return "product";
    }

    // @GetMapping("/deleteProduct/{id}")
    // public String deleteProduct(@PathVariable(value = "id") long id) {

    //     return "redirect:/product";
    // }
    
}
