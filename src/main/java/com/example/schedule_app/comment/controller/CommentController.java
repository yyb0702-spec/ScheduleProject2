package com.example.schedule_app.comment.controller;


import com.example.schedule_app.auth.dto.SessionUser;
import com.example.schedule_app.comment.dto.*;
import com.example.schedule_app.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> saveComment(@SessionAttribute(name = "loginUser") SessionUser sessionUser ,@PathVariable Long scheduleId,@Valid @RequestBody CreateCommentRequest request)
    {
        CreateCommentResponse result = commentService.save(sessionUser,scheduleId,request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<GetCommentResponse>> getAllComment(
            @PathVariable Long scheduleId
    ) {
        List<GetCommentResponse> result = commentService.getAll(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@SessionAttribute(name = "loginUser") SessionUser sessionUser,@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request)
    {
        UpdateCommentResponse result = commentService.update(sessionUser,commentId,request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@SessionAttribute(name = "loginUser") SessionUser sessionUser,@PathVariable Long commentId)
    {
        commentService.delete(sessionUser,commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
