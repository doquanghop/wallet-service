package io.github.doquanghop.walletsystem.shared.types;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BasePaginationRequest {
    private String sortBy = "createdAt";
    private int page = 0;
    private int pageSize = 10;
}
