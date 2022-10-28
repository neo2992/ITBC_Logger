package rs.itbootcamp.ITBC_Logger.models;


import org.springframework.data.annotation.CreatedDate;
import rs.itbootcamp.ITBC_Logger.models.enumerators.LogType;

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCreation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table (name = "Logs")
public class Log {
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID logId;
    private String message;
    private LogType logType;
    @CreatedDate
    private LocalDateTime date = LocalDateTime.now();

    public Log() {
    }

    public Log(String message, LogType logType, LocalDateTime date) {
        this.message = message;
        this.logType = logType;
        this.date = date;
    }

    public UUID getLogId() {
        return logId = logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", message='" + message + '\'' +
                ", logType=" + logType +
                ", date=" + date +
                '}';
    }
}
