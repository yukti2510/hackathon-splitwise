package hackathon.splitwise.controller;

import hackathon.splitwise.dto.request.CreateGroupRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.dto.response.GroupListResponseDto;
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
    public CreateGroupResponseDto createGroup(@RequestBody CreateGroupRequestDto createGroupRequestDto) {
        return groupService.createGroup(createGroupRequestDto);
    }

    @GetMapping("/groups-list")
    public GroupListResponseDto getGroupsList(@RequestHeader("phone") String phone) {
        log.info("Request to get groups list for phone: {}", phone);
        return groupService.getGroupsList(phone);
    }

}
