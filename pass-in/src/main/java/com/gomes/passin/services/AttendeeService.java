package com.gomes.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;

import com.gomes.passin.domain.attendees.Attendee;
import com.gomes.passin.domain.checkin.CheckIn;
import com.gomes.passin.dto.attendee.AttendeeDetails;
import com.gomes.passin.dto.attendee.AttendeeListResponseDTO;
import com.gomes.passin.repositories.AttendeeRepository;
import com.gomes.passin.repositories.CheckinRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);
        
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);

    }
}
