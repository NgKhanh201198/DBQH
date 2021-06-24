package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;

import java.util.List;
import java.util.Optional;

public interface IRecommendationsService {
    // Create
    void create(RecommendationsEntity recommendationsEntity);

}
