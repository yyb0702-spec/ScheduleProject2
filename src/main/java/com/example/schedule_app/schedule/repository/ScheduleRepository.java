package com.example.schedule_app.schedule.repository;

import com.example.schedule_app.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule , Long> {

    Optional<Schedule> findByIdAndUserId(Long scheduleId, Long userId);// Select * from schedule where id=? and user_id=?


    Page<Schedule> findAllByUserId(Long userId, Pageable pageable);//Select * from schedule where user_id=? order by modified desc
    //select count(*) from schedule where user_id=? 데이터조회쿼리,카운트쿼리 페이징처리는 총 2개 실행

}