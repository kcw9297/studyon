package studyon.app.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import studyon.app.layer.domain.member.MemberDTO;

import javax.sql.DataSource;
import java.io.IOException;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-13) : kcw97 최초 작성
 */

/**
 * 데이터베이스, JPA 관련 설정
 * @version 1.0
 * @author kcw97
 */
@Slf4j
@MapperScan(basePackages = "studyon.app.layer.domain.*.mapper")
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class DatabaseConfig {

    // 사용 DBCP
    private final DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(getPathMatchingResources("classpath*:mybatis/**/*.xml"));

        // Configuration 설정
        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true); // Underscore -> CamelCase 일부 매핑
        registerAliases(configuration);  // 커스텀 alias 등록
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    private Resource[] getPathMatchingResources(String pattern) throws IOException {
        return new PathMatchingResourcePatternResolver().getResources(pattern);
    }

    private void registerAliases(org.apache.ibatis.session.Configuration configuration) {
        TypeAliasRegistry registry = configuration.getTypeAliasRegistry();

        // 각 도메인의 DTO 패키지에서 Info 내부클래스 등록
        registry.registerAlias("memberInfo", MemberDTO.Read.class);
    }

}