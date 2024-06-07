package com.Famto.Famto.serviceImp;

import com.Famto.Famto.entity.Product;
import com.Famto.Famto.entity.Role;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.exception.CategoryNotFoundException;
import com.Famto.Famto.exception.InvalidRequestStateException;
import com.Famto.Famto.exception.UserNotFoundException;
import com.Famto.Famto.repo.ProductRepository;
import com.Famto.Famto.repo.UserRepository;
import com.Famto.Famto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Product saveCategory(Product product, String merchantId) {
        try {
            Optional<User> merchant = userRepository.findById(UUID.fromString(merchantId));
            if (merchant.isPresent()  && merchant.get().getRole() == Role.MERCHANTS) {
                User merchantData = merchant.get();
                Product productData = Product.builder()
                        .productName(product.getProductName())
                        .Price(product.getPrice())
                        .quantity(product.getQuantity())
                        .user(merchantData)
                        .build();
                return productRepository.save(productData);
            } else {
                throw new UserNotFoundException("User with ID " + merchantId + " not found");
            }
        } catch (IllegalArgumentException | UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the product", e);
        }
    }

    @Override
    public List<Product> listCategory(String merchantId) {
        try {
            List<Product> categories = productRepository.findByUser_Id(UUID.fromString(merchantId));
            return categories.stream()
                    .filter(product -> !product.isDelete())
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestStateException("Invalid merchant ID format");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching categories", e);
        }
    }

    @Override
    public boolean deleteCategory(String merchantId, String categoryId) {
        try {
            UUID categoryUUID = UUID.fromString(categoryId);
            Optional<Product> categoryData = productRepository.findById(categoryUUID);

            if (categoryData.isPresent()) {
                Product category = categoryData.get();
                if (!category.isDelete()) {
                    category.setDelete(true);
                    productRepository.save(category);
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new CategoryNotFoundException("Product with ID " + categoryId + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestStateException("Invalid category ID format");
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while soft deleting the category", e);
        }
    }



}
