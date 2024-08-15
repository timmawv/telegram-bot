package avlyakulov.timur.telegram_bot;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("update User set isActive = true where chatId = ?1")
    void setIsActiveTrue(Long chatId);

    @Modifying
    @Transactional
    @Query("update User set isActive = false where chatId = ?1")
    void setIsActiveFalse(Long chatId);
}
