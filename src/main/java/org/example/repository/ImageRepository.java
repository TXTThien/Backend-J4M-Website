package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Image;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImagesByProduct_ProductIDAndStatus(int id, Status status);
}
