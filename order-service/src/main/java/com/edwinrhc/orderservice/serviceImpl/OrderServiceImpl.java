package com.edwinrhc.orderservice.serviceImpl;

import com.edwinrhc.orderservice.constants.OrderConstants;
import com.edwinrhc.common.dto.ApiResponse;

import com.edwinrhc.orderservice.dto.order.CreateOrderDTO;
import com.edwinrhc.orderservice.dto.order.OrderResponseDTO;
import com.edwinrhc.orderservice.dto.order.UpdateOrderDTO;
import com.edwinrhc.orderservice.dto.payment.PaymentDTO;
import com.edwinrhc.orderservice.dto.product.ProductDTO;
import com.edwinrhc.orderservice.entity.Order;
import com.edwinrhc.orderservice.exception.ResourceNotFoundException;
import com.edwinrhc.orderservice.feign.PaymentClient;
import com.edwinrhc.orderservice.feign.ProductClient;
import com.edwinrhc.orderservice.repository.OrderRepository;
import com.edwinrhc.orderservice.service.OrderService;
import com.edwinrhc.orderservice.utils.OrderUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    @Autowired
    private ProductClient productClient;
    @Autowired
    private PaymentClient paymentClient;


/*
    @Override
    public ResponseEntity<String> createOrder(CreateOrderDTO createOrderDTO) {

        log.info("Create Order: {}", createOrderDTO);
        try{

            ProductDTO product = productClient.getProductById(createOrderDTO.getProductId()).getBody();

            if(product == null){
                return OrderUtils.getResponseEntity("Producto no encontrado",HttpStatus.NOT_FOUND);
            }

            BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(createOrderDTO.getProductQuantity()));

            Order order = modelMapper.map(createOrderDTO, Order.class);
            order.setOrderNumber(generarOrderNumber());
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus("PENDING");
            order.setTotalAmount(total);

            Order savedOrder = orderRepository.save(order);
            CreateOrderDTO savedOrderDTO = modelMapper.map(savedOrder, CreateOrderDTO.class);
            return OrderUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

        }catch (Exception e){
            log.error("Error al crear el order", e);
            e.printStackTrace();
        }
        return OrderUtils.getResponseEntity(OrderConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/

    @Override
    public ResponseEntity<ApiResponse> createOrder(CreateOrderDTO createOrderDTO) {
        log.info("Create Order: {}", createOrderDTO);

        try {
            // Obtener información del producto desde product-service
            ProductDTO product = productClient.getProductById(createOrderDTO.getProductId()).getBody();

            if (product == null) {
                return OrderUtils.apiResponseEntity("Producto no encontrado", null, HttpStatus.NOT_FOUND);
            }

            // Calcular total
            BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(createOrderDTO.getProductQuantity()));

            // Crear y guardar la orden
            Order order = modelMapper.map(createOrderDTO, Order.class);
            order.setOrderNumber(generarOrderNumber());
            order.setOrderDate(LocalDateTime.now());
            order.setOrderStatus("PENDING");
            order.setTotalAmount(total);

            Order savedOrder = orderRepository.save(order);

            // Preparar la respuesta usando OrderResponseDTO
            OrderResponseDTO responseDTO = new OrderResponseDTO();
            responseDTO.setId(savedOrder.getId());
            responseDTO.setOrderNumber(savedOrder.getOrderNumber());
            responseDTO.setOrderDate(savedOrder.getOrderDate());
            responseDTO.setTotalAmount(savedOrder.getTotalAmount());
            responseDTO.setOrderStatus(savedOrder.getOrderStatus());
            responseDTO.setProductId(createOrderDTO.getProductId());
            responseDTO.setProductQuantity(createOrderDTO.getProductQuantity());

            return OrderUtils.apiResponseEntity(
                    "Orden registrada exitosamente",
                    responseDTO,
                    HttpStatus.CREATED
            );

        } catch (FeignException.NotFound ex) {
            log.warn("Producto no encontrado en product-service: {}", createOrderDTO.getProductId());
            return OrderUtils.apiResponseEntity("Producto no encontrado", null, HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            log.error("Error al registrar orden: {}", ex.getMessage(), ex);
            return OrderUtils.apiResponseEntity("Error al registrar la orden", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<ApiResponse> deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new ResourceNotFoundException("Order","id",orderId));
        // Mapear antes de eliminar
        CreateOrderDTO orderDTO = modelMapper.map(order, CreateOrderDTO.class);
        orderRepository.delete(order);
        return OrderUtils.apiResponseEntity(
                OrderConstants.ORDER_ALREADY_DELETED,
                orderDTO,
                HttpStatus.OK
        );
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


    @Override
    public ProductDTO obtenerProductoPorId(Long id) {
        return productClient.getProductById(id).getBody();
    }

    @Override
    public ResponseEntity<ApiResponse> createPayment(PaymentDTO paymentDTO) {
        try{
            ResponseEntity<String> response = paymentClient.createPayment(paymentDTO);
            ApiResponse apiResponse = new ApiResponse(OrderConstants.PAYMENT_GENERATION_SUCCESSFULLY, response.getBody());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("Error al generar el pago", e);
            ApiResponse error = new ApiResponse(OrderConstants.PAYMENT_GENERATION_ERROR, null);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String generarOrderNumber(){
        String lastOrder = orderRepository.findLastOrderNumber();
        int nextNumber = 1;
        if(lastOrder != null && lastOrder.startsWith("ORD-")){
            String numberPart = lastOrder.substring(4);
            try{
                nextNumber = Integer.parseInt(numberPart) + 1;
            }catch (NumberFormatException e){
                nextNumber = 1;
            }
        }
        return String.format("ORD-%03d", nextNumber);
    }

}
