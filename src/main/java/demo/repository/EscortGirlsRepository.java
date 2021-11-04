package demo.repository;

import demo.model.EscortGirl;
import demo.model.Pimp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EscortGirlsRepository extends JpaRepository<EscortGirl,Long> {
    @Query("from EscortGirl e where e.isRent = false order by e.name")
    List<EscortGirl> findNotRentGirls();

    List<EscortGirl> findAllByPimpOrderById(Pimp p);



}
