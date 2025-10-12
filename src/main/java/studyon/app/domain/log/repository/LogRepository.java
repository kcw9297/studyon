package studyon.app.domain.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.domain.log.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
