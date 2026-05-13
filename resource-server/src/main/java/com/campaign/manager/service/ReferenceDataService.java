package com.campaign.manager.service;

import com.campaign.manager.model.Keyword;
import com.campaign.manager.model.Town;
import com.campaign.manager.repository.KeywordRepository;
import com.campaign.manager.repository.TownRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceDataService {

    private final KeywordRepository keywordRepository;
    private final TownRepository townRepository;

    public ReferenceDataService(KeywordRepository keywordRepository, TownRepository townRepository) {
        this.keywordRepository = keywordRepository;
        this.townRepository = townRepository;
    }

    public List<Keyword> searchKeywords(String search) {
        return keywordRepository.findByNameContainingIgnoreCase(search);
    }

    public List<Town> getAllTowns() {
        return townRepository.findAll();
    }
}
