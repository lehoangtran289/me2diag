package com.hust.backend.service.business.impl;

import com.hust.backend.constant.ApplicationEnum;
import com.hust.backend.constant.UserGenderEnum;
import com.hust.backend.constant.UserRoleEnum;
import com.hust.backend.dto.response.*;
import com.hust.backend.entity.UserEntity;
import com.hust.backend.repository.ExamRepository;
import com.hust.backend.repository.PatientRepository;
import com.hust.backend.repository.UserRepository;
import com.hust.backend.service.business.DashboardService;
import com.hust.backend.service.business.ExamService;
import com.hust.backend.utils.Common;
import com.hust.backend.utils.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final ExamService examService;

    public DashboardServiceImpl(PatientRepository patientRepository,
                                UserRepository userRepository,
                                ExamRepository examRepository,
                                ExamService examService) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.examService = examService;
    }

    // Default recentPatients List -> 7
    @Override
    public DashboardInfoResponseDTO getGeneralDashboardData(Integer listSize) {
        // get recent patient list
        List<PatientInfoResponseDTO> recentPatients = Transformer.listToList(
                patientRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, listSize)).getContent(),
                e -> Common.convertObject(e, PatientInfoResponseDTO.class)
        );

        // get recent examination list
        List<ExaminationResponseDTO> recentExams = examService
                .getAllExaminations(null, null, PageRequest.of(0, listSize)).getItems();

        // get top doctors list
        List<TopUserInfoResponseDTO> topUsers = new ArrayList<>();
        List<Object[]> topDoctors = userRepository.getTopDoctors(listSize);
        for (Object[] topDoctor : topDoctors) {
            UserEntity u = (UserEntity) topDoctor[0];
            Long count = (Long) topDoctor[1];
            topUsers.add(
                    TopUserInfoResponseDTO.builder()
                            .user(Common.convertObject(u, UserInfoResponseDTO.class))
                            .totalExams(count)
                            .build()
            );
        }
        return DashboardInfoResponseDTO.builder()
                .totalUsers(userRepository.countUsersByRole(UserRoleEnum.USER))
                .totalExperts(userRepository.countUsersByRole(UserRoleEnum.EXPERT))
                .totalMalePatients(patientRepository.countByGenderLike(UserGenderEnum.MALE))
                .totalFemalePatients(patientRepository.countByGenderLike(UserGenderEnum.FEMALE))
                .totalPFSExams(examRepository.countByAppId(ApplicationEnum.PFS))
                .totalKDCExams(examRepository.countByAppId(ApplicationEnum.KDC))
                .topUsers(topUsers)
                .recentPatients(recentPatients)
                .recentExams(recentExams)
                .build();
    }
}
