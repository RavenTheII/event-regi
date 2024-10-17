package com.example.eventregistration.userservice.service;

import com.example.eventregistration.userservice.model.User;
import com.example.eventregistration.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate; // Inject KafkaTemplate for sending messages

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add User and send a notification message to Kafka
    public User addUser(User user) {
        User savedUser = userRepository.save(user);

        // Send Kafka message to notify about the new user
        kafkaTemplate.send("user-added-topic", "New user added: " + savedUser.getId());

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        }
        return null; // Handle the case where the user does not exist
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            // Send a Kafka message to notify that a user has been deleted
            kafkaTemplate.send("user-deleted-topic", "User deleted: " + id);
            return true;
        }
        return false; // Handle the case where the user does not exist
    }
}
