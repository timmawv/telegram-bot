package avlyakulov.timur.telegram_bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String USER_NOT_ADDED = "❌ You weren't added to schedule system. Please press /start ❌";

    private final String USER_ADDED = "You were already added to schedule system.";

    private final String WELCOME_MESSAGE = "Hello, It's bot for counting days in year\nYou can chose from list of commands here";

    private final String BOT_ACTIVATED = "✅Congratulations bot was activate you will receive a message every minute✅";

    private final String BOT_DEACTIVATED = "❌Bot was deactivated you won't receive a message anymore❌";

    private final String WRONG_COMMAND = "❌You put the wrong text or button please try again❌";

    public SendMessage generateMessage(User user, String textFromUser) {
        SendMessage sendMessage;
        Optional<User> userByChatId = userRepository.findById(user.getChatId());
        switch (textFromUser) {
            case Buttons.BTN_START -> {
                if (userByChatId.isPresent()) {
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(USER_ADDED)
                            .replyMarkup(buildCommandsMenu())
                            .build();
                } else {
                    addUser(user);
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(WELCOME_MESSAGE)
                            .replyMarkup(buildCommandsMenu())
                            .build();
                }
            }
            case Buttons.BTN_ACTIVATE -> {
                if (userByChatId.isPresent()) {
                    userRepository.setIsActiveTrue(user.getChatId());
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(BOT_ACTIVATED)
                            .replyMarkup(buildCommandsMenu())
                            .build();
                } else {
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(USER_NOT_ADDED)
                            .build();
                }
            }
            case Buttons.BTN_DEACTIVATE -> {
                if (userByChatId.isPresent()) {
                    userRepository.setIsActiveFalse(user.getChatId());
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(BOT_DEACTIVATED)
                            .replyMarkup(buildCommandsMenu())
                            .build();
                } else {
                    sendMessage = SendMessage.builder()
                            .chatId(user.getChatId().toString())
                            .text(USER_NOT_ADDED)
                            .build();
                }

            }
            default -> {
                sendMessage = SendMessage.builder()
                        .chatId(user.getChatId().toString())
                        .text(WRONG_COMMAND)
                        .replyMarkup(buildCommandsMenu())
                        .build();
            }
        }

        return sendMessage;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getChatId());
        if (optionalUser.isEmpty())
            userRepository.save(user);
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
