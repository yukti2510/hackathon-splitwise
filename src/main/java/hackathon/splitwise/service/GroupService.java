package hackathon.splitwise.service;

import hackathon.splitwise.dto.request.CreateGroupRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.dto.response.GroupListResponseDto;
import hackathon.splitwise.entity.GroupEntity;
import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.repository.GroupRepository;
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

        public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
            this.userRepository = userRepository;
            this.groupRepository = groupRepository;
        }

        public void getGroup() {
            log.info("Group Service");
            UserEntity u = userRepository.findByPhone("9876543210");
            System.out.println(u);
        }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto) {
            log.info("Request to create group: {}", createGroupRequestDto);
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setName(createGroupRequestDto.getName());
            groupEntity.setType(createGroupRequestDto.getType());

            Map<String, String> metadata = new HashMap<>();
            metadata.put("logo", createGroupRequestDto.getLogo());
            groupEntity.setMetadata(metadata);

            GroupEntity groupEntity1 = groupRepository.saveAndFlush(groupEntity);

            return CreateGroupResponseDto.builder()
                    .id(groupEntity1.getId())
                    .name(groupEntity1.getName())
                    .logo(groupEntity1.getMetadata().get("logo"))
                    .type(groupEntity1.getType())
                    .membersList(createGroupRequestDto.getMembersList())
                    .creator(createGroupRequestDto.getCreator())
                    .build();
    }

    public GroupListResponseDto getGroupsList(String phone) {
        log.info("Request to get groups list for phone: {}", phone);
//        List<GroupEntity> groupEntities = groupRepository.findAllByPhone(phone);
        List<GroupEntity> groupEntities = new ArrayList<>();
        Double totalAmountPaid = 0.0;
        return GroupListResponseDto.builder()
                .groupList(groupEntities)
                .totalAmountPaid(totalAmountPaid)
                .build();
    }
}
