package com.gomes.passin.dto.event;

import com.gomes.passin.domain.events.Event;

import lombok.Getter;

@Getter
public class EventResponseDTO {
    EventDetailDTO event;
    public EventResponseDTO(Event event, Integer numberOfAttedees){
        this.event = new EventDetailDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug(), event.getMaximumAttendees(), numberOfAttedees);
    }
}
