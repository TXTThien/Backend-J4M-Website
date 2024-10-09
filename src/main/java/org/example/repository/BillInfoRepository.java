package org.example.repository;

import org.example.entity.Account;
import org.example.entity.BillInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillInfoRepository extends JpaRepository<BillInfo, Integer> {
}
