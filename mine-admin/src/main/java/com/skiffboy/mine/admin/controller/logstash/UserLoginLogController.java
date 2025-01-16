package com.skiffboy.mine.admin.controller.logstash;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.skiffboy.mine.admin.controller.base.AbstractController;
import com.skiffboy.mine.admin.model.entity.UserLoginLogEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import com.skiffboy.mine.admin.model.request.DeleteRequest;
import com.skiffboy.mine.admin.model.response.PageResponse;
import com.skiffboy.mine.admin.model.response.Result;
import com.skiffboy.mine.admin.model.vo.UserLoginLog;
import com.skiffboy.mine.admin.service.IUserLoginLogService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 登录日志表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "系统管理")
@RestController
@RequestMapping("/admin/user-login-log")
public class UserLoginLogController extends AbstractController {

    @Resource
    private IUserLoginLogService userLoginLogService;
    @Resource
    private Converter converter;

    @Operation(
            operationId = "UserLoginLogList",
            summary = "用户登录日志列表",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "log:userLogin:list", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<PageResponse<UserLoginLog>> page(@RequestParam(required = false) Map<String, Object> params) {
        Page<UserLoginLogEntity> pageInfo = userLoginLogService.page(params);
        List<UserLoginLog> list = converter.convert(pageInfo.getRecords(), UserLoginLog.class);
        return success(new PageResponse(pageInfo.getTotalRow(), list));
    }

    @Operation(
            operationId = "UserLoginLogDelete",
            summary = "删除用户登录日志",
            security = {
                    @SecurityRequirement(name = "Bearer"),
                    @SecurityRequirement(name = "ApiKey")
            })
    @ApiResponse(responseCode = "200")
    @SaCheckPermission(value = "log:userLogin:delete", orRole = CurrentUser.ROLE_CODE_SUPER_ADMIN)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Result delete(@RequestBody @Valid DeleteRequest request) {
        boolean success = userLoginLogService.removeByIds(request.getIds());
        return success ? success() : error(null);
    }

}