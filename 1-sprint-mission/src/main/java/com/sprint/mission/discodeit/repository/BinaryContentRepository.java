package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {

  BinaryContent save(BinaryContent binaryContent);

  Optional<BinaryContent> findById(UUID id);

  boolean existsById(UUID id);

  void deleteById(UUID id);
}
