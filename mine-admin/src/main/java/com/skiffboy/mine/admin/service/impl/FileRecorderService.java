package com.skiffboy.mine.admin.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.update.UpdateChain;
import com.skiffboy.mine.admin.enums.common.StorageMode;
import com.skiffboy.mine.admin.mapper.AttachmentMapper;
import com.skiffboy.mine.admin.model.entity.AttachmentEntity;
import com.skiffboy.mine.admin.model.holder.CurrentUser;
import jakarta.annotation.Resource;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.constant.Constant;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.dromara.x.file.storage.spring.SpringFileStorageProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileRecorderService implements FileRecorder {
    @Resource
    private AttachmentMapper attachmentMapper;
    @Resource
    private SpringFileStorageProperties springFileStorageProperties;
    private FileStorageService fileStorageService;

    @Override
    public boolean save(FileInfo fileInfo) {
        AttachmentEntity attachment = new AttachmentEntity();
        String fileHash = fileInfo.getHashInfo() == null ? null : fileInfo.getHashInfo().getMd5();
        AttachmentEntity existFile = attachmentMapper.findByHash(fileHash);
        if (existFile != null) {
            // 同一份文件多次上传，删除本次上传的文件
            if (fileStorageService == null) {
                fileStorageService = SpringUtil.getBean(FileStorageService.class);
            }
            fileStorageService.delete(fileInfo);

            // 更新信息
            UpdateChain.of(AttachmentEntity.class)
                    .set(AttachmentEntity::getUpdatedBy, CurrentUser.getId())
                    .set(AttachmentEntity::getUpdatedAt, new Date())
                    .where(AttachmentEntity::getId).eq(existFile.getId())
                    .update();

            return true;
        }
        attachment.setCreatedBy(CurrentUser.getId());
        attachment.setCreatedAt(new Date());
        attachment.setUpdatedBy(0L);
        attachment.setOriginName(fileInfo.getOriginalFilename());
        attachment.setStorageMode(StorageMode.local);
        attachment.setStoragePath(fileInfo.getPath());
        attachment.setObjectName(fileInfo.getFilename());
        attachment.setMimeType(fileInfo.getContentType());
        attachment.setHash(fileHash);
        attachment.setSuffix(fileInfo.getExt());
        attachment.setSizeByte(fileInfo.getSize());
        attachment.setSizeInfo(getHumanSize(fileInfo.getSize()));
        attachment.setUrl(fileInfo.getUrl());
        attachment.setRemark(JSONObject.toJSONString(fileInfo.getAttr()));

        attachmentMapper.insert(attachment);
        return attachment.getId() != null;
    }

    @Override
    public void update(FileInfo fileInfo) {
        long attachmentId = Long.valueOf(fileInfo.getId());
        UpdateChain.of(AttachmentEntity.class)
                .set(AttachmentEntity::getUpdatedBy, CurrentUser.getId())
                .set(AttachmentEntity::getUpdatedAt, new Date())
                .where(AttachmentEntity::getId).eq(attachmentId)
                .update();
    }

    @Override
    public FileInfo getByUrl(String url) {
        AttachmentEntity existFile = attachmentMapper.findByUrl(url);
        if (existFile != null) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(Long.toString(existFile.getId()));
            fileInfo.setUrl(existFile.getUrl());
            fileInfo.setSize(existFile.getSizeByte());
            fileInfo.setBasePath(existFile.getStoragePath());
            fileInfo.setFilename(existFile.getObjectName());
            fileInfo.setOriginalFilename(existFile.getOriginName());
            fileInfo.setExt(existFile.getSuffix());
            fileInfo.setPlatform(springFileStorageProperties.getDefaultPlatform());
            fileInfo.setContentType(existFile.getMimeType());
            fileInfo.setHashInfo(new HashInfo(MapBuilder.<String, String>create()
                    .put(Constant.Hash.MessageDigest.MD5, existFile.getHash()).build()));
            if (StrUtil.isNotEmpty(existFile.getRemark())) {
                Dict attr = JSONObject.parseObject(existFile.getRemark(), Dict.class);
                fileInfo.setAttr(attr);
            }

            return fileInfo;
        }
        return null;
    }

    @Override
    public boolean delete(String url) {
        return attachmentMapper.deleteByUrl(url);
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {

    }

    @Override
    public void deleteFilePartByUploadId(String uploadId) {

    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    public static String getHumanSize(long size) {
        if (size <= 0) {
            return "0 B";
        }

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
