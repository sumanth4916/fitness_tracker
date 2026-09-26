package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repositery.RecommendationsRepositerty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;
    private final RecommendationsRepositerty recommendationsRepositerty;
    private final ObjectMapper objectMapper;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("Gemini response is {}", aiResponse);

      try {
        JsonNode response = objectMapper.readTree(aiResponse);
        String recommendationJson = response.path("candidates").path(0)
            .path("content").path("parts").path(0).path("text").asText();
        Recommendation recommendation = objectMapper.readValue(
            recommendationJson, Recommendation.class);
        recommendation.setActivityId(activity.getId());
        recommendation.setUserId(activity.getUserId());
        recommendation.setActivityType(activity.getType());
        return recommendationsRepositerty.save(recommendation);
      } catch (Exception exception) {
        throw new IllegalStateException("Could not parse or save Gemini recommendation", exception);
      }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze the following fitness activity and provide useful recommendations.

        Activity:
        {
          "activityId": "%s",
          "userId": "%s",
          "activityType": "%s",
          "duration": %d,
          "caloriesBurned": %d,
          "startTime": "%s",
          "additionalMetrics": %s
        }

        Return only valid JSON. Do not use Markdown or code fences.

        The response must follow this exact structure:
        {
          "activityId": "%s",
          "userId": "%s",
          "activityType": "%s",
          "recommendations": "A concise recommendation",
          "improvements": [
            "Improvement 1",
            "Improvement 2"
          ],
          "suggestions": [
            "Suggestion 1",
            "Suggestion 2"
          ],
          "safety": [
            "Safety advice 1",
            "Safety advice 2"
          ]
        }

        Rules:
        - recommendations must be a concise summary.
        - improvements must contain practical ways to improve performance.
        - suggestions must contain useful next-step suggestions.
        - safety must contain relevant safety advice.
        - Return JSON only.
        """.formatted(
                activity.getId(),
                activity.getUserId(),
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMetrics(),
                activity.getId(),
                activity.getUserId(),
                activity.getType()
        ));
    }
}
