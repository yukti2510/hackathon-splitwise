package hackathon.splitwise.mappers;

import hackathon.splitwise.dto.UserDto;
import hackathon.splitwise.entity.UserEntity;

public class UserMapper {

    public static UserDto mapUserEntityToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .jupiterUserId(userEntity.getJupiterUserId())
                .phone(userEntity.getPhone())
                .build();
    }
}
