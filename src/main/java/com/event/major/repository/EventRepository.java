package com.event.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event.major.model.Event;

public interface EventRepository extends JpaRepository <Event , Long> {
    List<Event> findAllByCategory_Id(int id);

}
