package hackathon.splitwise.mappers;

import hackathon.splitwise.dto.GroupDto;
import hackathon.splitwise.entity.GroupDetailsEntity;
import hackathon.splitwise.entity.UserGroupMappingEntity;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;

public class GroupMapper {

    public static List<GroupDto> mapToGroupDtoList(List<GroupDetailsEntity> groupEntities, List<UserGroupMappingEntity> userGroupMappingEntities, Map<Long, Long> groupMemberCountMap) {
        Map<Long, Double> groupIdToAmountPaidMap = userGroupMappingEntities.stream()
                .collect(Collectors.toMap(UserGroupMappingEntity::getGroupId, UserGroupMappingEntity::getAmountPaid));

        return groupEntities.stream()
                .map(groupEntity -> mapToGroupDto(groupEntity, groupIdToAmountPaidMap.getOrDefault(groupEntity.getId(), 0.0), groupMemberCountMap.getOrDefault(groupEntity.getId(), 0L)))
                .collect(Collectors.toList());
    }

    // Mapper method to convert GroupDetailsEntity to GroupDto, with amountPaid
    public static GroupDto mapToGroupDto(GroupDetailsEntity groupEntity, Double amountPaid, Long memberCount) {
        return new GroupDto(
                groupEntity.getId(),
                groupEntity.getName(),
                groupEntity.getType(),
                groupEntity.getMetadata().get("logo"),
                amountPaid,
                memberCount
        );
    }
}

