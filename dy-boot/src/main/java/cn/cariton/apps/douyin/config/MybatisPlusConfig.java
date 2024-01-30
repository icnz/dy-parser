package cn.cariton.apps.douyin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author icnz
 * @date 2023-04-11 12:08
 */

@Configuration
public class MybatisPlusConfig {
    private final static Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        logger.info("MybatisPlusInterceptor Configuration init.");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        logger.info("MapperScannerConfigurer Configuration init.");
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("cn.cariton.apps.douyin.mapper");
        return scannerConfigurer;
    }
}
