package com.campaign.manager.service;

import com.campaign.manager.dto.CampaignDTO;
import com.campaign.manager.dto.CampaignRequestDTO;
import com.campaign.manager.model.Campaign;
import com.campaign.manager.model.Keyword;
import com.campaign.manager.model.Town;
import com.campaign.manager.model.User;
import com.campaign.manager.repository.CampaignRepository;
import com.campaign.manager.repository.KeywordRepository;
import com.campaign.manager.repository.TownRepository;
import com.campaign.manager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final KeywordRepository keywordRepository;
    private final TownRepository townRepository;
    private final UserRepository userRepository;
    private final EmeraldAccountService emeraldAccountService;

    public CampaignService(CampaignRepository campaignRepository, KeywordRepository keywordRepository, TownRepository townRepository, UserRepository userRepository, EmeraldAccountService emeraldAccountService) {
        this.campaignRepository = campaignRepository;
        this.keywordRepository = keywordRepository;
        this.townRepository = townRepository;
        this.userRepository = userRepository;
        this.emeraldAccountService = emeraldAccountService;
    }

    public List<CampaignDTO> getAllCampaigns() {

        return campaignRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public CampaignDTO getCampaignById(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));

        return mapToDTO(campaign);
    }

    @Transactional
    public CampaignDTO createCampaign(CampaignRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Town town = townRepository.findById(request.getTownId())
                .orElseThrow(() -> new EntityNotFoundException("Town not found"));

        Set<Keyword> keywords = request.getKeywordIds()
                .stream()
                .map(id -> keywordRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Keyword not found")))
                .collect(Collectors.toSet());

        emeraldAccountService.deductFunds(
                user.getEmeraldAccount(),
                request.getCampaignFund()
        );

        Campaign campaign = new Campaign();

        campaign.setCampaignName(request.getCampaignName());
        campaign.setKeywords(keywords);
        campaign.setBidAmount(request.getBidAmount());
        campaign.setCampaignFund(request.getCampaignFund());
        campaign.setStatus(request.getStatus());
        campaign.setTown(town);
        campaign.setRadius(request.getRadius());
        campaign.setUser(user);

        Campaign saved = campaignRepository.save(campaign);

        return mapToDTO(saved);
    }

    @Transactional
    public void deleteCampaign(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));

        emeraldAccountService.refundFunds(
                campaign.getUser().getEmeraldAccount(),
                campaign.getCampaignFund()
        );

        campaignRepository.delete(campaign);
    }

    @Transactional
    public CampaignDTO updateCampaign(Long id, CampaignRequestDTO request) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found"));

        Town town = townRepository.findById(request.getTownId())
                .orElseThrow(() -> new EntityNotFoundException("Town not found"));

        Set<Keyword> keywords = request.getKeywordIds()
                .stream()
                .map(keywordId -> keywordRepository.findById(keywordId)
                        .orElseThrow(() -> new EntityNotFoundException("Keyword not found")))
                .collect(Collectors.toSet());

        User user = campaign.getUser();

        BigDecimal oldFund = campaign.getCampaignFund();
        BigDecimal newFund = request.getCampaignFund();

        BigDecimal difference = newFund.subtract(oldFund);

        if (difference.compareTo(BigDecimal.ZERO) > 0) {

            emeraldAccountService.deductFunds(
                    user.getEmeraldAccount(),
                    difference
            );

        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {

            emeraldAccountService.refundFunds(
                    user.getEmeraldAccount(),
                    difference.abs()
            );
        }

        campaign.setCampaignName(request.getCampaignName());
        campaign.setKeywords(keywords);
        campaign.setBidAmount(request.getBidAmount());
        campaign.setCampaignFund(newFund);
        campaign.setStatus(request.getStatus());
        campaign.setTown(town);
        campaign.setRadius(request.getRadius());

        Campaign updated = campaignRepository.save(campaign);

        return mapToDTO(updated);
    }

    private CampaignDTO mapToDTO(Campaign campaign) {

        CampaignDTO dto = new CampaignDTO();

        dto.setId(campaign.getId());
        dto.setCampaignName(campaign.getCampaignName());

        dto.setKeywords(
                campaign.getKeywords()
                        .stream()
                        .map(Keyword::getName)
                        .collect(Collectors.toSet())
        );

        dto.setBidAmount(campaign.getBidAmount());
        dto.setCampaignFund(campaign.getCampaignFund());
        dto.setStatus(campaign.getStatus());
        dto.setTown(campaign.getTown().getName());
        dto.setRadius(campaign.getRadius());
        dto.setUsername(campaign.getUser().getUsername());

        return dto;
    }

}
