package com.ambjorn.webrisetask.repositories;

import com.ambjorn.webrisetask.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(int id);
    List<User> findAll();
    void delete(int id);
    Optional<User> findByIdWithoutLock(int id);
    public void lockUserSubscription(int userId);
}
