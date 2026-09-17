package com.fitness.activityservice;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepositery extends MongoRepository <Activity, String> {


}
