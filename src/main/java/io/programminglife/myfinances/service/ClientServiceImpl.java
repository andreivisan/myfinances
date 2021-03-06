package io.programminglife.myfinances.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.exception.MyFinancesException;
import io.programminglife.myfinances.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(Long clientId) throws MyFinancesException {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new MyFinancesException(String.format("Client with id %s was not found", clientId)));
    }

    @Override
    public Client findClientByEmail(String email) throws MyFinancesException {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new MyFinancesException(String.format("Client with email %s was not found", email)));
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

}