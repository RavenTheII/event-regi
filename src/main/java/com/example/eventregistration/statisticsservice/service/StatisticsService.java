package com.example.eventregistration.statisticsservice.service;

import com.example.eventregistration.statisticsservice.model.Statistics;
import com.example.eventregistration.statisticsservice.repository.StatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Statistics createStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Optional<Statistics> getStatisticsById(Long id) {
        return statisticsRepository.findById(id);
    }

    public Statistics updateStatistics(Long id, Statistics updatedStatistics) {
        if (statisticsRepository.existsById(id)) {
            updatedStatistics.setId(id);
            return statisticsRepository.save(updatedStatistics);
        }
        return null; // Handle the case where the statistics does not exist
    }

    public boolean deleteStatistics(Long id) {
        if (statisticsRepository.existsById(id)) {
            statisticsRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the statistics does not exist
    }

    // Handle user deletion notification from Kafka
    @KafkaListener(topics = "user-deleted-topic", groupId = "statistics_group")
    public void handleUserDeleted(String message) {
        // Parse the message to get the user ID
        Long userId = Long.parseLong(message.split(":")[1].trim());

        // Logic to remove the user from statistics
        removeUserFromStatistics(userId);
    }

    // Your logic to remove user statistics here
    private void removeUserFromStatistics(Long userId) {
        System.out.println("Removing user statistics for user ID: " + userId);

        // Add your logic to update statistics here, e.g., delete from a database or update an in-memory data structure
        deleteStatistics(userId); // Example call to delete statistics by user ID
    }
}
