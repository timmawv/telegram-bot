package avlyakulov.timur.telegram_bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleBotSender extends TelegramLongPollingBot {

    private final TelegramCredentials telegramCredentials;

    private final UserService userService;

    private final TelegramService telegramService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textFromUser = update.getMessage().getText();

            Long userId = update.getMessage().getChatId();
            String userFirstName = update.getMessage().getFrom().getFirstName();

            User user = new User(userId, userFirstName, false);

            log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

            SendMessage sendMessage = userService.generateMessage(user, textFromUser);

            try {
                this.sendApiMethod(sendMessage);
            } catch (TelegramApiException e) {
                log.error("Exception when sending message: ", e);
            }
        } else {
            log.warn("Unexpected update from user");
        }
    }

    @Scheduled(fixedDelay = 1000 * 30, initialDelay = 1000)
    public void sendMessage() {
        List<User> users = userService.findAll();
        List<SendMessage> listOfMessages = telegramService.getListOfMessages(users);
        try {
            for (SendMessage message : listOfMessages) {
                this.sendApiMethod(message);
            }
        } catch (TelegramApiException e) {
            log.error("Exception when sending message: ", e);
        }
    }

    @Override
    public String getBotUsername() {
        return telegramCredentials.getUsername();
    }

    @Override
    public String getBotToken() {
        return telegramCredentials.getToken();
    }
}