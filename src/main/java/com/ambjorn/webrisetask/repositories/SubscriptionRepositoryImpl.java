package com.ambjorn.webrisetask.repositories;

import com.ambjorn.webrisetask.models.Subscription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Subscription> findById(int id) {
        try {
            Subscription subscription = em.createQuery(
                            "SELECT s FROM Subscription s WHERE s.id = :id", Subscription.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(subscription);
        } catch (NoResultException e) {
            log.info("Подписок с id = {} не найдено", id);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> topThree() {
        List<String> subscriptions =
        em.createNativeQuery("""
                SELECT s.title from subscriptions s
                inner join user_subscriptions us on us.subscription_id = s.id
                group by s.title
                order by count(us.user_id) desc
                limit 3
                """, String.class
        ).getResultList();
        if (subscriptions.isEmpty()) {
            log.info("В топе пока ничего нет");
        }
        return subscriptions;
    }
}
