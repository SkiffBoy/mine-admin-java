package com.skiffboy.mine.admin.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteRequest {
    @NotEmpty(message = "{delete_list}{jakarta.validation.constraints.NotEmpty.message}")
    private List<Long> ids;
}
