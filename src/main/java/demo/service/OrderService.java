package demo.service;

import demo.model.Client;
import demo.model.Order;
import demo.model.OrderStatus;
import demo.repository.ClientRepository;
import demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public void createNewOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

    public Order getOrderByParameters(Client c, LocalDateTime d) {
        return orderRepository.findOrderByClientAndDate(c, d);
    }


    public List<Order> getClientOrders(Client client) {
        return orderRepository.findAllByClientOrderById(client);
    }

    public void setClientAndStatusToOrder(Long orderId, Long clientId) {
        Order order = orderRepository.getById(orderId);
        Client client = clientRepository.getById(clientId);
        order.setClient(client);

        LocalDateTime date = order.getDate();
        LocalDateTime dateWithOrderPeriod = date.plusHours(order.getHours());
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(date) && now.isBefore(dateWithOrderPeriod)) {
            order.setStatus(OrderStatus.NOW);
        } else if (dateWithOrderPeriod.isAfter(now)) {
            order.setStatus(OrderStatus.FUTURE);
        } else {
            order.setStatus(OrderStatus.PAST);
        }
//        updateOrder(order);
    }
}
