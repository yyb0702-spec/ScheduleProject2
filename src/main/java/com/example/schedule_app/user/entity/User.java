package com.example.schedule_app.user.entity;

import com.example.schedule_app.common.entity.BaseEntity;
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
    @Column(nullable = false)
    private String name;
    @Column(unique = true ,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public User(String name,String email,String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void userUpdate(String name,String email)
    {
        if (name != null)this.name = name;
        if (email != null)this.email = email;
    }
}
