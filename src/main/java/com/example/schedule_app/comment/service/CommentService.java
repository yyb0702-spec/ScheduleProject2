package com.example.schedule_app.comment.service;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.comment.dto.*;
import com.example.schedule_app.comment.entity.Comment;
import com.example.schedule_app.comment.repository.CommentRepository;
import com.example.schedule_app.common.exception.CommentNotFoundException;
import com.example.schedule_app.common.exception.NotOwnerException;
import com.example.schedule_app.common.exception.ScheduleNotFoundException;
import com.example.schedule_app.common.exception.UserNotFoundException;
import com.example.schedule_app.schedule.entity.Schedule;
import com.example.schedule_app.schedule.repository.ScheduleRepository;
import com.example.schedule_app.user.entity.User;
import com.example.schedule_app.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    //────────────────────────────────────생성────────────────────────────────────
    @Transactional
    public CreateCommentResponse save(SessionUser sessionUser, Long scheduleId, @Valid CreateCommentRequest request) {
        User user = findUserById(sessionUser.id());
        Schedule schedule = findScheduleById(scheduleId);
        Comment comment = new Comment(user, schedule, request.getContent());
        Comment saveComment = commentRepository.save(comment);
        return new CreateCommentResponse(saveComment.getId(),
                saveComment.getContent(),
                saveComment.getCreatedAt(),
                saveComment.getModifiedAt());
    }

    //────────────────────────────────────조회────────────────────────────────────
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);

        return comments.stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .toList();
    }

    //────────────────────────────────────수정────────────────────────────────────
    @Transactional
    public UpdateCommentResponse update(SessionUser sessionUser, Long commentId, @Valid UpdateCommentRequest request) {
        Comment comment = findOwnerComment(commentId,sessionUser.id());

        comment.updateComment(request.getContent());
        return new UpdateCommentResponse(comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }

    //────────────────────────────────────삭제────────────────────────────────────
    @Transactional
    public void delete(SessionUser sessionUser, Long commentId) {
        Comment comment = findOwnerComment(commentId,sessionUser.id());

        commentRepository.deleteById(commentId);
    }

    private Comment findOwnerComment(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("없는 스케쥴 입니다"));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다."));
    }

}
