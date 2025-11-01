package studyon.app.layer.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studyon.app.common.enums.Entity;
import studyon.app.common.enums.FileType;
import studyon.app.layer.domain.file.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByEntityIdAndEntity(Long entityId, Entity entity);

    List<File> findAllByEntityId(Long entityId);

    @Query("""
        SELECT f
        FROM File f
        WHERE f.entityId = :entityId AND f.fileType = :fileType
    """)
    List<File> findAllByEntityIdAndFileType(Long entityId, FileType fileType);
}
