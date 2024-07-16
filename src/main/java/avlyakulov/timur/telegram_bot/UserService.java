package avlyakulov.timur.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public SendMessage generateMessage(User user, String textFromUser) {
        SendMessage sendMessage;

        switch (textFromUser) {
            case Buttons.BTN_START -> {
                sendMessage = SendMessage.builder()
                        .chatId(user.getUserId().toString())
                        .text("Hello, It's bot for counting days in year\nYou can chose from list of commands here")
                        .replyMarkup(buildCommandsMenu())
                        .build();
            }
            case Buttons.BTN_ACTIVATE -> {
                addUser(user);
                activateUser(user.getUserId());
                sendMessage = SendMessage.builder()
                        .chatId(user.getUserId().toString())
                        .text("✅Congratulations bot was activate you will receive a message every minute✅")
                        .replyMarkup(buildCommandsMenu())
                        .build();
            }
            case Buttons.BTN_DEACTIVATE -> {
                deactivateUser(user.getUserId());
                sendMessage = SendMessage.builder()
                        .chatId(user.getUserId().toString())
                        .text("❌Bot was deactivated you won't receive a message anymore❌")
                        .replyMarkup(buildCommandsMenu())
                        .build();
            }
            default -> {
                sendMessage = SendMessage.builder()
                        .chatId(user.getUserId().toString())
                        .text("❌You put the wrong text or button please try again❌")
                        .replyMarkup(buildCommandsMenu())
                        .build();
            }
        }

        return sendMessage;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void activateUser(Long userId) {
        userDao.activateUser(userId);
    }

    public void deactivateUser(Long userId) {
        userDao.deactivateUser(userId);
    }

    private ReplyKeyboardMarkup buildCommandsMenu() {
        List<KeyboardButton> buttons = List.of(
                new KeyboardButton(Buttons.BTN_ACTIVATE),
                new KeyboardButton(Buttons.BTN_DEACTIVATE));

        KeyboardRow row1 = new KeyboardRow(buttons);

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1))
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}
