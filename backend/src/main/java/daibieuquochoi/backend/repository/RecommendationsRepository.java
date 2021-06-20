package daibieuquochoi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import daibieuquochoi.backend.entity.RecommendationsEntity;

@Repository
public interface RecommendationsRepository extends JpaRepository<RecommendationsEntity, Long>{

}
