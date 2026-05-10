package com.jobportal.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @KafkaListener(topics = "notificationTopic", groupId = "notification-group")
    public void consumeApplicationEvent(String message) {

        System.out.println("==================================================");
        System.out.println("📩 NEW KAFKA MESSAGE RECEIVED IN NOTIFICATION SERVICE!");
        System.out.println("Message Payload: " + message);

        // Here is where you would normally write the logic to send a real email
        // For example: emailSender.send("user@email.com", "Thanks for applying!");

        System.out.println("✅ Simulated Email successfully sent to user!");
        System.out.println("==================================================");
    }
}
