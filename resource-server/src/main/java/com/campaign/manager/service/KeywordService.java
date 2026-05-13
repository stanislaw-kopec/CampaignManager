package com.campaign.manager.service;

import com.campaign.manager.model.Keyword;
import com.campaign.manager.repository.KeywordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Transactional(readOnly = true)
    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Keyword> searchKeywords(String search) {
        return keywordRepository.findByNameContainingIgnoreCase(search);
    }

    @Transactional(readOnly = true)
    public Keyword getKeywordById(Long id) {
        return keywordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Keyword not found with id: " + id));
    }

    public Keyword createKeyword(String name) {
        Keyword keyword = new Keyword(name);
        return keywordRepository.save(keyword);
    }

    public Keyword updateKeyword(Long id, String newName) {
        Keyword keyword = getKeywordById(id);
        keyword.setName(newName);
        return keywordRepository.save(keyword);
    }

    public void deleteKeyword(Long id) {
        if (!keywordRepository.existsById(id)) {
            throw new EntityNotFoundException("Keyword not found with id: " + id);
        }
        keywordRepository.deleteById(id);
    }
}
