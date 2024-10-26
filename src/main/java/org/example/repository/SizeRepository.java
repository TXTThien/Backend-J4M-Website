package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Size;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    List<Size>findAllByStatus(Status status);
}
