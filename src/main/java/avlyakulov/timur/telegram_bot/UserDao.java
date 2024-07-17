package avlyakulov.timur.telegram_bot;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDao {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public Optional<User> findByChatId(Long chatId) {
        return users.stream()
                .filter(u -> u.getChatId().equals(chatId))
                .findFirst();
    }

    public List<User> findAll() {
        return users;
    }

    public void setIsActiveTrue(Long chatId) {
        Optional<User> user = users.stream().filter(u -> u.getChatId().equals(chatId)).findFirst();
        user.ifPresentOrElse(value -> value.setIsActive(true), () -> {
            throw new UserNotFoundException("❌ You weren't added to schedule system. Please press /start ❌");
        });
    }

    public void setIsActiveFalse(Long chatId) {
        Optional<User> user = users.stream().filter(u -> u.getChatId().equals(chatId)).findFirst();
        user.ifPresentOrElse(value -> value.setIsActive(false), () -> {
            throw new UserNotFoundException("❌ You weren't added to schedule system. Please press /start ❌");
        });
    }
}
