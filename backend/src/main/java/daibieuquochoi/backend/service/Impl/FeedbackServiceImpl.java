package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.FeedbackEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.repository.FeedbackRepository;
import daibieuquochoi.backend.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public void create(FeedbackEntity feedbackEntity) {
        feedbackRepository.save(feedbackEntity);
    }

    @Override
    public List<FeedbackEntity> getByRecommendations(RecommendationsEntity recommendationsEntity, String status) {
        return feedbackRepository.findByRecommendationsAndStatusOrderByIdDesc(recommendationsEntity, status);
    }
}
