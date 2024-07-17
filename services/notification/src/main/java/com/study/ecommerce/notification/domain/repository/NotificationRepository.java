package com.study.ecommerce.notification.domain.repository;

import com.study.ecommerce.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
