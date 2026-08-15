package com.leaveflow.approval_service.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.leaveflow.approval_service.client.EmployeeClient;
import com.leaveflow.approval_service.dto.EmployeeDTO;
import com.leaveflow.approval_service.entity.EtatSaga;
import com.leaveflow.approval_service.entity.SagaInstance;
import com.leaveflow.approval_service.event.LeaveRequestedEvent;
import com.leaveflow.approval_service.repository.SagaInstanceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestedConsumer {
	private final SagaInstanceRepository sagaInstanceRepository;
	private final EmployeeClient employeeClient;
	
	@KafkaListener(topics="leave-requests",groupId="approval-service-group")
	public void handle(LeaveRequestedEvent event) {
		if (sagaInstanceRepository.existsByLeaveRequestId(event.getLeaveRequestId())) {
			log.warn("Saga déjà existante pour leaveRequestId={}, événement ignoré (idempotence)",
					event.getLeaveRequestId());
			return;
		}
		
	EmployeeDTO employee =employeeClient.getById(event.getEmployeeId());
	SagaInstance saga =SagaInstance.builder()
			.leaveRequestId(event.getLeaveRequestId())
            .employeeId(event.getEmployeeId())
            .managerId(employee.getManagerId())
            .etat(EtatSaga.EN_ATTENTE_MANAGER)
            .build();
	
	sagaInstanceRepository.save(saga);
	log.info("Saga créée pour leaveRequestId={}, en attente du manager id={}",
             event.getLeaveRequestId(), employee.getManagerId());
		
	}
	
	
}
