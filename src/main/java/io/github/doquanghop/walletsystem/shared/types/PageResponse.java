package io.github.doquanghop.walletsystem.shared.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse<T> implements Serializable {
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<T> data;
    private boolean hasNext;
    private boolean hasPrevious;

    public static <T> PageResponse<T> build(Page<T> page) {
        return PageResponse.<T>builder()
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .data(page.getContent())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }

    public static <T> PageResponse<T> build(Page<T> page, List<T> data) {
        return PageResponse.<T>builder()
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .data(data)
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}