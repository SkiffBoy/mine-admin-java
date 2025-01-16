package com.skiffboy.mine.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.model.entity.AttachmentEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.response.PageResponse;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.model.vo.Attachment;
import com.skiffboy.mine.admin.service.IAttachmentService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 上传文件信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "数据中心")
@RestController
@RequestMapping("/admin/attachment")
public class AttachmentController extends AbstractController {

    @Resource
    private IAttachmentService attachmentService;
    @Resource
    private Converter converter;

    @Operation(
            operationId = "AttachmentList",
            summary = "附件列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "dataCenter:attachment:list", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<PageResponse<Attachment>> list(@RequestParam(required = false) Map<String, Object> params) {
        params.put("current_user_id", CurrentUser.getId());
        Page<AttachmentEntity> pageInfo = attachmentService.page(params);
        List<Attachment> list = converter.convert(pageInfo.getRecords(), Attachment.class);
        return success(new PageResponse(pageInfo.getTotalRow(), list));
    }

    @Operation(
            operationId = "UploadAttachment",
            summary = "上传附件",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "dataCenter:attachment:upload", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result upload(MultipartFile file) {
        AttachmentEntity attachmentEntity = attachmentService.upload(file);
        Attachment schema = converter.convert(attachmentEntity, Attachment.class);
        return success(schema);
    }

    @Operation(
            operationId = "DeleteAttachment",
            summary = "删除附件"
    )
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "dataCenter:attachment:delete", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delete(@PathVariable("id") long id) {
        if (!attachmentService.existsById(id)) {
            return error("attachment.attachment_not_exist");
        }
        attachmentService.deleteById(id);
        return success();
    }

}