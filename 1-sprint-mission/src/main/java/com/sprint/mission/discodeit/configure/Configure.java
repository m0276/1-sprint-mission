package com.sprint.mission.discodeit.configure;

import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.storage.LocalBinaryContentStorage;
import java.io.File;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {

  @Bean
  BinaryContentStorage localBinaryContentStorage() {
    return new LocalBinaryContentStorage();
  }

}
