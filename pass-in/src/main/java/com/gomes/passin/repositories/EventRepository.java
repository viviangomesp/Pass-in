package com.gomes.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomes.passin.domain.events.Event;

public interface EventRepository extends JpaRepository<Event, String>{
    
    
}
