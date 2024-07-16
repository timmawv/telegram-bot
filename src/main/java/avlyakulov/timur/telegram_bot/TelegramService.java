package avlyakulov.timur.telegram_bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TelegramService {

    private final BigDecimal percentOfYear;

    {
        int dayOfYear = LocalDate.now().getDayOfYear();

        int currentYear = Year.now().getValue();

        LocalDate startOfYear = LocalDate.of(currentYear, 1, 1);
        LocalDate endOfYear = LocalDate.of(currentYear, 12, 31);

        long daysInYear = ChronoUnit.DAYS.between(startOfYear, endOfYear) + 1;

        percentOfYear = new BigDecimal(dayOfYear)
                .divide(new BigDecimal(daysInYear), 2, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));
    }

    public List<SendMessage> getListOfMessages(List<User> users) {
        return users.stream()
                .filter(User::getIsActive)
                .map(user -> SendMessage.builder()
                        .chatId(user.getUserId().toString())
                        .text("hello this year has already passed " + percentOfYear + "%")
                        .build())
                .toList();
    }
}
