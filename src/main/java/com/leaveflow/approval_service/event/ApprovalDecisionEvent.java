package com.leaveflow.approval_service.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDecisionEvent {
    private Long leaveRequestId;
    private Long employeeId;
    private boolean approuvee;
    private String commentaire;
}