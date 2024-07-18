package avlyakulov.timur.telegram_bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Getter
@Configuration
public class TelegramBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ScheduleBotSender scheduleBotSender) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            log.info("Registering bot...");
            telegramBotsApi.registerBot(scheduleBotSender);
            log.info("Telegram bot is ready to accept updates from user......");
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            log.error("Failed to register bot(check internet connection / bot token or make sure only one instance of bot is running).", e);
            throw new RuntimeException(e);
        }
    }
}