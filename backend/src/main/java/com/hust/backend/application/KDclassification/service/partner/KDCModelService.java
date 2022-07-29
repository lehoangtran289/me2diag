package com.hust.backend.application.KDclassification.service.partner;

import com.hust.backend.application.KDclassification.dto.request.KDCModelInputRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ep-tracking-cms-be", url = "${kdc-model.host}",
             configuration = KDCModelFeignConfig.class)
public interface KDCModelService {
    @PostMapping("/kdc/api/v1/predict")
    String predictDisease(KDCModelInputRequestDTO request);
}
