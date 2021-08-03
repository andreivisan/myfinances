package io.programminglife.myfinances.service;

import java.util.List;

import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.exception.MyFinancesException;

public interface ClientService {
    
    List<Client> findAll();

    Client findClientById(Long clientId) throws MyFinancesException;

    Client findClientByEmail(String email) throws MyFinancesException;

    Client saveClient(Client client);

    void deleteClient(Long clientId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}