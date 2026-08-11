package com.leaveflow.approval_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leaveflow.approval_service.entity.SagaInstance;

public interface SagaInstanceRepository extends JpaRepository<SagaInstance,Long> {
	Optional<SagaInstance> findByLeaveRequestId(Long leaveRequestId);
    boolean existsByLeaveRequestId(Long leaveRequestId);
}
