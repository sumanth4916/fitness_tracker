package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    GeminiService geminiService;
    public String generateRecommendation (Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("Gemini response is {}", aiResponse);
        return aiResponse;
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
