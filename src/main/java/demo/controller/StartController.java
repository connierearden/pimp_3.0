package demo.controller;

import demo.model.Client;
import demo.model.EscortGirl;
import demo.model.Pimp;
import demo.service.ClientService;
import demo.service.EscortGirlsService;
import demo.service.PimpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StartController {

    private EscortGirlsService girlsService;
    private PimpService pimpService;
    private ClientService clientService;

    public StartController(EscortGirlsService girlsService, PimpService pimpService, ClientService clientService) {
        this.girlsService = girlsService;
        this.pimpService = pimpService;
        this.clientService = clientService;
    }

    @GetMapping
    public String createStartData() {
        if (girlsService.getAllGirls().isEmpty()) {
            Client client = Client.builder().name("Danil").money(100000).build();
            clientService.addNewClient(client);
            Pimp pimp1 = new Pimp("Mike");
            Pimp pimp2 = new Pimp("Jordan");
            pimpService.createNewPimp(pimp1);
            pimpService.createNewPimp(pimp2);
            List<EscortGirl> girls = List.of(
                    new EscortGirl("Алиса", 32, 60, 170, 4),
                    new EscortGirl("Марина", 23, 50, 160, 3),
                    new EscortGirl("Мария", 22, 61, 171, 2),
                    new EscortGirl("Дарья", 22, 52, 164, 2),
                    new EscortGirl("Эля", 21, 59, 175, 3),
                    new EscortGirl("Нина", 25, 63, 174, 4),
                    new EscortGirl("Антонина", 28, 57, 163, 3),
                    new EscortGirl("Вика", 30, 66, 172, 4),
                    new EscortGirl("Настя", 36, 69, 178, 5),
                    new EscortGirl("Оксана", 31, 53, 158, 3),
                    new EscortGirl("Ксюша", 26, 50, 150, 1),
                    new EscortGirl("Аня", 20, 49, 154, 2),
                    new EscortGirl("Дарья", 19, 64, 169, 3),
                    new EscortGirl("Света", 33, 60, 159, 3),
                    new EscortGirl("Карина", 29, 55, 167, 1)
            );

            girls.forEach(g -> g.setPimp(g.getAge()>25 ? pimp1 : pimp2));
            girls.forEach(girlsService::createGirl);
        }
        return "redirect:/clients";
    }
}
