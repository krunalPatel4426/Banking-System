package com.test.demo.Repository;

import com.test.demo.models.User;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = 1  where u.id = :id")
    int deleteUser(Long id);

    Optional<User> findByUsernameAndIsDeleted(String username, int isDeleted);
    Optional<User> findUserByEmailAndIsDeleted(String email, int isDeleted);

    Optional<User> findByUsernameOrEmailAndIsDeleted(String username, String email, int isDeleted);

    Optional<User> findUserByIdAndIsDeleted(Long id, int isDeleted);

    List<User> findUserByBalanceIsLessThanEqualAndIsDeleted(BigDecimal limit, int isDeleted);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id = :id AND u.isDeleted = 0")
    Optional<User> findByIdWithLock(Long id);
 }
