package demo.controller;

import demo.model.Client;
import demo.model.EscortGirl;
import demo.model.MoneyForDifferentOperation;
import demo.model.Order;
import demo.service.ClientService;
import demo.service.EscortGirlsService;
import demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("clients")
public class ClientController {

    private ClientService clientService;
    private EscortGirlsService girlsService;
    private OrderService orderService;

    public ClientController(ClientService clientService, EscortGirlsService girlsService, OrderService orderService) {
        this.clientService = clientService;
        this.girlsService = girlsService;
        this.orderService = orderService;
    }

    @GetMapping
    public String showStartPage(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "client/client-list";
    }

    @GetMapping("/create")
    public String returnPageForNew() {
        return "client/client-create";
    }

    @PostMapping("/create")
    public String getNew(Client client) {
        clientService.addNewClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/update/user_id={id}")
    public String returnUpPage(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.getClientById(id));
        return "client/client-update";
    }

    @PostMapping("/update")
    public String updateClient(Client client) {
        clientService.updateClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/info/user_id={id}")
    public String getInfoPage(Model model, @PathVariable Long id) {
        model.addAttribute("client", clientService.getClientById(id));
        model.addAttribute("orders", orderService.getClientOrders(clientService.getClientById(id)));
        return "client/client-info";
    }

    @GetMapping("/info/user_id={id}/balance")
    public String getBalancePage(Model model, @PathVariable Long id) {
        model.addAttribute("client", clientService.getClientById(id));
        return "client/client-balance";
    }

    @PostMapping("/info/user_id={id}/balance/update")
    public String updateBalance(@PathVariable final Long id,
                                @ModelAttribute("moneyForDifferentOperation") MoneyForDifferentOperation money) {
        Client clientById = clientService.getClientById(id);
        clientById.setMoney(clientById.getMoney() + money.getMoney());
        clientService.updateClient(clientById);
        return "redirect:/clients/info/user_id=" + id;
    }

    @GetMapping("/info/user_id={id}/order/create")
    public String getOrderPage(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.getClientById(id));
        model.addAttribute("listOfGirls", girlsService.getFreeGirls());
        return "client/client-order";
    }

    @PostMapping("/info/user_id={clientId}/order/create")
    public String getOrderPage(@PathVariable Long clientId, @ModelAttribute("order") Order order) {
        int price = order.getGirls().stream().mapToInt(EscortGirl::getPrice).sum() * order.getHours();
//        order.setPrice(price);
        String returnedPage = "";

//        orderService.createNewOrder(order);

        if (clientService.isTransactionAvailable(clientId, price)) {
            clientService.makeSuccessfulTransaction(clientId, price);
            for (EscortGirl girl : order.getGirls()) {
                Order newOrder = new Order();
                newOrder.setCreated(LocalDate.now());
                newOrder.setHours(order.getHours());
                newOrder.setDate(order.getDate());
                newOrder.setGirls(List.of(girl));
                newOrder.setPrice(girl.getPrice()*newOrder.getHours());



                orderService.createNewOrder(newOrder);

                Order freshOrder = orderService.getOrderByParameters(newOrder.getClient(), newOrder.getDate());
                orderService.setClientAndStatusToOrder(freshOrder.getId(), clientId);

                girlsService.changeGirlStatus(girl.getId(), freshOrder.getId());
//                girlsService.sendMoneyToPimp(girl.getId(), order.getHours());

                returnedPage = "redirect:/clients/info/user_id=" + clientId;


            }
        } else {
            returnedPage = "redirect:/clients/info/user_id=" + clientId + "/balance";
        }
        return returnedPage;
    }

    @GetMapping("info/user_id={id}/update")
    public String updateClientInfo(@PathVariable Long id) {
        List<Order> orders = orderService.getClientOrders(clientService.getClientById(id));
        orders.forEach(o -> {
                    orderService.setClientAndStatusToOrder(o.getId(), id);
                    orderService.updateOrder(o);
                    o.getGirls().forEach(g -> girlsService.changeGirlStatus(g.getId(), o.getId()));
                }
        );
        return "redirect:/clients/info/user_id=" + id;
    }
}
