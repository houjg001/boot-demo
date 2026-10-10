package org.example.web.event;

import java.time.LocalDateTime;

public record UserUpdateEvent(
        Long userId,
        String userName,
        String email,
        LocalDateTime createTime) {
}
