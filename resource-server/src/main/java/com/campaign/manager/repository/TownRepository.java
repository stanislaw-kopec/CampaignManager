package com.campaign.manager.repository;

import com.campaign.manager.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    List<Town> findAllByOrderByName();

    @Query("SELECT COUNT(c) FROM Campaign c WHERE c.town.id = :townId")
    long countCampaignsUsingTown(@Param("townId") Long townId);

    @Query("SELECT c.id FROM Campaign c WHERE c.town.id = :townId")
    List<Long> findCampaignIdsUsingTown(@Param("townId") Long townId);
}
