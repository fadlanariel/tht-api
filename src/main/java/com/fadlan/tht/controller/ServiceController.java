package com.fadlan.tht.controller;

import com.fadlan.tht.dto.ServiceDto;
import com.fadlan.tht.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
@Tag(name = "Information")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    @Operation(summary = "Get list of PPOB services", description = "Private, requires JWT Bearer token")
    public ResponseEntity<?> getServices() {
        try {
            List<ServiceDto> services = serviceService.getAllServices();
            Map<String, Object> response = new HashMap<>();
            response.put("status", 0);
            response.put("message", "Sukses");
            response.put("data", services);

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 500);
            error.put("message", "Terjadi kesalahan pada server");
            error.put("data", null);
            return ResponseEntity.status(500).body(error);
        }
    }
}
