package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query("SELECT b FROM Bill b WHERE b.accountID.accountID = :accountId AND b.paid = false ")
    Optional<Bill> findUnpaidBillbyAccountID(@Param("accountId") int accountId);
}
