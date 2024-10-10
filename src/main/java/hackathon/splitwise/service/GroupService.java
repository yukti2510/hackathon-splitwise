package hackathon.splitwise.service;

import hackathon.splitwise.dto.UserDto;
import hackathon.splitwise.dto.request.CreateGroupRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.dto.response.GroupListResponseDto;
import hackathon.splitwise.dto.request.AddMembersToGroupRequestDto;
import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.entity.UserGroupEntity;
import hackathon.splitwise.entity.UserGroupMappingEntity;
import hackathon.splitwise.repository.GroupRepository;
import hackathon.splitwise.repository.UserGroupMappingRepository;
import hackathon.splitwise.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@Service
@Log4j2
public class GroupService {

        private final UserRepository userRepository;

        private final GroupRepository groupRepository;

        private final UserGroupMappingRepository userGroupMappingRepository;

        public GroupService(UserRepository userRepository, GroupRepository groupRepository, UserGroupMappingRepository userGroupMappingRepository) {
            this.userRepository = userRepository;
            this.groupRepository = groupRepository;
            this.userGroupMappingRepository = userGroupMappingRepository;
        }

        public void getGroup() {
            log.info("Group Service");
            UserEntity u = userRepository.findByPhone("9876543210");
            System.out.println(u);
        }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto) {
            log.info("Request to create group: {}", createGroupRequestDto);
            UserGroupEntity groupEntity = new UserGroupEntity();
            groupEntity.setName(createGroupRequestDto.getName());
            groupEntity.setType(createGroupRequestDto.getType());

            Map<String, String> metadata = new HashMap<>();
            metadata.put("logo", createGroupRequestDto.getLogo());
            groupEntity.setMetadata(metadata);

        UserGroupEntity groupEntity1 = groupRepository.saveAndFlush(groupEntity);

        List<UserDto> groupMembersList =  addMembersToGroup(AddMembersToGroupRequestDto.builder()
                .groupId(groupEntity1.getId())
                .membersList(createGroupRequestDto.getMembersList())
                .build());

            return CreateGroupResponseDto.builder()
                    .id(groupEntity1.getId())
                    .name(groupEntity1.getName())
                    .logo(groupEntity1.getMetadata().get("logo"))
                    .type(groupEntity1.getType())
                    .membersList(groupMembersList)
                    .creator(createGroupRequestDto.getCreator())
                    .build();
    }

    public GroupListResponseDto getGroupsList(String phone) {
        log.info("Request to get groups list for phone: {}", phone);
//        List<GroupEntity> groupEntities = groupRepository.findAllByPhone(phone);
        List<UserGroupEntity> groupEntities = new ArrayList<>();
        Double totalAmountPaid = 0.0;
        return GroupListResponseDto.builder()
                .groupList(groupEntities)
                .totalAmountPaid(totalAmountPaid)
                .build();
    }

    public List<UserDto> addMembersToGroup(AddMembersToGroupRequestDto addMembersToGroupRequestDto) {
        log.info("Request to add members to group: {}", addMembersToGroupRequestDto);
        List<UserDto> userDtoList = addMembersToGroupRequestDto.getMembersList();
        List<UserEntity> userEntities = new ArrayList<>();
        for (UserDto userDto: userDtoList) {
            UserGroupMappingEntity userGroupMappingEntity = new UserGroupMappingEntity();
            userGroupMappingEntity.setGroupId(addMembersToGroupRequestDto.getGroupId());
            userGroupMappingEntity.setPhone(userDto.getPhone());
            userGroupMappingRepository.saveAndFlush(userGroupMappingEntity);
            if (userRepository.findByPhone(userDto.getPhone()) == null) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(userDto.getName());
                userEntity.setJupiterUserId(userDto.getJupiterUserId());
                userEntity.setPhone(userDto.getPhone());
                userEntities.add(userRepository.saveAndFlush(userEntity));
            }
        }
        return userEntities.stream().map(userEntity -> UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .jupiterUserId(userEntity.getJupiterUserId())
                .build()).toList();
    }

}
