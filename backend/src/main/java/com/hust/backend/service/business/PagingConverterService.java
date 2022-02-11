package com.hust.backend.service.business;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PagingConverterService {
    Sort from(String[] sort);
    Pageable page(int pageNo, int pageSize, String[] sort);
}
