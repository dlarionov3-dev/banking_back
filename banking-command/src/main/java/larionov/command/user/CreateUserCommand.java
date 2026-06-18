package larionov.command.user;

import java.time.LocalDate;

public record CreateUserCommand (
        String email,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate dateOfBirth
) {
}
