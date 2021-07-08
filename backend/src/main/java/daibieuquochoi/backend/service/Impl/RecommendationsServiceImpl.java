package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.repository.RecommendationsRepository;
import daibieuquochoi.backend.service.IRecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationsServiceImpl implements IRecommendationsService {
    @Autowired
    RecommendationsRepository recommendationsRepository;

    @Override
    public void create(RecommendationsEntity recommendationsEntity) {
        this.recommendationsRepository.save(recommendationsEntity);
    }

    @Override
    public Optional<RecommendationsEntity> findByID(long id) {
        return recommendationsRepository.findById(id);
    }

    @Override
    public List<RecommendationsEntity> getRecommendationsAll() {
        return recommendationsRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
    }

    @Override
    public List<RecommendationsEntity> search(String keyword) {
        System.out.println(keyword);
        if (keyword != null) {
            return recommendationsRepository.search(keyword);
        }
        return recommendationsRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
    }
}
