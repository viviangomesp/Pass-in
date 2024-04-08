package com.gomes.passin.services;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

import com.gomes.passin.domain.attendees.Attendee;
import com.gomes.passin.domain.events.Event;
import com.gomes.passin.domain.events.exceptions.EventNotFoundException;
import com.gomes.passin.dto.attendee.AttendeeIdDTO;
import com.gomes.passin.dto.attendee.AttendeeRequestDTO;
import com.gomes.passin.dto.attendee.exceptions.EventFullException;
import com.gomes.passin.dto.event.EventIdDTO;
import com.gomes.passin.dto.event.EventRequestDTO;
import com.gomes.passin.dto.event.EventResponseDTO;
import com.gomes.passin.repositories.EventRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.getEventById(eventId);
        List <Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDto){
        Event newEvent = new Event();
        newEvent.setTitle(eventDto.title());
        newEvent.setDetails(eventDto.details());
        newEvent.setMaximumAttendees(eventDto.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDto.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeeService.verifyAttendeeSubscription("","");

        Event event = this.getEventById(eventId);
        List <Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        
        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");
        
        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
    }

    private String createSlug(String text){
        String normalizer = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalizer.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
            .replaceAll("[^\\w\\s]", "")
            .replaceAll("\\s+", "-")
            .toLowerCase();
        
    }
}

