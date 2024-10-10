package hackathon.splitwise.controller;

import hackathon.splitwise.dto.GroupDto;
import hackathon.splitwise.dto.request.CreateGroupRequestDto;
import hackathon.splitwise.dto.response.CreateGroupResponseDto;
import hackathon.splitwise.dto.response.GroupListResponseDto;
import hackathon.splitwise.dto.request.AddMembersToGroupRequestDto;
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

    @PostMapping("/add-members")
    public String addMembersToGroup(@RequestBody AddMembersToGroupRequestDto addMembersToGroupRequestDto) {
        groupService.addMembersToGroup(addMembersToGroupRequestDto);
        return "Members added successfully";
    }

    @GetMapping("/id/{group-id}")
    public GroupDto getGroupDetailsById(@PathVariable("group-id") String groupId, @RequestHeader("phone") String phone) {
        log.info("Request to get groups details by id: {} and phone: {}", groupId, phone);
        return groupService.getGroupDetailsById(groupId, phone);
    }

    @GetMapping("/groups-list")
    public GroupListResponseDto getGroupsList(@RequestHeader("phone") String phone) {
        log.info("Request to get groups list for phone: {}", phone);
        return groupService.getGroupsList(phone);
    }

}
