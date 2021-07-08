package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.FeedbackEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;

import java.util.List;

public interface IFeedbackService {
    // Create
    void create(FeedbackEntity feedbackEntity);

    //Read
    List<FeedbackEntity> getByRecommendations(RecommendationsEntity recommendationsEntity);

}
