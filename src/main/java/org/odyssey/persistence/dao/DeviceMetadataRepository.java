package org.odyssey.persistence.dao;

import java.util.List;
import org.odyssey.persistence.model.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

	List<DeviceMetadata> findByUserId(Long userId);
}
