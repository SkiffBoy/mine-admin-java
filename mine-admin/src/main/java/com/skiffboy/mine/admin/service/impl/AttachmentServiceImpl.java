package com.skiffboy.mine.admin.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.skiffboy.mine.admin.mapper.AttachmentMapper;
import com.skiffboy.mine.admin.model.entity.AttachmentEntity;
import com.skiffboy.mine.admin.service.IAttachmentService;
import com.skiffboy.mine.util.PageParamUtil;
import jakarta.annotation.Resource;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATE_PATTERN;

/**
 * 上传文件信息表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, AttachmentEntity> implements IAttachmentService {
    @Resource
    private FileStorageService fileStorageService;

    @Override
    public Page<AttachmentEntity> page(Map<String, Object> params) {
        return page(PageParamUtil.getPage(params), mapper.handleSearch(params));
    }

    @Override
    public AttachmentEntity upload(MultipartFile uploadedFile) {
        // 如果要先计算 MD5，则需要读取一遍文件内容，才能计算 MD5，再上传，累计会读取 2 遍
        String fileExt = FileNameUtil.extName(uploadedFile.getOriginalFilename());
        String fileName = IdUtil.fastUUID() + (StrUtil.isEmpty(fileExt) ? StrUtil.EMPTY : "." + fileExt);
        String storagePath = DateUtil.format(new Date(), NORM_DATE_PATTERN) + "/";
        FileInfo uploadResult = fileStorageService.of(uploadedFile)
                // 相对于 basePath 的路径
                .setPath(storagePath)
                .setOriginalFilename(uploadedFile.getOriginalFilename())
                .setSaveFilename(fileName)
                .setHashCalculatorMd5()
                .upload();
        if (uploadResult == null) {
            throw new RuntimeException("上传失败！");
        }
        return mapper.findByHash(uploadResult.getHashInfo().getMd5());
    }

    @Override
    public boolean existsById(long id) {
        return mapper.existsById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        AttachmentEntity entity = mapper.selectOneById(id);
        boolean fileDeleted = fileStorageService.delete(entity.getUrl());
        if (!fileDeleted) {
            throw new RuntimeException("删除失败");
        }
    }

}