package com.mitocode.service.impl;

import com.mitocode.model.Client;
import com.mitocode.repository.interfaces.IClientRepo;
import com.mitocode.repository.interfaces.IGenericRepo;
import com.mitocode.service.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Esto para solo acepte en la inyección aquellos que tienen "final"
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements IClientService {

    private final IClientRepo repo; // Al heredar JpaRepository, Spring asume que necesitas una instancia aquí para persistir en db

    @Override
    protected IGenericRepo<Client, Integer> getRepo() {
        return repo;
    }

    // OBS: Lo comentado ya no es necesario, porque al extender de CRUDImpl<Client, Integer> ya se estarían generando los métodos necesarios
    /*
    @Override
    public Client save(Client client) throws Exception {
        return repo.save(client);
    }

    @Override
    public Client update(Integer id, Client client) throws Exception {
        client.setIdClient(id);
        return repo.save(client);
    }

    @Override
    public List<Client> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Client findById(Integer id) throws Exception {
        return repo.findById(id).orElse(new Client());
    }

    @Override
    public void delete(Integer id) throws Exception {
        repo.deleteById(id);
    }
    */
}
