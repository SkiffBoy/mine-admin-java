package com.skiffboy.mine.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    @Schema(description = "总数量")
    private long total;

    @Schema(description = "数据列表")
    private List<T> list;

    public PageResponse(int total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageResponse(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
