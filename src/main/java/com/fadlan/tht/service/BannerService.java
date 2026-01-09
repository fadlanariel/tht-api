package com.fadlan.tht.service;

import com.fadlan.tht.dto.BannerDto;
import com.fadlan.tht.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<BannerDto> getAllBanners() {
        return bannerRepository.findAll();
    }
}
