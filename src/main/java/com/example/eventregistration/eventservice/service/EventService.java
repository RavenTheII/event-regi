package com.example.eventregistration.eventservice.service;

import com.example.eventregistration.eventservice.model.Event;
import com.example.eventregistration.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Inject KafkaTemplate for sending messages

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Create Event and send a notification message to Kafka
    public Event createEvent(Event event) {
        Event savedEvent = eventRepository.save(event);

        // Send Kafka message to notify about the new event
        kafkaTemplate.send("event-created-topic", "New event created: " + savedEvent.getName());

        return savedEvent;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        if (eventRepository.existsById(id)) {
            updatedEvent.setId(id);
            return eventRepository.save(updatedEvent);
        }
        return null; // Handle case where event does not exist
    }

    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false; // Handle case where event does not exist
    }
}
