package com.api.toolscript.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.toolscript.models.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
