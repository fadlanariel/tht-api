package com.fadlan.tht.service;

import com.fadlan.tht.dto.ServiceDto;
import com.fadlan.tht.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceDto> getAllServices() throws SQLException {
        return serviceRepository.findAllServices();
    }
}
