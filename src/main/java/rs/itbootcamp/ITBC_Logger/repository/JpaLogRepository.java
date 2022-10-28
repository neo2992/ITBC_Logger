package rs.itbootcamp.ITBC_Logger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.itbootcamp.ITBC_Logger.models.Log;
import rs.itbootcamp.ITBC_Logger.models.enumerators.LogType;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaLogRepository extends JpaRepository<Log, UUID> {

    @Modifying
    @Query(value = "INSERT INTO Logs (logId, message, logType, logDate) VALUES (:logId, :message, :logType, :logDate", nativeQuery = true)
    @Transactional
    void saveLog(@Param("logId") UUID logId, @Param("message") String message, @Param("logType") LogType logType, @Param("logDate") LocalDateTime logDate);

    @Query(value = "SELECT Logs.message, Logs.logType, Logs.logDate FROM Logs WHERE (logDate >= :dateFrom AND logDate <= :dateTo) AND id = :token AND message LIKE %:message% AND logType LIKE %:logType%", nativeQuery = true)
    List<String> getLogsById(@Param("token") String token, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, @Param("message") String message, @Param("logType") String logType);
}
