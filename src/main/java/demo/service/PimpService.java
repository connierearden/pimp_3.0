package demo.service;


import demo.model.EscortGirl;
import demo.model.Order;
import demo.model.Pimp;
import demo.repository.EscortGirlsRepository;
import demo.repository.PimpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PimpService {
    @Autowired
    private PimpRepository pimpRepository;
    @Autowired
    private EscortGirlsRepository girlsRepository;

    public List<Pimp> getAllPimps() {
        return pimpRepository.findAll();
    }

    public void createNewPimp(Pimp pimp) {
        pimpRepository.save(pimp);
    }

    public void updatePimp(Pimp pimp) {
        pimpRepository.save(pimp);
    }

    public Pimp getPimpById(Long id) {
        return pimpRepository.getById(id);
    }

    public List<Order> getOrdersForPimp(Long pimpId) {
        return pimpRepository.getOrderListForPimp(pimpId);
    }

    // бля, такая красота оказалась ненужной ((
//    public int getTodayProfitForOrders(Long pimpId) {
//        int finalProfit = 0;
//        List<Order> todayOrders = getOrdersForPimp(pimpId).stream()
//                .filter(o -> o.getCreated().getDayOfYear() == LocalDateTime.now().getDayOfYear())
//                .collect(Collectors.toList());
//
//        for (Order order : todayOrders) {
//            int orderSum = order.getGirls().stream()
//                    .filter(g -> g.getPimp().getId() == pimpId)
//                    .mapToInt(EscortGirl::getPrice).sum()*order.getHours();
//            finalProfit+=orderSum;
//        }
//        return finalProfit;
//
//    }

    public List<EscortGirl> getPimpGirls(Long pimpId) {
        return girlsRepository.findAllByPimpOrderById(pimpRepository.getById(pimpId));
    }


}
