package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;

import java.util.List;
import java.util.Optional;

public interface IRecommendationsService {
    // Create
    void create(RecommendationsEntity recommendationsEntity);

    // Read
    Optional<RecommendationsEntity> findByID(long id);

    List<RecommendationsEntity> getRecommendationsAll();

    List<RecommendationsEntity> getRecommendationsByAgency(AgencyEntity agencyEntity);

    List<RecommendationsEntity> search(String keyword);

    //Update
    void update(RecommendationsEntity recommendationsEntity);

    void updateStatus(long id, String status);
}
