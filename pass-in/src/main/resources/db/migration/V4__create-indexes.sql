CREATE UNIQUE INDEX events_slug_key ON events(slug);
CREATE UNIQUE INDEX attendees_events_id_email_key ON attendees(event_id, email);
CREATE UNIQUE INDEX check_ins_attendees_id_key ON check_ins(attendees_id);