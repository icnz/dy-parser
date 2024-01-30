package cn.cariton.apps.douyin.service;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.domain.Comment;
import cn.cariton.apps.douyin.domain.Video;
import cn.cariton.apps.douyin.utils.DouYinUtils;
import cn.cariton.apps.douyin.utils.HttpContextUtils;
import cn.cariton.apps.douyin.utils.StringUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author icnz
 * @date 2023-12-19 17:55
 */
@Service
public class ParserService {
    private final static Logger logger = LoggerFactory.getLogger(ParserService.class);

    @Resource
    private VideoService videoService;
    @Resource
    private CommentService commentService;

    public Result parseVideo(String keyword, int sortType, int publishTime, String cookies) {
        logger.info("parseVideo method(keyword={}, sortType={}, publishTime={},cookies={})", keyword, sortType,
                publishTime, cookies);
        String token = (String) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("TOKEN");
        // 按条件查询视频
        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < 10; i++) { //分次查询
            String searchStr = DouYinUtils.searchVideo(keyword, sortType, publishTime, cookies, 20 * i, 20);
            if (StringUtils.isEmpty(searchStr) || searchStr.equalsIgnoreCase("error")) {
                continue;
            }
            JSONObject searchInfo = JSONObject.parseObject(searchStr);
            JSONArray videoInfos = searchInfo.getJSONArray("data");
            if (Objects.isNull(videoInfos) || videoInfos.size() == 0) {
                break;
            }
            for (int j = 0; j < videoInfos.size(); j++) {
                JSONObject video = videoInfos.getJSONObject(j).getJSONObject("aweme_info");
                Video videoAttr = new Video();
                videoAttr.setVid(video.getString("aweme_id"));
                videoAttr.setDescp(video.getString("desc"));
                videoAttr.setStamp(video.getLong("create_time"));
                videoAttr.setToken(token);
                videos.add(videoAttr);
            }
        }
        if (videos.size() == 0) {
            return Result.fail("未找到相关视频");
        }
        // videoService.saveBatch(videos);
        // 根据视频ID查询评论
        List<Comment> comments = new ArrayList<>();
        videos.forEach(video -> {
            for (int i = 0; i < 2; i++) { //分次查询
                String searchStr = DouYinUtils.parseComment(video.getVid(), cookies, 50 * i, 50);
                if (StringUtils.isEmpty(searchStr) || searchStr.equalsIgnoreCase("error")) {
                    continue;
                }
                JSONObject searchInfo = JSONObject.parseObject(searchStr);
                JSONArray commentInfos = searchInfo.getJSONArray("comments");
                if (Objects.isNull(commentInfos) || commentInfos.size() == 0) {
                    break;
                }
                parseComment(commentInfos, comments, token);
            }
        });
        if (comments.size() == 0) {
            return Result.fail("未找到相关评论");
        }
        // logger.info(Fastjson2Utils.toJSONString(comments));
        commentService.saveBatch(comments);
        // 根据评论ID查询回复
        List<Comment> replies = new ArrayList<>();
        comments.forEach(comment -> {
            String searchStr = DouYinUtils.parseReply(comment.getVid(), comment.getCid(), cookies, 0, 20);
            if (StringUtils.isEmpty(searchStr) || searchStr.equalsIgnoreCase("error")) {
                return;
            }
            JSONObject searchInfo = JSONObject.parseObject(searchStr);
            JSONArray commentInfos = searchInfo.getJSONArray("comments");
            if (Objects.isNull(commentInfos) || commentInfos.size() == 0) {
                return;
            }
            parseComment(commentInfos, replies, token);
        });
        // logger.info(Fastjson2Utils.toJSONString(replies));
        commentService.saveBatch(replies);
        return Result.ok("视频检索完成");
    }

    private void parseComment(JSONArray commentInfos, List<Comment> comments, String token) {
        for (int j = 0; j < commentInfos.size(); j++) {
            JSONObject commentInfo = commentInfos.getJSONObject(j);
            Comment comment = new Comment();
            comment.setCid(commentInfo.getString("cid"));
            comment.setToken(token);
            comment.setText(Objects.isNull(commentInfo.getString("text")) ? "" : commentInfo.getString("text"));
            comment.setVid(/*"https://www.douyin.com/video/" + */commentInfo.getString("aweme_id"));
            comment.setStamp(commentInfo.getLong("create_time"));
            comment.setArea(commentInfo.getString("ip_label"));
            JSONArray imageList = commentInfo.getJSONArray("image_list");
            String[] imageArr = {null};
            if (Objects.nonNull(imageList) && imageList.size() > 0) {
                for (int k = 0; k < imageList.size(); k++) {
                    imageArr[k] = imageList.getJSONObject(k).getJSONObject("thumb_url").getString("uri");
                }
            }
            comment.setImages(Arrays.toString(imageArr));
            comment.setRid(commentInfo.getString("reply_id"));
            JSONObject userInfo = commentInfo.getJSONObject("user");
            comment.setUid(userInfo.getString("uid"));
            comment.setSid(userInfo.getString("short_id"));
            comment.setNickname(userInfo.getString("nickname"));
            comment.setAvatar(/*"https://p3-pc.douyinpic.com/aweme/100x100/" + */userInfo.getString("avatar_uri"));
            comment.setSuid(/*"https://www.douyin.com/user/" + */userInfo.getString("sec_uid"));
            comments.add(comment);
        }
    }

    // public static void main(String[] args) {
    //     String keyword = "厌学";
    //     int sortType = 2;
    //     int publishTime = 7;
    //     String cookies = "__security_server_data_status=1;SEARCH_RESULT_LIST_TYPE=%22single%22;" +
    //             "sessionid_ss=74db563a0698d6f35887155d80c67ed3;=douyin.com;" +
    //             "passport_auth_status_ss=36df348a6e804081debeb26f47e31b76%2C;" +
    //             "s_v_web_id=verify_lq6dnya6_8Y1Fl7o7_YFRR_4pnC_9Ceu_QVTQ6j6Koise;LOGIN_STATUS=1;ssid_ucp_sso_v1=1
    //             .0" +
    //             ".0-KDkzZGZhMWEwY2IzNjc2OWVjYWE1YzI4YjJlMWYyZTY5NDcwMTgzNjkKHQjQpuGElwMQycvwqwYY7zEgDDC7" +
    //             "-IPhBTgGQPQHGgJobCIgNGI1MzViZGY1OTZkZWRlNzM1MDA5NmJkMDk2Njk0Njc;" +
    //             "passport_csrf_token=6c78dd2d1a2af16453b84d6e2d0da8e5;home_can_add_dy_2_desktop=%221%22;" +
    //             "ttcid=6bb6076bd06f4ef9b41bbfa4169d5d4c88;" +
    //             "tt_scid=CGoghthCWnKthpIp5BGZpnwASp9x6xHQ0dOobIYS1jiHv09b6WnOiua0okUFFrAL839c;" +
    //             "_bd_ticket_crypt_cookie=857db2cdb276874ec592762e6b2c80cf;passport_fe_beating_status=true;" +
    //             "sid_ucp_v1=1.0" +
    //             "
    //             .0-KDNiNTY5NDJjMTUzN2U5Mjk0NjJkNWNjMjViMGM1OWUwZmIyMGZkNzAKGQjQpuGElwMQ2MvwqwYY7zEgDDgGQPQHSAQaAmxxIiA3NGRiNTYzYTA2OThkNmYzNTg4NzE1NWQ4MGM2N2VkMw;device_web_memory_size=8;FOLLOW_NUMBER_YELLOW_POINT_INFO=%22MS4wLjABAAAABXblu-oV3FdO4cqStfMbjdfcGITU-IlQSDbkQ5ECrmQ%2F1702915200000%2F1702862628501%2F0%2F1702887632285%22;toutiao_sso_user=4b535bdf596dede7350096bd09669467;sid_ucp_sso_v1=1.0.0-KDkzZGZhMWEwY2IzNjc2OWVjYWE1YzI4YjJlMWYyZTY5NDcwMTgzNjkKHQjQpuGElwMQycvwqwYY7zEgDDC7-IPhBTgGQPQHGgJobCIgNGI1MzViZGY1OTZkZWRlNzM1MDA5NmJkMDk2Njk0Njc;csrf_session_id=379d6e386bd085e7019604b119832ea6;volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.5%7D;FOLLOW_LIVE_POINT_INFO=%22MS4wLjABAAAABXblu-oV3FdO4cqStfMbjdfcGITU-IlQSDbkQ5ECrmQ%2F1703001600000%2F0%2F1702980760719%2F0%22;sso_uid_tt=e02c6688978af991f94fbbbd6aaa106c;stream_player_status_params=%22%7B%5C%22is_auto_play%5C%22%3A0%2C%5C%22is_full_screen%5C%22%3A0%2C%5C%22is_full_webscreen%5C%22%3A0%2C%5C%22is_mute%5C%22%3A1%2C%5C%22is_speed%5C%22%3A1%2C%5C%22is_visible%5C%22%3A0%7D%22;msToken=TA92OdwdiTxL_hi5ZBxoCQ3wM5mTfNILe2TEwBoyJu0_LhFuEvT9TDJQfhivzo2MgWop8CzLZfLKXsZzk81eEUxyYjT0fqsj3ZGNI8PK5Hk71bK-p20=;bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCR3Y4WVBENm9GRHVybjdDS0tHS1lwbnhvY2QxU0FpdEhGOGFscUQwMk1KQlNVSFRIeUlnazQ0Um8xdWUxdlkyUXpOc1hsMlVScWRXS0dLRFk4a1dUQjQ9IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoxfQ%3D%3D;sid_guard=74db563a0698d6f35887155d80c67ed3%7C1702634968%7C5183988%7CTue%2C+13-Feb-2024+10%3A09%3A16+GMT;ttwid=1%7CYYP_dqKUrRyQrqoIbcgneMFu0NsfHOFaacUYmo0e6KU%7C1702868339%7Cd30a9a963de96e6bc8e413af6b33fac7ce123addcddb09f643a44773a2010cb4;store-region-src=uid;sso_uid_tt_ss=e02c6688978af991f94fbbbd6aaa106c;stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1920%2C%5C%22screen_height%5C%22%3A1080%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A16%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A10%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22;__ac_signature=_02B4Z6wo00f01zSBxbQAAIDAQq4Y-vkmQkM0ocEAAKir4vxuxh6o7kjIH3mGa94FjyOUi1LGyb-OBLJmwqJxerpbBcTSXvbrrNr-hpRNQRBcjs3GCeOClz-ezGrlvqg2aYQyCXAslEn9Qus-b3;_bd_ticket_crypt_doamin=2;architecture=amd64;bd_ticket_guard_client_web_domain=2;carnival_live_pull=1702872658980;device_web_cpu_core=16;download_guide=%223%2F20231215%2F1%22;dy_sheight=1080;dy_swidth=1920;FORCE_LOGIN=%7B%22videoConsumedRemainSeconds%22%3A180%2C%22isForcePopClose%22%3A1%7D;IsDouyinActive=true;msToken=slIGbh_l7W7zoQP6N_U2bw8UbS5vEU8FmF8wyZ5P26172YgGHDie3Uilx-uvq9gwdWiyU_aX5SItb5949bNrADgFyj_bfBZ6Ag_PJ28ApCPbe8G65c0=;n_mh=6m_qs7a8Hc38uPnEg2CUrFmHXOvze_JWPPit1sPDphY;odin_tt=92e07e2b4ddea24225e2d02c41462fcab9e3227052bc288414d0a65bba30118b44ee36e57e29d58664c3fd127e47cab4;passport_assist_user=Cj0cLJ1Bs1oc7ObhKDfbNP_xMKRSvhKhRI5kQw0-UG4htsgCO_1cRc8muD3Hcb1VWS3n8LdlSEgrsGH_bLxzGkoKPPo8-phMx-PMLq6jqdF4GpvnOIvaDAtdDLcY-Kk_p3DYaeaGfwXxe_V7xF7GJyA9fBu4oCrK-UkPx4AKyxCogMQNGImv1lQgASIBA__V_9s%3D;passport_auth_status=36df348a6e804081debeb26f47e31b76%2C;passport_csrf_token_default=6c78dd2d1a2af16453b84d6e2d0da8e5;publish_badge_show_info=%220%2C0%2C0%2C1702634967469%22;pwa2=%220%7C0%7C3%7C0%22;sessionid=74db563a0698d6f35887155d80c67ed3;sid_tt=74db563a0698d6f35887155d80c67ed3;ssid_ucp_v1=1.0.0-KDNiNTY5NDJjMTUzN2U5Mjk0NjJkNWNjMjViMGM1OWUwZmIyMGZkNzAKGQjQpuGElwMQ2MvwqwYY7zEgDDgGQPQHSAQaAmxxIiA3NGRiNTYzYTA2OThkNmYzNTg4NzE1NWQ4MGM2N2VkMw;store-region=cn-sd;strategyABtestKey=%221702980760.813%22;toutiao_sso_user_ss=4b535bdf596dede7350096bd09669467;uid_tt=97dfee030b79de830b862f8761f56023;uid_tt_ss=97dfee030b79de830b862f8761f56023; msToken=SjaDXcNS4yfsjsPGJVa0CyUlpiRgbqQpui25TZjrEzJ58ldioS2RJVVweMAF9ZDjD68b0-qw1g-QOO94q8-vYMKC9dQwiJ4H2vR-zvFAtSl04JIXwtg=";
    //     ParserService parserService = new ParserService();
    //     parserService.parseVideo(keyword, sortType, publishTime, cookies);
    //
    //
    //     // Timestamp timestamp = new Timestamp(1693562694 * 1000L);
    //     // System.out.println(timestamp.toLocalDateTime());
    //     // LocalDateTime localDateTime1 = LocalDateTime.now();
    //     // long timestamp1 = Timestamp.valueOf(localDateTime1).getTime() / 1000;
    //     // Timestamp timestamp2 = new Timestamp(timestamp1 * 1000L);
    //     // System.out.println(timestamp2.toLocalDateTime());
    // }
}
