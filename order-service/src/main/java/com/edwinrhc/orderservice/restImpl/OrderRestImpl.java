package com.edwinrhc.orderservice.restImpl;

import com.edwinrhc.common.dto.ApiResponse;
import com.edwinrhc.orderservice.constants.OrderConstants;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import com.edwinrhc.orderservice.dto.product.ProductDTO;
import com.edwinrhc.orderservice.rest.OrderRest;
import com.edwinrhc.orderservice.service.OrderService;
import com.edwinrhc.orderservice.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ResponseEntity<String> updateOrder(UpdateOrderDTO dto) {
        try{
            return orderService.updateOrder(dto);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<CreateOrderDTO>> getAllOrders() {
       try{
           return new  ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
       }catch(Exception ex){
           ex.printStackTrace();
           return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Override
    public ResponseEntity<List<CreateOrderDTO>> getByOrderNum(String orderNum) {
        List<CreateOrderDTO> orders = orderService.getOrdersByNumber(orderNum);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CreateOrderDTO>> getByOrderStatus(String orderStatus) {
        List<CreateOrderDTO> orders = orderService.getOrdersByStatus(orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CreateOrderDTO> getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    @Override
    public ResponseEntity<String> deleteOrderById(Long id) {
        try{
            return  orderService.deleteOrder(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductDTO> obtenerProducto(Long id) {
        ProductDTO producto = orderService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @Override
    public ResponseEntity<ApiResponse> createPayment(PaymentDTO dto) {
        try{
            return orderService.createPayment(dto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return OrderUtils.getApiResponse(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
