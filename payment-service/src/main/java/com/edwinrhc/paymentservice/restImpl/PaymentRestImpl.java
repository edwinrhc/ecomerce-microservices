package com.edwinrhc.paymentservice.restImpl;

import com.edwinrhc.paymentservice.constants.PaymentConstants;
import com.edwinrhc.paymentservice.dto.payment.CreatePaymentDTO;
import com.edwinrhc.paymentservice.dto.payment.UpdatePaymentDTO;
import com.edwinrhc.paymentservice.rest.PaymentRest;
import com.edwinrhc.paymentservice.service.PaymentService;
import com.edwinrhc.paymentservice.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PaymentRestImpl implements PaymentRest {

    @Autowired
    PaymentService paymentService;

    @Override
    public ResponseEntity<String> createPayment(CreatePaymentDTO dto) {
        try{
            return paymentService.createPayment(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return PaymentUtils.getResponseEntity(PaymentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updatePayment(UpdatePaymentDTO dto) {
        try{
            return paymentService.updatePayment(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return PaymentUtils.getResponseEntity(PaymentConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<CreatePaymentDTO>> getAllPayments() {
      try{
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
      }catch (Exception e){
          e.printStackTrace();
      }
      return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<CreatePaymentDTO> getPaymentById(Long id) {
        return  paymentService.getPaymentById(id);
    }

    @Override
    public ResponseEntity<List<CreatePaymentDTO>> getPaymentByStatus(String status) {
        List<CreatePaymentDTO> payments = paymentService.getPaymentsStatus(status);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
