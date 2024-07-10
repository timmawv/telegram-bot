package avlyakulov.timur.telegram_bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class Listener implements ApplicationListener<ContextRefreshedEvent> {

    private SimpleEchoBot simpleEchoBot;

    @Autowired
    public Listener(SimpleEchoBot simpleEchoBot) {
        this.simpleEchoBot = simpleEchoBot;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            log.info("Registering bot...");
            telegramBotsApi.registerBot(simpleEchoBot);
        } catch (TelegramApiException e) {
            log.error("Failed to register bot(check internet connection / bot token or make sure only one instance of bot is running).", e);
        }
        log.info("Telegram bot is ready to accept updates from user......");
    }
}