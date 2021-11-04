package demo.service;

import demo.model.EscortGirl;
import demo.model.OrderStatus;
import demo.model.Pimp;
import demo.repository.EscortGirlsRepository;
import demo.repository.PimpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscortGirlsService {

    private EscortGirlsRepository girlsRepository;
    private OrderService orderService;
    private PimpRepository pimpRepository;

    public EscortGirlsService(EscortGirlsRepository girlsRepository, OrderService orderService, PimpRepository pimpRepository) {
        this.girlsRepository = girlsRepository;
        this.orderService = orderService;
        this.pimpRepository = pimpRepository;
    }

    public List<EscortGirl> getAllGirls() {
        return girlsRepository.findAll();
    }

    public List<EscortGirl> getFreeGirls() {
        return girlsRepository.findNotRentGirls();
    }


    public void createGirl(EscortGirl girl) {
        girlsRepository.save(girl);
    }

    public void changeGirlStatus(Long girlId, Long orderId) {
        EscortGirl girl = girlsRepository.getById(girlId);
        girl.setRent(orderService.getOrderById(orderId).getStatus() == OrderStatus.NOW);

        updateGirl(girl);
    }

    public void updateGirl(EscortGirl g) {
        girlsRepository.save(g);
    }

//    public void sendMoneyToPimp(Long girlId, int hours) {
//        EscortGirl girl = girlsRepository.getById(girlId);
//        Pimp pimp = pimpRepository.getPimpByGirlId(girlId);
//        pimp.setMoney(pimp.getMoney() + girl.getPrice()*hours);
//        pimpRepository.save(pimp);
//    }
}
