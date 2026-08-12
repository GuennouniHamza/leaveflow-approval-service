package com.leaveflow.approval_service.controller;

import com.leaveflow.approval_service.service.SagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sagas")
@RequiredArgsConstructor
public class SagaController {

    private final SagaService sagaService;

    @PostMapping("/{id}/decision")
    public ResponseEntity<Void> decider(
            @PathVariable Long id,
            @RequestParam boolean approuvee,
            @RequestParam(required = false) String commentaire) {
        sagaService.traiterDecision(id, approuvee, commentaire);
        return ResponseEntity.ok().build();
    }
}