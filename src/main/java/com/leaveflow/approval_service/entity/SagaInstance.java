package com.leaveflow.approval_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "saga_instances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaInstance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "leave_request_id", nullable = false, unique = true)
    private Long leaveRequestId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "manager_id")
    private Long managerId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatSaga etat;

    private String commentaireDecision;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
