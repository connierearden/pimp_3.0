package demo.repository;

import demo.model.Client;
import demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClientOrderById(Client client);

    Order findOrderByClientAndDate(Client client, LocalDateTime date);
}
