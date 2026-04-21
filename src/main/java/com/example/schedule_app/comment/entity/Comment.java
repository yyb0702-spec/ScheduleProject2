package com.example.schedule_app.comment.entity;

import com.example.schedule_app.common.entity.BaseEntity;
import com.example.schedule_app.schedule.entity.Schedule;
import com.example.schedule_app.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    private String content;

    public Comment(User user,Schedule schedule, String content)
    {
        this.user = user;
        this.schedule = schedule;
        this.content = content;
    }

    public void updateComment(String content)
    {

        if(content != null)this.content = content;
    }
}
