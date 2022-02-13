package com.frostbear.edu.kommunity.repo;

import com.frostbear.edu.kommunity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}