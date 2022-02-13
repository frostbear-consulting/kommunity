package com.frostbear.edu.kommunity.repo;

import com.frostbear.edu.kommunity.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThreadRepository extends JpaRepository<Thread, UUID> {
}