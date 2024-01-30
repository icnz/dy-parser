package cn.cariton.apps.douyin.controller;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.service.ParserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author icnz
 * @date 2023-12-21 15:18
 */
@RestController
@RequestMapping("/parser")
@Validated
public class ParserController {

    @Resource
    private ParserService parserService;

    @GetMapping("video/search")
    public Result search(@RequestParam(value = "keyword") String keyword,
                           @RequestParam(value = "cookies") String cookies) {
        return parserService.parseVideo(keyword, 2, 7, cookies);
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
