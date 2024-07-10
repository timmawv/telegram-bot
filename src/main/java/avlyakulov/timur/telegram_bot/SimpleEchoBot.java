package avlyakulov.timur.telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SimpleEchoBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String telegramToken;

    @Value("${telegram.bot.username}")
    private String telegramUsername;

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }
}