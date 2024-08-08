package avlyakulov.timur.telegram_bot;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    private Long chatId;

    @EqualsAndHashCode.Exclude
    private String userFirstName;

    @EqualsAndHashCode.Exclude
    private Boolean isActive;
}
