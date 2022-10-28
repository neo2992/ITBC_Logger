package rs.itbootcamp.ITBC_Logger.repository;


import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.itbootcamp.ITBC_Logger.models.User;
import rs.itbootcamp.ITBC_Logger.models.enumerators.LogType;
import rs.itbootcamp.ITBC_Logger.models.enumerators.UserType;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {

    @Override
    Optional<User> findById(UUID uuid);

    @Override
    List<User> findAll();

    @Query (value = "SELECT id FROM Clients WHERE username=:username", nativeQuery = true)
    UUID getIdByName (@Param("username") String username);

    @Modifying
    @Query(value = "insert into Clients (id, username, password, email, userType) VALUES (:id, :username, :password, :email, :userType)", nativeQuery = true)
    @Transactional
    void saveUser(@Param("id") UUID id, @Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("userType") UserType userType);

    @Query(value = "SELECT COUNT(*) FROM Clients WHERE username=:username", nativeQuery = true)
    Integer isDuplicateName(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) FROM Clients WHERE email=:email", nativeQuery = true)
    Integer isDuplicateEmail(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE Clients SET token = :token WHERE username = :username", nativeQuery = true)
    @Transactional
    void saveToken(@Param("token") String token, @Param("username") String username);

    @Query(value = "SELECT DISTINCT token FROM Clients WHERE id =:token", nativeQuery = true)
    String getToken(@Param("token") String token);

    @Query(value = "SELECT userType FROM Clients WHERE id = :token", nativeQuery = true)
    String getUserTypeById(@Param("token") String token);

    @Modifying
    @Query(value = "UPDATE Clients SET password = :password WHERE id = :id", nativeQuery = true)
    @Transactional
    void updatePassword(@Param("password") String password, @Param("id") String id);

    @Query(value = "SELECT password FROM Clients WHERE id = :id", nativeQuery = true)
    String getPasswordById(@Param("id") String id);
}
