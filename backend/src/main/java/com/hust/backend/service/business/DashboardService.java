package com.hust.backend.service.business;

import com.hust.backend.dto.response.DashboardInfoResponseDTO;

public interface DashboardService {
    DashboardInfoResponseDTO getGeneralDashboardData(Integer listSize);
}
