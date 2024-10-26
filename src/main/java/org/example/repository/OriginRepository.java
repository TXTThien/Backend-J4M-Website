package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Brand;
import org.example.entity.Origin;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OriginRepository extends JpaRepository<Origin, Integer> {
    List<Origin> findAllByStatus(Status status);

}