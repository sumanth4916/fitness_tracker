package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repositery.RecommendationsRepositerty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationsRepositerty recommendationsRepositerty;
    public List<Recommendation> getUserRecommnedations(String userId) {

        return recommendationsRepositerty.findByUserId(userId);
    }

    public Recommendation getActivityRecommnedations(String activityId) {
        return recommendationsRepositerty.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("no recommendations"));
    }
}
