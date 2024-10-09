package hackathon.splitwise.service;

import hackathon.splitwise.entity.UserEntity;
import hackathon.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@Service
public class GroupService {

        private final UserRepository userRepository;

        public GroupService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public void getGroup() {
            UserEntity u = userRepository.findByPhone("9876543210");
            System.out.println(u);
        }
}
