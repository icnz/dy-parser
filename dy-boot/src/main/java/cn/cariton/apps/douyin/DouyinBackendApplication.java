package cn.cariton.apps.douyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"cn.cariton.apps"})
public class DouyinBackendApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(DouyinBackendApplication.class, args);
    }

}
