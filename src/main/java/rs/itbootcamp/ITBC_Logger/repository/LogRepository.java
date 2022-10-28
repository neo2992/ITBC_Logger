package rs.itbootcamp.ITBC_Logger.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rs.itbootcamp.ITBC_Logger.models.Log;
import rs.itbootcamp.ITBC_Logger.models.User;

import java.util.List;

@Repository
public class LogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createLog(Log log, String token) {
        String query = "INSERT INTO Logs (logId, id, message, logType, logDate) VALUES ('" + log.getLogId() + "','" + token + "','"
                + log.getMessage() + "','" + log.getLogType() + "','" + log.getDate() + "')";
        jdbcTemplate.execute(query);
    }

    public List<Log> getAllLogsbyId(User user) {
        String query = "SELECT * FROM Logs WHERE id = " + user.getId();
        return jdbcTemplate.query(
                query,
                BeanPropertyRowMapper.newInstance(Log.class)
        );
    }
}
