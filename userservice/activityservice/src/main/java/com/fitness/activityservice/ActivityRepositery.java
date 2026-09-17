package com.fitness.activityservice;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepositery extends MongoRepository <Activity, String> {


    List<Activity> findByUserId(String userId);

}
