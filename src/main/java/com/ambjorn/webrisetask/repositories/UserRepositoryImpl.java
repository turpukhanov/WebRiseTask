package com.ambjorn.webrisetask.repositories;

import com.ambjorn.webrisetask.exception.UserDeleteException;
import com.ambjorn.webrisetask.exception.UserSaveException;
import com.ambjorn.webrisetask.models.User;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;
    private static final String LOADGRAPH = "jakarta.persistence.loadgraph";

    @Override
    public void save(User user) {
        try {
            User managerUser = em.merge(user);
            log.info("Сохранен пользователь: {}", managerUser);
        } catch (RuntimeException e) {
            log.error("Ошибка при сохранении пользователя: {}", user, e);
            throw new UserSaveException("Ошибка при сохранении пользователя", e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            log.info("Пользователь с id = {} не найден", id);
            return Optional.empty();
        }
    }

    public void lockUserSubscription(int userId) {
        em.createNativeQuery("""
                            SELECT 1
                            FROM user_subscriptions
                            WHERE user_id = :userId
                            FOR UPDATE
                        """)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<User> findByIdWithoutLock(int id) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .setHint(LOADGRAPH, getUserGraph())
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            log.info("Пользователь с id = {} не найден", id);
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class)
                .setHint(LOADGRAPH, getUserGraph())
                .getResultList();
        if (users.isEmpty()) {
            log.info("Пока нет ни одного пользователя");
        }
        return users;
    }

    @Override
    public void delete(int id) {
        findById(id).ifPresent(person -> {
            try {
                em.remove(person);
                log.info("Пользователь с id {} удален", id);
            } catch (RuntimeException e) {
                log.error("Ошибка при удалении пользователя с id = {}", id, e);
                throw new UserDeleteException("Ошибка при удалении пользователя", e);
            }
        });
    }

    private EntityGraph<?> getUserGraph() {
        return em.getEntityGraph("User.withSubscriptions");
    }
}
