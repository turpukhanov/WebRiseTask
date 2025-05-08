package com.ambjorn.webrisetask.services;

import com.ambjorn.webrisetask.dto.UserDTO;
import com.ambjorn.webrisetask.exception.SubscriptionNotFoundException;
import com.ambjorn.webrisetask.exception.UserNotFoundException;
import com.ambjorn.webrisetask.mappers.UserMapper;
import com.ambjorn.webrisetask.models.Subscription;
import com.ambjorn.webrisetask.models.User;
import com.ambjorn.webrisetask.repositories.SubscriptionRepository;
import com.ambjorn.webrisetask.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userMapper = userMapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createUser(UserDTO userDTO) {
        User user = userMapper.toEntitySave(userDTO);
        userRepository.save(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void setSubscription(int userId, int subscriptionId) {
        userRepository.lockUserSubscription(userId);
        User user = userRepository.findByIdWithoutLock(userId).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        );
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> new SubscriptionNotFoundException("Подписки с таким id не найдено")
        );
        user.addSubscription(subscription);
        log.info("Пользователю id {} установлена подписка на {}", userId, subscription.getTitle());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteSubscription(int userId, int subscriptionId) {
        userRepository.lockUserSubscription(userId);
        User user = userRepository.findByIdWithoutLock(userId).orElseThrow(
                () -> new UserNotFoundException("Пользователь не найден")
        );
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(
                () -> new SubscriptionNotFoundException("Подписка не найдена")
        );
        user.getSubscriptions().remove(subscription);
        log.info("Подписка на {} успешно удалена", subscription.getTitle());
    }

    @Transactional
    public UserDTO getUserById(int id) {
        User user = userRepository.findByIdWithoutLock(id).orElseThrow(
                () -> new UserNotFoundException("Пользователь с таким id не найден")
        );
        return userMapper.toDTO(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateUserInfo(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(
                () -> new UserNotFoundException("Пользователь с таким id не найден")
        );
        userMapper.toEntityUpdate(userDTO, user);
        userRepository.save(user);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new UserNotFoundException("Нет пользователей");
        }
        return userList.stream().map(userMapper::toDTO).toList();
    }
}
