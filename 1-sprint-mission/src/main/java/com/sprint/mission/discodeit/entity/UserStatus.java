package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus extends BaseUpdatableEntity {

//  private static final long serialVersionUID = 1L;
//  private UUID id;
//  private Instant createdAt;
//  private Instant updatedAt;
//  //

  @OneToOne
  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "last_active_at")
  private Instant lastActiveAt;

  public UserStatus(UUID userId, Instant lastActiveAt) {
    this.userId = userId;
    this.lastActiveAt = lastActiveAt;
  }

  public void update(Instant lastActiveAt) {
    boolean anyValueUpdated = false;
    if (lastActiveAt != null && !lastActiveAt.equals(this.lastActiveAt)) {
      this.lastActiveAt = lastActiveAt;
      anyValueUpdated = true;
    }

    if (anyValueUpdated) {
      setUpdatedAt(Instant.now());
    }
  }

  public Boolean isOnline() {
    Instant instantFiveMinutesAgo = Instant.now().minus(Duration.ofMinutes(5));

    return lastActiveAt.isAfter(instantFiveMinutesAgo);
  }
}
