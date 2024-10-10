package hackathon.splitwise.controller;

import hackathon.splitwise.dto.request.CreateUserRequestDto;
import hackathon.splitwise.service.GroupService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/create")
    public String createGroup(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return groupService.createGroup(createUserRequestDto);
    }

}
