package com.huumod.ecommerce.notification.service;

import com.huumod.ecommerce.common.exception.AppException;
import com.huumod.ecommerce.common.exception.ErrorCode;
import com.huumod.ecommerce.notification.dto.request.EmailRequest;
import com.huumod.ecommerce.notification.dto.request.SendEmailRequest;
import com.huumod.ecommerce.notification.dto.request.Sender;
import com.huumod.ecommerce.notification.dto.response.EmailResponse;
import com.huumod.ecommerce.notification.repository.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {

    EmailClient emailClient;

    @NonFinal
    @Value("${brevo.api-key}")
    String API_KEY;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Huumod")
                        .email("trihuutran4@gmail.com")
                        .build())
                .to(request.getTo())
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(API_KEY, emailRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_NOT_SEND);
        }
    }
}
