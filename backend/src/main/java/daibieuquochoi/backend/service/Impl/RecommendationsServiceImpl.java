package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.repository.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daibieuquochoi.backend.service.IRecommendationsService;

@Service
public class RecommendationsServiceImpl implements IRecommendationsService {
    @Autowired
    RecommendationsRepository recommendationsRepository;

    @Override
    public void create(RecommendationsEntity recommendationsEntity) {
        this.recommendationsRepository.save(recommendationsEntity);
    }
}
