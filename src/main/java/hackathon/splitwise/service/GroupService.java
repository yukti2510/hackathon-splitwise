package hackathon.splitwise.service;

import hackathon.splitwise.dto.request.CreateUserRequestDto;
import hackathon.splitwise.entity.GroupEntity;
import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.repository.GroupRepository;
import hackathon.splitwise.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public String createGroup(CreateUserRequestDto createUserRequestDto) {
            log.info("Request to create group: {}", createUserRequestDto);
//            GroupEntity groupEntity = groupRepository.insert(createUserRequestDto);
            return "Success";
    }
}
