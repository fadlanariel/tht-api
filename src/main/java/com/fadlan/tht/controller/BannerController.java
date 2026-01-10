package com.fadlan.tht.controller;

import com.fadlan.tht.dto.response.ApiResponse;
import com.fadlan.tht.dto.BannerDto;
import com.fadlan.tht.service.BannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banner")
@Tag(name = "Information")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BannerDto>>> getBanner() {
        List<BannerDto> banners = bannerService.getAllBanners();
        ApiResponse<List<BannerDto>> response = new ApiResponse<>(0, "Sukses", banners);
        return ResponseEntity.ok(response);
    }
}
