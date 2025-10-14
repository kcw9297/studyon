package studyon.app.layer.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.log.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
