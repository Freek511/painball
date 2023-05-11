package freek.anotheratempt.Mappers;

import freek.anotheratempt.DTO.CreateUserDto;
import freek.anotheratempt.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target ="city", source = "city")
    User createUserDtoToUser(CreateUserDto createUserDto);
}
