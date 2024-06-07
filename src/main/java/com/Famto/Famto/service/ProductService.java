package com.Famto.Famto.service;

import com.Famto.Famto.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveCategory(Product product, String merchantId);

    List<Product> listCategory(String merchantId);

    boolean deleteCategory(String merchantId, String product);



}
