package larionov.controller;

import larionov.api.UserApi;
import larionov.command.user.CreateUserCommandHandler;
import larionov.mapper.ApiUserMapper;
import larionov.model.CreateUserRequest;
import larionov.model.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final ApiUserMapper userMapper;

    private final CreateUserCommandHandler handler;

    @Override
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {
        var createUserCommand =  userMapper.toCommand(createUserRequest);
        var idCreatedUser = handler.createUser(createUserCommand);
        return new ResponseEntity<>(HttpStatus.OK); // TODO
    }

}
