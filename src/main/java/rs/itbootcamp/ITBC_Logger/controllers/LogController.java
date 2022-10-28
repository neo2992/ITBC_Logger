package rs.itbootcamp.ITBC_Logger.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.itbootcamp.ITBC_Logger.models.Log;
import rs.itbootcamp.ITBC_Logger.repository.JpaLogRepository;
import rs.itbootcamp.ITBC_Logger.repository.JpaUserRepository;
import rs.itbootcamp.ITBC_Logger.repository.LogRepository;

import java.util.UUID;

@RestController
public class LogController {

    private final LogRepository logRepository;
    private final JpaLogRepository jpaLogRepository;
    private final JpaUserRepository jpaUserRepository;


    public LogController(LogRepository logRepository, JpaLogRepository jpaLogRepository, JpaUserRepository jpaUserRepository) {
        this.logRepository = logRepository;
        this.jpaLogRepository = jpaLogRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @PostMapping("/api/logs/create")
    public ResponseEntity<?> createLog(@RequestBody Log log, @RequestHeader String token) {
        if (log.getMessage().length() > 1024) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Message to long.");
        }
        if (log.getLogType().ordinal() != 0 && log.getLogType().ordinal() != 1 && log.getLogType().ordinal() != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect logType");
        }

        if (token.equalsIgnoreCase(jpaUserRepository.getToken(token))) {
            log.setLogId(UUID.randomUUID());
            logRepository.createLog(log, token);
            return ResponseEntity.status(HttpStatus.CREATED).body("Log created");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect token");
    }

    @GetMapping("/api/logs/search")
    public ResponseEntity<?> searchLogsById(@RequestHeader String token,
                                            @RequestParam(name = "message", required = false, defaultValue = "%%") String message,
                                            @RequestParam(name = "logType", required = false, defaultValue = "%%") String logType,
                                            @RequestParam(name = "dateFrom", required = false) String dateFrom,
                                            @RequestParam(name = "dateTo", required = false) String dateTo) {
        if (token.equalsIgnoreCase(jpaUserRepository.getToken(token))) {
            return ResponseEntity.status(HttpStatus.OK).body(jpaLogRepository.getLogsById(token, dateFrom, dateTo, message, logType));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("ne radi");
    }
}

