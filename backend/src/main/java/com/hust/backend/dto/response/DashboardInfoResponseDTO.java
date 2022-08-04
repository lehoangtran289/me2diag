package com.hust.backend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardInfoResponseDTO {
    private Integer totalUsers;
    private Integer totalExperts;
    private Integer totalMalePatients;
    private Integer totalFemalePatients;
    private Integer totalPFSExams;
    private Integer totalKDCExams;
    private List<PatientInfoResponseDTO> recentPatients;
    private List<TopUserInfoResponseDTO> topUsers;
}
