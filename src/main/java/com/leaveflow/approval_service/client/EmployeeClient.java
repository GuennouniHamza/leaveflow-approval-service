package com.leaveflow.approval_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.leaveflow.approval_service.dto.EmployeeDTO;

@FeignClient(name="employee-service", url ="${employee-service.url}")
public interface EmployeeClient {
@GetMapping("/api/employee/{id}")
EmployeeDTO getById(@PathVariable Long id);
}
