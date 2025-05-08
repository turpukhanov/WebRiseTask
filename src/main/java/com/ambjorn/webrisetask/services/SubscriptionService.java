package com.ambjorn.webrisetask.services;

import com.ambjorn.webrisetask.dto.SubscriptionDTO;
import com.ambjorn.webrisetask.repositories.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(UserService userService, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getSubscriptions(int id) {
        List<SubscriptionDTO> subscriptions = userService.getUserById(id).getSubscriptions();
        if (subscriptions == null || subscriptions.isEmpty()) {
            log.info("У пользователя {} нет подписок", id);
            return new ArrayList<>();
        }
        return subscriptions;
    }

    @Transactional(readOnly = true)
    public List<String> getTopThree() {
        return subscriptionRepository.topThree();
    }

}
