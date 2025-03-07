package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

@Getter
public abstract class BaseEntity {

  @Id
  UUID id = UUID.randomUUID();

  @Column(name = "created_at")
  @CreatedDate
  Instant createdAt;

}
