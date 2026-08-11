package com.leaveflow.approval_service.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestedEvent {
    private Long leaveRequestId;
    private Long employeeId;
    private Long leaveTypeId;
    private Integer nombreJours;
}