package com.campaign.manager.service;

import com.campaign.manager.model.Town;
import com.campaign.manager.repository.TownRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TownService {

    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Transactional(readOnly = true)
    public List<Town> getAllTowns() {
        return townRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Town getTownById(Long id) {
        return townRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Town not found with id: " + id));
    }

    public Town createTown(String name) {
        Town town = new Town(name);
        return townRepository.save(town);
    }

    public Town updateTown(Long id, String newName) {
        Town town = getTownById(id);
        town.setName(newName);
        return townRepository.save(town);
    }

    public void deleteTown(Long id) {
        if (!townRepository.existsById(id)) {
            throw new EntityNotFoundException("Town not found with id: " + id);
        }
        townRepository.deleteById(id);
    }

}
