package com.edwinrhc.paymentservice.serviceImpl;

import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.paymentservice.constants.PaymentConstants;
import com.edwinrhc.paymentservice.dto.payment.CreatePaymentDTO;
import com.edwinrhc.paymentservice.dto.payment.UpdatePaymentDTO;
import com.edwinrhc.paymentservice.entity.Payment;
import com.edwinrhc.paymentservice.exception.ResourceNotFoundException;
import com.edwinrhc.paymentservice.repository.PaymentRepository;
import com.edwinrhc.paymentservice.service.PaymentService;
import com.edwinrhc.paymentservice.utils.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponse> createPayment(CreatePaymentDTO createPaymentDTO) {
        try{
            log.info("Iniciando creación de pago con DTO: {}", createPaymentDTO);

            Payment payment = modelMapper.map(createPaymentDTO, Payment.class);
            payment.setPaymentDate(LocalDateTime.now());
            Payment savedPayment = paymentRepository.save(payment);
            CreatePaymentDTO savePaymentDTO = modelMapper.map(savedPayment, CreatePaymentDTO.class);
            log.info("Pago guardado exitosamente: {}", savePaymentDTO);

            return PaymentUtils.apiResponseEntity(PaymentConstants.PAYMENT_SUCCESSFULLY_REGISTERED,savePaymentDTO, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Error al crear el pago: ", e);
            return PaymentUtils.apiResponseEntity(
                    PaymentConstants.SOMETHING_WENT_WRONG,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseEntity<String> updatePayment(UpdatePaymentDTO updatePaymentDTO) {
       log.info("updatePaymentDTO: {} " , updatePaymentDTO);
       try{
           Optional<Payment> optionalPayment = paymentRepository.findById(updatePaymentDTO.getId());

           if(!optionalPayment.isPresent()){
               return PaymentUtils.getResponseEntity(PaymentConstants.PAYMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
           }
           Payment existingPayment = optionalPayment.get();
           modelMapper.map(updatePaymentDTO, existingPayment);
           paymentRepository.save(existingPayment);
           log.info("updatePaymentDTO: {} " , updatePaymentDTO);
           return PaymentUtils.getResponseEntity(PaymentConstants.PAYMENT_ALREADY_UPDATED, HttpStatus.OK);

       }catch (Exception e){
           log.error("Error  al actualizar el pago", e);
           e.printStackTrace();
       }
       return PaymentUtils.getResponseEntity(PaymentConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<CreatePaymentDTO> getPaymentById(Long paymentId) {
        log.info("getPaymentById: {} ", paymentId);
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
            CreatePaymentDTO dto = modelMapper.map(payment, CreatePaymentDTO.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error  al obtener el pago", e);
            throw new RuntimeException(PaymentConstants.PAYMENT_NOT_FOUND + " " + e.getMessage());
        }

    }

    @Override
    public List<CreatePaymentDTO> getAllPayments() {
        try{
            log.info("Get all payments");
            return paymentRepository.findAll()
                    .stream()
                    .map(payment -> modelMapper.map(payment,CreatePaymentDTO.class))
                    .collect(Collectors.toList());
        }catch(Exception e){
            log.error("Error  al obtener el pago", e);
            throw new RuntimeException(PaymentConstants.INVALID_DATA + " " +e.getMessage(),e);
        }
    }

    @Override
    public List<CreatePaymentDTO> getPaymentsStatus(String status) {
        List<Payment> payments = paymentRepository.findByPaymentStatusIgnoreCase(status);
        if(payments.isEmpty()){
            throw new ResourceNotFoundException("Payment", "status", status);
        }
        return payments
                .stream()
                .map(payment-> modelMapper.map(payment,CreatePaymentDTO.class))
                .collect(Collectors.toList());
    }
}
