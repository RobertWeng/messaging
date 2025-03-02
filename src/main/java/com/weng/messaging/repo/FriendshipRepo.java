package com.weng.messaging.repo;

import com.weng.messaging.model.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship, Long> {
}