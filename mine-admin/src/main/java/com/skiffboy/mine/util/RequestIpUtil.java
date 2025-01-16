package com.skiffboy.mine.util;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
public final class RequestIpUtil {
    /**
     * IP地区的分隔符
     */
    private static final String REGION_SPLIT_SYMBOL = "\\|";
    /**
     * 未知IP的地区
     */
    private static final String REGION_UNKNOWN = "0";
    /**
     * X_FORWARDED 请求头中的 IP 拆分符号
     */
    private static final String IP_SPLIT_SYMBOL = ",";
    /**
     * 未知IP
     */
    private static final String UNKNOWN_IP = "unknown";
    /**
     * 代理服务器获得的客户端真实IP的请求头
     */
    private static final String[] IP_HEADERS = {
            "X-Forwarded-For",
            "X-Real-IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
    };

    private static Searcher IP_SEARCHER;

    /**
     * 初始化数据
     *
     * @param filePath
     */
    public static void init(String filePath) {
        try {
            byte[] cBuff = Searcher.loadContentFromFile(filePath);
            IP_SEARCHER = Searcher.newWithBuffer(cBuff);

        } catch (Throwable e) {
            log.error("初始化ip2region.xdb文件失败,报错信息:[{}]", e.getMessage(), e);
            throw new RuntimeException("系统异常!");
        }
    }

    @PostConstruct
    private void postConstruct() {
        init(this.getClass().getResource("/").getPath() + "ip2region.xdb");
    }

    /**
     * 自定义解析ip地址
     *
     * @param ipStr ipStr
     * @return 返回结果例 河南省,洛阳市,洛龙区
     */
    public static String getRegion(String ipStr) {
        try {
            if (StrUtil.isEmpty(ipStr)) {
                return StrUtil.EMPTY;
            }
            ipStr = ipStr.trim();
            String region = IP_SEARCHER.search(ipStr);
            return Arrays.stream(region.split(REGION_SPLIT_SYMBOL))
                    .filter(s -> !REGION_UNKNOWN.equals(s))
                    .distinct().collect(Collectors.joining(","));
        } catch (Exception e) {
            log.error("解析ip地址出错", e);
            return StrUtil.EMPTY;
        }
    }

    /**
     * 根据用户请求，获取用户的IPV4地址
     *
     * @param request
     * @return 当前请求的IP地址
     */
    public static String getIpv4Address(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = null;
        // 根据请求头，依次获取IP
        for (String header : IP_HEADERS) {
            ip = request.getHeader(header);
            // 如果有IP结果，跳出循环
            if (!isUnknown(ip)) {
                break;
            }
        }
        // 如果经过了多层代理，请求头中得到的IP地址将会是多个IP以英文逗号分隔的IP列表
        if (ip != null && ip.contains(IP_SPLIT_SYMBOL)) {
            ip = ip.substring(0, ip.indexOf(IP_SPLIT_SYMBOL));
        }
        // 根据请求头无法获取到IP，使用Servlet容器获取IP
        if (isUnknown(ip)) {
            // 在中间没有（负载均衡）代理的情况下（客户端直接连接本服务器），拿到的是客户端的IP
            String remoteAddr = request.getRemoteAddr();
            // 如果IP是本机IP中的任意一个，根据网卡取本机配置的IP
            ip = remoteAddr;
        }
        return ip;
    }

    /**
     * 判断IP是否为未知IP（无法获取到IP）
     *
     * @param ip IP地址
     * @return
     */
    public static boolean isUnknown(String ip) {
        return !StringUtils.hasText(ip) || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip);
    }
}
