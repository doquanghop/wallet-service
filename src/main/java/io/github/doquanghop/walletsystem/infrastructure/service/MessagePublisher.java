package io.github.doquanghop.walletsystem.infrastructure.service;

import io.github.doquanghop.walletsystem.shared.tracing.HasRequestId;

public interface MessagePublisher<T extends HasRequestId> {
    void publish(String channel, T message);
}
