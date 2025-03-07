package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;


@Setter
@Getter
public class BaseUpdatableEntity extends BaseEntity {

  @LastModifiedDate
  @Column(name = "updated_at")
  Instant updatedAt;

}
