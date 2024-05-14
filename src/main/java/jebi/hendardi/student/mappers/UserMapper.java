package jebi.hendardi.student.mappers;

import jebi.hendardi.student.dtos.SignUpDto;
import jebi.hendardi.student.dtos.UserDto;
import jebi.hendardi.student.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
