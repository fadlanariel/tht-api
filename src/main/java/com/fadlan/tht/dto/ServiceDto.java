package com.fadlan.tht.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDto {
    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private Long serviceTariff;

    // constructor
    public ServiceDto(String serviceCode, String serviceName, String serviceIcon, Long serviceTariff) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
        this.serviceTariff = serviceTariff;
    }
}
