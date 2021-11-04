package demo.service;

import demo.model.Client;
import demo.model.Order;
import demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients () {
        return clientRepository.findAll();
    }

    public void addNewClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClientById (Long id) {
        return clientRepository.getById(id);
    }

    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    public boolean isTransactionAvailable (Long clientId, int price) {
        Client client = clientRepository.getById(clientId);
        return client.getMoney()>=price;
    }


    public void makeSuccessfulTransaction(Long clientId, int price) {
        Client client = clientRepository.getById(clientId);
        client.setMoney(client.getMoney()- price);
        clientRepository.save(client);
    }
}
