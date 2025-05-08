package com.ambjorn.webrisetask.controller;

import com.ambjorn.webrisetask.dto.SubscriptionDTO;
import com.ambjorn.webrisetask.services.SubscriptionService;
import com.ambjorn.webrisetask.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SubscriptionController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @PutMapping("/user/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<HttpStatus> subscribe(@PathVariable int userId, @PathVariable int subscriptionId) {
        log.info("Попытка подписать пользователя {} на сервис {}", userId, subscriptionId);
        userService.setSubscription(userId, subscriptionId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/subscriptions")
    public List<SubscriptionDTO> getSubscriptions(@PathVariable int userId) {
        log.info("Попытка запросить список подписок пользователя {}", userId);
        return subscriptionService.getSubscriptions(userId);
    }

    @DeleteMapping("/user/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<HttpStatus> unsubscribe(@PathVariable int userId, @PathVariable int subscriptionId) {
        log.info("Попытка отписать пользователя {} от сервиса {}", userId, subscriptionId);
        userService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/subscriptions/top")
    public List<String> getTopSubscriptions() {
        log.info("Попытка получить топ 3 сервисов");
        return subscriptionService.getTopThree();
    }
}
