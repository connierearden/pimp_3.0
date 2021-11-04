package demo.controller;


import demo.model.Order;
import demo.model.Pimp;
import demo.service.EscortGirlsService;
import demo.service.OrderService;
import demo.service.PimpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("pimps")
public class PimpController {

    private PimpService pimpService;
    private OrderService orderService;
    private EscortGirlsService girlsService;

    public PimpController(PimpService pimpService, OrderService orderService, EscortGirlsService girlsService) {
        this.pimpService = pimpService;
        this.orderService = orderService;
        this.girlsService = girlsService;
    }

    @GetMapping
    public String getPimpList(Model model) {
        model.addAttribute("pimps", pimpService.getAllPimps());
        return "pimp/pimp-list";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "pimp/pimp-create";
    }

    @PostMapping("/create")
    public String createPimp(@ModelAttribute Pimp pimp) {
        pimpService.createNewPimp(pimp);
        return "redirect:/pimps";
    }

    @GetMapping("/info/pimp_id={id}")
    public String getInfoPage(@PathVariable Long id, Model model) {
        model.addAttribute("pimp", pimpService.getPimpById(id));
        model.addAttribute("pimpOrders", pimpService.getOrdersForPimp(id));
        model.addAttribute("pimpGirls", pimpService.getPimpGirls(id));
//        model.addAttribute("todayMoney", pimpService.getTodayProfitForOrders(id));
        return "pimp/pimp-info";
    }

    @GetMapping("/info/pimp_id={pimpId}/order/order_id={orderId}")
    public String processOrder(@PathVariable Long pimpId, @PathVariable Long orderId) {
        final double PRICE_PARTS = 0.5;
        Order order = orderService.getOrderById(orderId);
        int orderPrice = order.getPrice();
        if (!order.isProcessed()) {
            order.getGirls().forEach(g -> g.setWallet(g.getWallet() + orderPrice * PRICE_PARTS));
            order.setProcessed(true);
            orderService.updateOrder(order);
            Pimp pimp = pimpService.getPimpById(pimpId);
            pimp.setMoney(pimp.getMoney() + orderPrice * PRICE_PARTS);
            pimpService.updatePimp(pimp);
        }
        return "redirect:/pimps/info/pimp_id=" + pimpId;
    }


}
