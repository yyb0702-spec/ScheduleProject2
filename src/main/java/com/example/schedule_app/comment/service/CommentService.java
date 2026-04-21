package com.example.schedule_app.comment.service;

import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.comment.dto.*;
import com.example.schedule_app.comment.entity.Comment;
import com.example.schedule_app.comment.repository.CommentRepository;
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
        User user = userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다.")); //유저 확인
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("없는 스케쥴 입니다")); // 스케쥴 확인

        Comment comment = new Comment(user,schedule,request.getContent());
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
    public UpdateCommentResponse update(SessionUser sessionUser,Long commentId, @Valid UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("없는 댓글 입니다"));

        if (!comment.getUser().getId().equals(sessionUser.id())) {
            throw new NotOwnerException("본인 댓글만 수정할 수 있습니다.");
        }

        comment.updateComment(request.getContent());
        return new UpdateCommentResponse(comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
    //────────────────────────────────────삭제────────────────────────────────────
    @Transactional
    public void delete(SessionUser sessionUser,Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("없는 댓글 입니다"));
        if (!comment.getUser().getId().equals(sessionUser.id())) {
            throw new NotOwnerException("본인 댓글만 수정할 수 있습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
