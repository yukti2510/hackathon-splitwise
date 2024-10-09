package hackathon.splitwise.controller;

import hackathon.splitwise.service.GroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@RestController
@RequestMapping(value = "/v1/group", produces = "application/json")
@Log4j2
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/test")
    public String test() {
        groupService.getGroup();
        return "Application is running";
    }

}
