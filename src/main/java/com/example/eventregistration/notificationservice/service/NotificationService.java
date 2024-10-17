package com.example.eventregistration.notificationservice.service;

import com.example.eventregistration.notificationservice.model.Notification;
import com.example.eventregistration.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Kafka Listener for notifications
    @KafkaListener(topics = "notification-topic", groupId = "notification_group")
    public void listen(String message) {
        // Process the incoming notification message
        Notification notification = new Notification();
        notification.setMessage(message);
        // Save notification to the database (if you want to store them)
        notificationRepository.save(notification);
        // You can also add more logic here if needed
        System.out.println("Received Notification: " + message);
    }

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        if (notificationRepository.existsById(id)) {
            updatedNotification.setId(id);
            return notificationRepository.save(updatedNotification);
        }
        return null; // Handle the case where the notification does not exist
    }

    public boolean deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the notification does not exist
    }
}
