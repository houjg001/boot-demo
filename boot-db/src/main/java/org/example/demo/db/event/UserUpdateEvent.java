package org.example.demo.db.event;

import java.time.LocalDateTime;

public record UserUpdateEvent(
        Long userId,
        String userName,
        String email,
        LocalDateTime createTime) {
}
