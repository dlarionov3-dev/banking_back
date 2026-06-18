package larionov.command.user;


import larionov.mapper.CommandUserMapper;
import larionov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler {

    private final CommandUserMapper userMapper;

    private final UserRepository userRepository;

    public UUID createUser(CreateUserCommand createUserCommand) {
        var userEntity = userMapper.toUserEntity(createUserCommand);
        var persistedUserEntity = userRepository.save(userEntity);
        return persistedUserEntity != null ? persistedUserEntity.getId() : null;
    }

}
