package com.leaveflow.approval_service.service;

public interface SagaService {
	void traiterDecision(Long sagaId,boolean approuvee,String commentaire);
}
