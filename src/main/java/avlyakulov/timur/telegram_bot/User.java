package avlyakulov.timur.telegram_bot;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @Column(name = "chatid")
    private Long chatId;

    @Column(name = "firstname")
    @EqualsAndHashCode.Exclude
    private String firstName;

    @Column(name = "isactive")
    @EqualsAndHashCode.Exclude
    private Boolean isActive;
}
