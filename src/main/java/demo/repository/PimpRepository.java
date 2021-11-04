package demo.repository;

import demo.model.EscortGirl;
import demo.model.Order;
import demo.model.Pimp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PimpRepository extends JpaRepository<Pimp, Long> {

    //query for many-to-many relationship
    @Query("select o from Order o join o.girls es where es.pimp.id = :id")
    List<Order> getOrderListForPimp(Long id);

    @Query("select p from Pimp p join p.escortGirlList es where es.id = :girlId")
    Pimp getPimpByGirlId (Long girlId);
}
