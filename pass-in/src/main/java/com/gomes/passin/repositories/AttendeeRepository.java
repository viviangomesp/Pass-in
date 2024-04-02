package com.gomes.passin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomes.passin.domain.attendees.Attendee;

public interface AttendeeRepository extends JpaRepository<Attendee, String>{
    
}
