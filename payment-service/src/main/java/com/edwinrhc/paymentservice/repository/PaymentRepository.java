package com.edwinrhc.paymentservice.repository;

import com.edwinrhc.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {


    List<Payment> findByPaymentStatusIgnoreCase(String status);

}
