package larionov.mapper;

import larionov.command.user.CreateUserCommand;
import larionov.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommandUserMapper {

    UserEntity toUserEntity(CreateUserCommand command);

}
