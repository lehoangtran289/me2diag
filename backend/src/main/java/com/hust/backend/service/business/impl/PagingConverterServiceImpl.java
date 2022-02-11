package com.hust.backend.service.business.impl;

import com.hust.backend.service.business.PagingConverterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PagingConverterServiceImpl implements PagingConverterService {
    private static final String REGEX = "([+-])?([a-zA-Z]+)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static Sort.Direction direction(String direction) {
        if (direction == null) {
            return Sort.Direction.ASC;
        }
        if ("+".equals(direction)) {
            return Sort.Direction.ASC;
        } else if ("-".equals(direction)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public Sort from(String[] sort) {
        if(sort != null && sort.length != 0){
            List<Sort.Order> orders = new ArrayList<>();
            for (String s : sort) {
                Matcher m = PATTERN.matcher(s);
                if (m.find()) {
                    orders.add(new Sort.Order(direction(m.group(1)), m.group(2)));
                }
            }
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    @Override
    public Pageable page(int pageNo, int pageSize, String[] sort) {
        return PageRequest.of(pageNo, pageSize, this.from(sort));
    }
}
