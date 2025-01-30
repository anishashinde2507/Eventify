package com.event.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.event.major.model.Category;
import com.event.major.model.Event;
import com.event.major.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    public List<Event>getAllEvent(){
        return eventRepository.findAll();
    }

    public void addEvent(Event event){
        eventRepository.save(event);

    }

    public void removeEventById(long id){
        eventRepository.deleteById(id);
    }

    public Optional<Event> getEventById(long id){
        return eventRepository.findById(id);

    } 
    public List<Event>getAllEventsByCategoryId(int id){
        return eventRepository.findAllByCategory_Id(id);

    }




    
}
