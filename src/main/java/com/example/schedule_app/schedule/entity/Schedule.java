package com.example.schedule_app.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table (name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    public Schedule(String name, String title, String content)
    {
        this.name=name;
        this.title=title;
        this.content=content;
    }

    public void updateSchedule(String title, String content)
    {
        this.title=title;
        this.content=content;
    }



}
