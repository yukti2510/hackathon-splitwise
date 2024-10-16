package hackathon.splitwise.service;

import hackathon.splitwise.dto.GroupDto;
import hackathon.splitwise.dto.TransactionDetailResponseDto;
import hackathon.splitwise.dto.TransactionDto;
import hackathon.splitwise.dto.UserDto;
import hackathon.splitwise.dto.request.CreateGroupRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.dto.response.GroupDetailsResponseDto;
import hackathon.splitwise.dto.response.GroupListResponseDto;
import hackathon.splitwise.dto.request.AddMembersToGroupRequestDto;
import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.entity.GroupDetailsEntity;
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

import static hackathon.splitwise.mappers.GroupMapper.mapToGroupDto;
import static hackathon.splitwise.mappers.GroupMapper.mapToGroupDtoList;

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

        private final TransactionService transactionService;

        public GroupService(UserRepository userRepository, GroupRepository groupRepository, UserGroupMappingRepository userGroupMappingRepository, TransactionService transactionService) {
            this.userRepository = userRepository;
            this.groupRepository = groupRepository;
            this.userGroupMappingRepository = userGroupMappingRepository;
            this.transactionService = transactionService;
        }

        public void getGroup() {
            log.info("Group Service");
            UserEntity u = userRepository.findByPhone("9876543210");
            System.out.println(u);
        }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto) {
            log.info("Request to create group: {}", createGroupRequestDto);
            GroupDetailsEntity groupEntity = new GroupDetailsEntity();
            groupEntity.setName(createGroupRequestDto.getName());
            groupEntity.setType(createGroupRequestDto.getType());

            Map<String, String> metadata = new HashMap<>();
            metadata.put("logo", createGroupRequestDto.getLogo());
            groupEntity.setMetadata(metadata);

        GroupDetailsEntity groupEntity1 = groupRepository.saveAndFlush(groupEntity);

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
        List<UserGroupMappingEntity> userGroupMappingEntities = userGroupMappingRepository.findAllByPhone(phone);
        List<Long> groupIds = userGroupMappingEntities.stream()
                .map(UserGroupMappingEntity::getGroupId)
                .toList();
        List<GroupDetailsEntity> groupEntities = groupRepository.findAllById(groupIds);
        groupEntities.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        Double totalAmountPaid = userGroupMappingEntities.stream().mapToDouble(UserGroupMappingEntity::getAmountPaid)
                .sum();
        Map<Long, Long> groupMemberCountMap = new HashMap<>();
        for (Long groupId : groupIds) {
            Long memberCount = userGroupMappingRepository.countByGroupId(groupId);
            groupMemberCountMap.put(groupId, memberCount);
        }
        return GroupListResponseDto.builder()
                .groupList(mapToGroupDtoList(groupEntities, userGroupMappingEntities, groupMemberCountMap))
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

        List<UserEntity> userEntities1 = userRepository.findAllByPhoneIn(userDtoList.stream().map(UserDto::getPhone).toList());

        return userEntities1.stream().map(userEntity -> UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .jupiterUserId(userEntity.getJupiterUserId())
                .build()).toList();
    }

    public GroupDetailsResponseDto getGroupDetailsById(Long groupId, String phone) {
        GroupDetailsEntity groupDetailsEntity = groupRepository.findById(groupId).get();
        List<UserGroupMappingEntity> userGroupMappingEntityList = userGroupMappingRepository.findAllByGroupId(groupDetailsEntity.getId());

        UserGroupMappingEntity currentUserGroupMappingEntity = userGroupMappingEntityList.stream()
                .filter(userGroupMappingEntity1 -> userGroupMappingEntity1.getPhone().equals(phone))
                .findFirst().get();

        GroupDto groupDto =  mapToGroupDto(groupDetailsEntity, currentUserGroupMappingEntity.getAmountPaid(), 1L);

        List<String> membersPhoneList = userGroupMappingEntityList.stream()
                .map(UserGroupMappingEntity::getPhone)
                .toList();

        List<UserEntity> userEntities = userRepository.findAllByPhoneIn(membersPhoneList);

        List<UserDto> membersList = userEntities.stream().map(userEntity -> UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .jupiterUserId(userEntity.getJupiterUserId())
                .build()).toList();

        List<TransactionDetailResponseDto> transactionDetailList = transactionService.getTransactionsList(groupId, phone);
        groupDto.setTotalMembers((long) membersList.size());
        return GroupDetailsResponseDto.builder()
                .group(groupDto)
                .membersList(membersList)
                .transactionsList(transactionDetailList)
                .build();
    }

    public GroupListResponseDto searchGroups(String phone, String name) {
        log.info("Request to search groups for phone: {} and text: {}", phone, name);
        List<UserGroupMappingEntity> userGroupMappingEntities = userGroupMappingRepository.findAllByPhone(phone);
        List<Long> groupIds = userGroupMappingEntities.stream()
                .map(UserGroupMappingEntity::getGroupId)
                .toList();
        List<GroupDetailsEntity> groupEntities = groupRepository.findAllByIdAndNameContaining(groupIds, name);
        groupEntities.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        Double totalAmountPaid = userGroupMappingEntities.stream().mapToDouble(UserGroupMappingEntity::getAmountPaid)
                .sum();
        Map<Long, Long> groupMemberCountMap = new HashMap<>();
        for (Long groupId : groupIds) {
            Long memberCount = userGroupMappingRepository.countByGroupId(groupId);
            groupMemberCountMap.put(groupId, memberCount);
        }
        return GroupListResponseDto.builder()
                .groupList(mapToGroupDtoList(groupEntities, userGroupMappingEntities, groupMemberCountMap))
                .totalAmountPaid(totalAmountPaid)
                .build();
    }
}
