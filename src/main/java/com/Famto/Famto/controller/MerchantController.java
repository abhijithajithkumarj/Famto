package com.Famto.Famto.controller;

import com.Famto.Famto.entity.Product;
import com.Famto.Famto.exception.CategoryNotFoundException;
import com.Famto.Famto.exception.InvalidRequestStateException;
import com.Famto.Famto.exception.UserNotFoundException;
import com.Famto.Famto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('MERCHANTS')")
public class MerchantController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Product> addSaveCategory(
            @RequestBody Product product,
            @RequestParam String merchantId
    ) {
        try {
            Product savedProduct = productService.saveCategory(product, merchantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/get-product")
    public ResponseEntity<List<Product>> getListOfCategory(@RequestParam String merchantId) {
        try {
            List<Product> categories = productService.listCategory(merchantId);
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            } else {
                return ResponseEntity.ok().body(categories);
            }
        } catch (InvalidRequestStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteMerchant(@RequestParam String productId, @RequestParam String merchantId) {
        try {
            boolean isDeleted = productService.deleteCategory(merchantId, productId);
            if (isDeleted) {
                return ResponseEntity.ok("Product deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } catch (InvalidRequestStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}
