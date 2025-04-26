package com.edwinrhc.orderservice.restImpl;

import com.edwinrhc.orderservice.constants.OrderConstants;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.rest.OrderRest;
import com.edwinrhc.orderservice.service.OrderService;
import com.edwinrhc.orderservice.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestImpl implements OrderRest {

    @Autowired
    OrderService orderService;


    @Override
    public ResponseEntity<String> createOrder(CreateOrderDTO dto) {
        try{
            return orderService.createOrder(dto);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
