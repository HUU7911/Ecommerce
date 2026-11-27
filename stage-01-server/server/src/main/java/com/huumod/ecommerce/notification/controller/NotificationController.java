package com.huumod.ecommerce.notification.controller;

import com.huumod.ecommerce.common.event.NotificationEvent;
import com.huumod.ecommerce.notification.dto.request.Recipient;
import com.huumod.ecommerce.notification.dto.request.SendEmailRequest;
import com.huumod.ecommerce.notification.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    NotificationService notificationService;

    @KafkaListener(topics = {
            "user-created"
    })
    public void kafkaListener(NotificationEvent event) {
        log.info("Receive notification event: ");
        notificationService.sendEmail(SendEmailRequest.builder()
                        .to(List.of(Recipient.builder()
                                        .email(event.getRecipient())
                                .build()))
                        .subject(event.getSubject())
                        .htmlContent(event.getBody())
                .build());
    }
}
