package cn.cariton.apps.douyin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author icnz
 * @date 2023-12-20 15:59
 */
public class DouYinUtils {
    private static final String VIDEO_SEARCH_URL = "https://www.douyin.com/aweme/v1/web/search/item/";
    private static final String COMMENT_URL = "https://www.douyin.com/aweme/v1/web/comment/list/";
    private static final String REPLY_URL = "https://www.douyin.com/aweme/v1/web/comment/list/reply/";

    /**
     * sort_type: 0:综合排序；1：点赞最多；2最新发布
     * publish_time: 0：不限；1：1天内；7：一周内；182：半年内
     */
    public static String searchVideo(String keyword, int sortType, int publishTime, String cookies, int offset,
                                     int count) {
        Map<String, String> params = new HashMap<>();
        params.put("device_platform", "webapp");
        params.put("aid", "6383");
        params.put("channel", "channel_pc_web");
        params.put("search_channel", "channel_pc_web");
        params.put("sort_type", String.valueOf(sortType));
        params.put("publish_time", String.valueOf(publishTime));
        params.put("keyword", keyword);
        params.put("search_source", "tab_search");
        params.put("query_correct_type", "1");
        params.put("is_filter_search", "1");
        params.put("from_group_id", "");
        params.put("offset", String.valueOf(offset));
        params.put("count", String.valueOf(count));
        params.put("pc_client_type", "1");
        params.put("version_code", "170400");
        params.put("version_name", "17.4.0");
        params.put("cookie_enabled", "true");
        params.put("screen_width", "1920");
        params.put("screen_height", "1080");
        params.put("browser_language", "zh-CN");
        params.put("browser_platform", "Win32");
        params.put("browser_name", "Edge");
        params.put("browser_version", "120.0.0.0");
        params.put("browser_online", "true");
        params.put("engine_name", "Blink");
        params.put("engine_version", "120.0.0.0");
        params.put("os_name", "Windows");
        params.put("os_version", "10");
        params.put("cpu_core_num", "16");
        params.put("device_memory", "8");
        params.put("platform", "PC");
        params.put("downlink", "10");
        params.put("effective_type", "4g");
        params.put("round_trip_time", "50");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Referer", "https://www.douyin.com/");
        headers.put("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Microsoft Edge\";v=\"120\"");
        headers.put("Sec-Ch-Ua-Mobile", "?0");
        headers.put("Sec-Ch-Ua-Platform", "\"Windows\"");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        headers.put("Cookie", cookies);
        return HttpClientUtils.get(VIDEO_SEARCH_URL, params, headers);
    }

    public static String parseComment(String videoId, String cookies, int cursor, int count) {
        Map<String, String> params = new HashMap<>();
        params.put("device_platform", "webapp");
        params.put("aid", "6383");
        params.put("channel", "channel_pc_web");
        params.put("aweme_id", videoId);
        params.put("cursor", String.valueOf(cursor));
        params.put("count", String.valueOf(count));
        params.put("item_type", "0");
        params.put("whale_cut_token", "");
        params.put("cut_version", "1");
        params.put("rcFT", "");
        params.put("pc_client_type", "1");
        params.put("version_code", "170400");
        params.put("version_name", "17.4.0");
        params.put("cookie_enabled", "true");
        params.put("screen_width", "1920");
        params.put("screen_height", "1080");
        params.put("browser_language", "zh-CN");
        params.put("browser_platform", "Win32");
        params.put("browser_name", "Edge");
        params.put("browser_version", "120.0.0.0");
        params.put("browser_online", "true");
        params.put("engine_name", "Blink");
        params.put("engine_version", "120.0.0.0");
        params.put("os_name", "Windows");
        params.put("os_version", "10");
        params.put("cpu_core_num", "16");
        params.put("device_memory", "8");
        params.put("platform", "PC");
        params.put("downlink", "10");
        params.put("effective_type", "4g");
        params.put("round_trip_time", "50");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Referer", "https://www.douyin.com/");
        headers.put("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Microsoft Edge\";v=\"120\"");
        headers.put("Sec-Ch-Ua-Mobile", "?0");
        headers.put("Sec-Ch-Ua-Platform", "\"Windows\"");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        headers.put("Cookie", cookies);
        return HttpClientUtils.get(COMMENT_URL, params, headers);
    }

    public static String parseReply(String videoId, String commentId, String cookies, int cursor, int count) {
        Map<String, String> params = new HashMap<>();
        params.put("device_platform", "webapp");
        params.put("aid", "6383");
        params.put("channel", "channel_pc_web");
        params.put("item_id", videoId);
        params.put("comment_id", commentId);
        params.put("cursor", String.valueOf(cursor));
        params.put("count", String.valueOf(count));
        params.put("item_type", "0");
        params.put("whale_cut_token", "");
        params.put("cut_version", "1");
        // params.put("rcFT", "");
        params.put("pc_client_type", "1");
        params.put("version_code", "170400");
        params.put("version_name", "17.4.0");
        params.put("cookie_enabled", "true");
        params.put("screen_width", "1920");
        params.put("screen_height", "1080");
        params.put("browser_language", "zh-CN");
        params.put("browser_platform", "Win32");
        params.put("browser_name", "Edge");
        params.put("browser_version", "120.0.0.0");
        params.put("browser_online", "true");
        params.put("engine_name", "Blink");
        params.put("engine_version", "120.0.0.0");
        params.put("os_name", "Windows");
        params.put("os_version", "10");
        params.put("cpu_core_num", "16");
        params.put("device_memory", "8");
        params.put("platform", "PC");
        params.put("downlink", "10");
        params.put("effective_type", "4g");
        params.put("round_trip_time", "50");
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Referer", "https://www.douyin.com/");
        headers.put("Sec-Ch-Ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Microsoft Edge\";v=\"120\"");
        headers.put("Sec-Ch-Ua-Mobile", "?0");
        headers.put("Sec-Ch-Ua-Platform", "\"Windows\"");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        headers.put("Cookie", cookies);
        return HttpClientUtils.get(REPLY_URL, params, headers);
    }
}
