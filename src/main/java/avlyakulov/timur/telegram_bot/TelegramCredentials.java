package avlyakulov.timur.telegram_bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "telegram")
public class TelegramCredentials {

    private final String token;

    private final String username;
}