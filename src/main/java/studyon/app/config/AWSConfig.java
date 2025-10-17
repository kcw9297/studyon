package studyon.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AWSConfig {

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 지역 : 서울
                .credentialsProvider(DefaultCredentialsProvider.create()) // 로컬의 .aws/credentials 파일 혹은 EC2 내 ROLE 사용
                .build();
    }

}