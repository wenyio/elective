package com.example.elective.entity.base;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
public class BasePageRPO {


    private int page = 1;

    private int size = 10;

    private String sort = "created";

    private String sortType = "DESC";

    public Pageable buildPageable() {
        Sort sort;
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortType);
        } catch (Exception e) {
            direction = Sort.Direction.DESC;
        }

        if (StringUtils.hasLength(this.sort)) {
            sort = Sort.by(direction, this.sort);
        } else {
            sort = Sort.by(direction, "updated");
        }

        return PageRequest.of(this.page - 1, size, sort);
    }

    public Pageable buildNativeSqlPageable() {
        Sort sort;
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(sortType);
        } catch (Exception e) {
            direction = Sort.Direction.DESC;
        }

        if (StringUtils.hasLength(this.sort)) {

            sort = Sort.by(direction, this.sort);
        } else {
            sort = Sort.by(direction, "created");
        }

        return PageRequest.of(this.page - 1, size, sort);
    }


}
