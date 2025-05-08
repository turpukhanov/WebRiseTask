package com.ambjorn.webrisetask.repositories;

import com.ambjorn.webrisetask.models.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(int id);
    List<String> topThree();
}
