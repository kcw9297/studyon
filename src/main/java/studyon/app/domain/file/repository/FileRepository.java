package studyon.app.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.common.enums.Entity;
import studyon.app.domain.file.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByEntityIdAndEntity(Long entityId, Entity entity);
}
