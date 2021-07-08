package daibieuquochoi.backend.repository;

import daibieuquochoi.backend.entity.RecommendationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.FeedbackEntity;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>{
    List<FeedbackEntity> findByRecommendationsOrderByIdDesc(RecommendationsEntity recommendationsEntity);
}
