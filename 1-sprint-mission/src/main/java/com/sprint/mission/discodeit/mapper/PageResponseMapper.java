package com.sprint.mission.discodeit.mapper;


import com.sprint.mission.discodeit.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public class PageResponseMapper {

  public static <T> PageResponse<T> fromPage(Page<T> page) {
    return new PageResponse<T>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements()
    );
  }

  public static <T> PageResponse<T> fromSlice(Slice<T> slice) {
    return new PageResponse<>(
        slice.getContent(),
        slice.getNumber(),
        slice.getSize(),
        null
    );
  }
}
