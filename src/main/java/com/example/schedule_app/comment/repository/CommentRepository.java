package com.example.schedule_app.comment.repository;

import com.example.schedule_app.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByScheduleId(Long scheduleId);//Select * from comment where schedule_id
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);// Select * from comment where id=? and user_id=?
    void deleteAllByScheduleId(Long scheduleId);//delete from comment where schedule_id
}
