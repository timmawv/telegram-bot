package avlyakulov.timur.telegram_bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class TelegramService {

    private BigDecimal percentOfYear;

    public List<SendMessage> getListOfMessages(List<User> users) {
        setPercentOfYear();
        return users.stream()
                .filter(User::getIsActive)
                .map(user -> SendMessage.builder()
                        .chatId(user.getChatId().toString())
                        .text("Hello this year has already passed " + percentOfYear + "%")
                        .build())
                .toList();
    }

    public void setPercentOfYear() {
        int dayOfYear = LocalDate.now().getDayOfYear();
        int daysInYear = Year.now().length();

        percentOfYear = BigDecimal.valueOf(dayOfYear)
                .divide(BigDecimal.valueOf(daysInYear), 2, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));
    }
}
