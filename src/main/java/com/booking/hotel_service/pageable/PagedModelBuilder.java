package com.booking.hotel_service.pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import com.booking.common.Resource;

public class PagedModelBuilder {
	public static <T> PagedModel<Resource<T>> build(Page<?> page, List<Resource<T>> content) {
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
            page.getSize(),
            page.getNumber(),
            page.getTotalElements(),
            page.getTotalPages()
        );
        return PagedModel.of(content, metadata);
    }
}
