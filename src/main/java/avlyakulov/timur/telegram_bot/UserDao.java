package avlyakulov.timur.telegram_bot;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDao {

    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> findAll() {
        return users.stream().toList();
    }

    public void activateUser(Long userId) {
        Optional<User> user = users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
        user.ifPresent(value -> value.setIsActive(true));
    }

    public void deactivateUser(Long userId) {
        Optional<User> user = users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
        user.ifPresent(value -> value.setIsActive(false));
    }
}
