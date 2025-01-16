package com.skiffboy.mine.admin.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.skiffboy.mine.admin.model.entity.AttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传文件信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IAttachmentService extends IService<AttachmentEntity> {

    Page<AttachmentEntity> page(Map<String, Object> params);

    AttachmentEntity upload(MultipartFile uploadedFile);

    boolean existsById(long id);

    void deleteById(long id);
}