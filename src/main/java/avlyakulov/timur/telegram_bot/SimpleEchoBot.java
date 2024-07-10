package avlyakulov.timur.telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SimpleEchoBot extends TelegramLongPollingBot {

    @Value(value = "telegram.token")
    private String telegramToken;

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "timmawv_bot";
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }


}
