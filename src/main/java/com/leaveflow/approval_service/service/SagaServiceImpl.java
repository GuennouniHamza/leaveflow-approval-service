package com.leaveflow.approval_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leaveflow.approval_service.entity.EtatSaga;
import com.leaveflow.approval_service.entity.SagaInstance;
import com.leaveflow.approval_service.event.ApprovalDecisionEvent;
import com.leaveflow.approval_service.repository.SagaInstanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SagaServiceImpl implements SagaService {

    private final SagaInstanceRepository sagaInstanceRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final int SEUIL_JOURS_RH = 10;

    @Override
    public void traiterDecision(Long sagaId, boolean approuvee, String commentaire) {
        SagaInstance saga = sagaInstanceRepository.findById(sagaId)
                .orElseThrow(() -> new RuntimeException("Saga introuvable : " + sagaId));

        saga.setCommentaireManager(commentaire);

        if (!approuvee) {
            saga.setEtat(EtatSaga.REFUSEE);
            sagaInstanceRepository.save(saga);
            publierDecision(saga, false, commentaire);
            return;
        }

        saga.setEtat(EtatSaga.APPROUVEE);
        sagaInstanceRepository.save(saga);
        publierDecision(saga, true, commentaire);
    }

    private void publierDecision(SagaInstance saga, boolean approuvee, String commentaire) {
        ApprovalDecisionEvent event = new ApprovalDecisionEvent(
                saga.getLeaveRequestId(), saga.getEmployeeId(), approuvee, commentaire);
        kafkaTemplate.send("approval-decisions", event);
    }
}