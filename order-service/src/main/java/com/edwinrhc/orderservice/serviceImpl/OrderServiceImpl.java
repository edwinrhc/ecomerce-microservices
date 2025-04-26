package com.edwinrhc.orderservice.serviceImpl;

import com.edwinrhc.orderservice.constants.OrderConstants;
import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.entity.Order;
import com.edwinrhc.orderservice.exception.ResourceNotFoundException;
import com.edwinrhc.orderservice.repository.OrderRepository;
import com.edwinrhc.orderservice.service.OrderService;
import com.edwinrhc.orderservice.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public ResponseEntity<String> createOrder(CreateOrderDTO createOrderDTO) {

        log.info("Create Order: {}", createOrderDTO);
        try{
            Order order = modelMapper.map(createOrderDTO, Order.class);
            order.setOrderDate(LocalDateTime.now());
            Order savedOrder = orderRepository.save(order);
            CreateOrderDTO savedOrderDTO = modelMapper.map(savedOrder, CreateOrderDTO.class);
            return OrderUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

        }catch (Exception e){
            log.error("Error al crear el order", e);
            e.printStackTrace();
        }
        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateOrder(UpdateOrderDTO updateOrderDTO) {
       log.info("Initial Update Order: {}", updateOrderDTO);
        try{
            Optional<Order> optionalOrder = orderRepository.findById(updateOrderDTO.getId());

            if(!optionalOrder.isPresent()){
                return OrderUtils.getResponseEntity(OrderConstants.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        Order existingOrder = optionalOrder.get();
            modelMapper.map(updateOrderDTO, existingOrder);
            orderRepository.save(existingOrder);
            log.info("Update Order: {}", existingOrder.getId());
            return OrderUtils.getResponseEntity(OrderConstants.ORDER_ALREADY_UPDATED,HttpStatus.OK);

        }catch (Exception e){
            log.error("Error al actualizar el order", e);
            e.printStackTrace();
        }

        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteOrder(Long orderId) {
        try{

            Order order = orderRepository.findById(orderId).
                    orElseThrow(() -> new ResourceNotFoundException("Order","id",orderId));
            orderRepository.delete(order);
            return OrderUtils.getResponseEntity(OrderConstants.ORDER_ALREADY_DELETED,HttpStatus.OK);

        }catch (ResourceNotFoundException e){
            log.warn("Order not found: {}", e.getMessage());
            return OrderUtils.getResponseEntity(OrderConstants.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error al eliminar el order", e);
            return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CreateOrderDTO> getOrderById(Long orderId) {
        log.info("Get Order by id: {}", orderId);
        try{
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order","id",orderId));

            CreateOrderDTO createOrderDTO = modelMapper.map(order, CreateOrderDTO.class);
            return new ResponseEntity<>(createOrderDTO, HttpStatus.OK);

        }catch (ResourceNotFoundException e){
            throw e;
        } catch(Exception e){
            log.error("Error al obtener el order", e);
            throw new RuntimeException(OrderConstants.ORDER_NOT_FOUND + " " + e.getMessage(),e);

        }
    }

    @Override
    public List<CreateOrderDTO> getAllOrders() {
      try{
          log.info("Get All Orders");
          return orderRepository.findAll()
                  .stream()
                  .map(order -> modelMapper.map(order,CreateOrderDTO.class))
                  .collect(Collectors.toList());
      }catch (Exception e){
          log.error("Error al obtener los ordenes", e);
          throw new RuntimeException(OrderConstants.INVALID_DATA +" "+ e.getMessage(),e);
      }
    }

    @Override
    public List<CreateOrderDTO> getOrdersByNumber(String orderNumber) {

        List<Order> orders = orderRepository.findByOrderNumberIgnoreCase(orderNumber);
        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Order","number",orderNumber);
        }
        return orders
                .stream()
                .map( order -> modelMapper.map(order,CreateOrderDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CreateOrderDTO> getOrdersByStatus(String orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatusIgnoreCase(orderStatus);
        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Order","status",orderStatus);
        }
        return orders
                .stream()
                .map( order -> modelMapper.map(order,CreateOrderDTO.class))
                .collect(Collectors.toList());
    }
}
