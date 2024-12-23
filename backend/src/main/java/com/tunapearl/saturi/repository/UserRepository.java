package com.tunapearl.saturi.repository;

import com.tunapearl.saturi.domain.user.Role;
import com.tunapearl.saturi.domain.user.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long saveUser(UserEntity user) {
        em.persist(user);
        return user.getUserId();
    }

    public Optional<UserEntity> findByUserId(Long userId) {
        return Optional.ofNullable(em.find(UserEntity.class, userId));
    }

    public Optional<List<UserEntity>> findByEmail(String email) {
        List<UserEntity> results =
                em.createQuery("select u from UserEntity u where u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultList();

        // 결과 리스트가 비어 있는 경우 Optional.empty() 반환
        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }

    public Optional<List<UserEntity>> findByNickname(String nickname) {
        List<UserEntity> results = em.createQuery("select u from UserEntity u where u.nickname = :nickname", UserEntity.class)
                .setParameter("nickname", nickname)
                .getResultList();

        // 결과 리스트가 비어 있는 경우 Optional.empty() 반환
        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }

    public Optional<List<UserEntity>> findByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(em.createQuery(
                "select u from UserEntity u" +
                    " where u.email = :email and u.password = :password", UserEntity.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList());
    }

    public Optional<List<UserEntity>> findAll() {
        return Optional.ofNullable(
                em.createQuery("select u from UserEntity u", UserEntity.class)
                .getResultList());
    }

    public Optional<List<UserEntity>> findAllSortedByExp() {
        return Optional.ofNullable(em.createQuery("select u from UserEntity u where u.isDeleted = false order by u.exp desc", UserEntity.class)
                .getResultList());
    }

    public Optional<List<UserEntity>> findAllExceptAdmin() {
        Role admin = Role.ADMIN;
        List<UserEntity> users = em.createQuery("select u from UserEntity u where u.role != :admin", UserEntity.class)
                .setParameter("admin", admin)
                .getResultList();

        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }
}