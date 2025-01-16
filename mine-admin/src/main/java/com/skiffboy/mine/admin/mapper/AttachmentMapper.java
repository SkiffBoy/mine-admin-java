package com.skiffboy.mine.admin.mapper;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.skiffboy.mine.admin.model.entity.AttachmentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 上传文件信息表 映射层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Mapper
public interface AttachmentMapper extends BaseMapper<AttachmentEntity> {

    default boolean existsById(Serializable id) {
        QueryWrapper queryWrapper = QueryWrapper.create().from(AttachmentEntity.class);
        long count = selectCountByQuery(queryWrapper.eq(AttachmentEntity::getId, id));
        return count != 0;
    }

    default AttachmentEntity findByHash(String hash) {
        QueryWrapper queryWrapper = QueryWrapper.create().from(AttachmentEntity.class);
        return selectOneByQuery(queryWrapper.eq(AttachmentEntity::getHash, hash));
    }

    default AttachmentEntity findByUrl(String url) {
        QueryWrapper queryWrapper = QueryWrapper.create().from(AttachmentEntity.class);
        return selectOneByQuery(queryWrapper.eq(AttachmentEntity::getUrl, url));
    }

    default boolean deleteByUrl(String url) {
        QueryWrapper queryWrapper = QueryWrapper.create().from(AttachmentEntity.class);
        return deleteByQuery(queryWrapper.eq(AttachmentEntity::getUrl, url)) != 0;
    }

    default QueryWrapper handleSearch(Map<String, Object> params) {
        QueryWrapper query = QueryWrapper.create().from(AttachmentEntity.class);
        if (params.containsKey("suffix")) {
            String suffixString = (String) params.get("suffix");
            String[] suffixArray = suffixString.split(",");
            List<String> suffixList = new ArrayList<>();
            for (String suffix : suffixArray) {
                suffixList.add(suffix);
            }
            params.put("suffix", suffixList);
            query = query.in(AttachmentEntity::getSuffix, suffixList);
        }
        return query
                .like(AttachmentEntity::getOriginName, MapUtil.getStr(params, "origin_name"),
                        StrUtil.isNotEmpty(MapUtil.getStr(params, "origin_name")))
                ;
    }

}
