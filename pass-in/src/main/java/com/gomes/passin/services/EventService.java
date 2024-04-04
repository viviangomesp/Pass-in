package com.gomes.passin.services;

import java.text.Normalizer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gomes.passin.domain.attendees.Attendee;
import com.gomes.passin.domain.events.Event;
import com.gomes.passin.domain.events.exceptions.EventNotFoundException;
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
        Event event = this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
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
    private String createSlug(String text){
        String normalizer = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalizer.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
            .replaceAll("[^\\w\\s]", "")
            .replaceAll("\\s+", "-")
            .toLowerCase();
        
    }
}
