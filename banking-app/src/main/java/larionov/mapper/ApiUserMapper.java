package larionov.mapper;

import larionov.command.user.CreateUserCommand;
import larionov.model.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiUserMapper {

    CreateUserCommand toCommand(CreateUserRequest request);

}
