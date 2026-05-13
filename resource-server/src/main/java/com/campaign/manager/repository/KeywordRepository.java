package com.campaign.manager.repository;

import com.campaign.manager.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(c) FROM Campaign c JOIN c.keywords k WHERE k.id = :keywordId")
    long countCampaignsUsingKeyword(@Param("keywordId") Long keywordId);

    @Query("SELECT c.id FROM Campaign c JOIN c.keywords k WHERE k.id = :keywordId")
    List<Long> findCampaignIdsUsingKeyword(@Param("keywordId") Long keywordId);
}
