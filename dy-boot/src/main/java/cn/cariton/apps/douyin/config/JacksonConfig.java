package cn.cariton.apps.douyin.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return builder -> {
            builder
                    .serializationInclusion(JsonInclude.Include.NON_NULL)
                    .failOnEmptyBeans(false)
                    .failOnUnknownProperties(false)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .dateFormat(new SimpleDateFormat(DEFAULT_DATETIME_PATTERN))
                    .timeZone(TimeZone.getTimeZone("GMT+8"));
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
            builder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        };
    }
}
