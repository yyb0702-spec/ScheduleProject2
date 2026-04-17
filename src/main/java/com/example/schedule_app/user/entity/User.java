package com.example.schedule_app.user.entity;

import com.example.schedule_app.schedule.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
//    private String password;

    public User(String name,String email)
    {
        this.name = name;
        this.email = email;
    }

    public void userUpdate(String name,String email)
    {
        this.name = name;
        this.email = email;
    }
}
