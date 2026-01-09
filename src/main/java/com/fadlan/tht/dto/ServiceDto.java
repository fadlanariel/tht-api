package com.fadlan.tht.dto;

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

    // getters & setters
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }

    public Long getServiceTariff() {
        return serviceTariff;
    }

    public void setServiceTariff(Long serviceTariff) {
        this.serviceTariff = serviceTariff;
    }
}
