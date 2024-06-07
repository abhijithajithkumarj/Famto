package com.Famto.Famto.repo;

import com.Famto.Famto.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,UUID> {
    List<Product> findByUser_Id(UUID id);
}
