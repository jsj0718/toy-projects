package com.elevenhelevenm.practice.board.global.config.batch.config.db.jdbc.reader;

import com.elevenhelevenm.practice.board.domain.sales.SalesSum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.time.LocalDate;

import static com.elevenhelevenm.practice.board.global.config.batch.config.db.jdbc.reader.BatchOnlyJdbcReaderTestConfig.FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * @StepScope이 필요없는 단위 테스트
 */
class BatchOnlyJdbcReaderTestConfigTest {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    ConfigurableApplicationContext context;
    LocalDate orderDate;
    BatchOnlyJdbcReaderTestConfig job;

    @BeforeEach
    void setUp() {
        //DataSource, JdbcTemplate, Reader 등 실행 가능한 Context 생성
        context = new AnnotationConfigApplicationContext(TestDataSourceConfiguration.class); // (1)
        //TestDataSourceConfiguration에서 생성된 DataSource를 가져옴
        dataSource = (DataSource) context.getBean("dataSource"); // (2)
        //JdbcTemplate의 경우 DataSource가 있어야 하고, 해당 DB에 쿼리를 날림
        jdbcTemplate = new JdbcTemplate(dataSource); // (3)
        orderDate = LocalDate.of(2019, 10, 6);
        //테스트 대상인 COnfig에 생성한 DataSource 생성자 주입 (생성자 주입으로 해야하는 이유)
        job = new BatchOnlyJdbcReaderTestConfig(dataSource); // (4)
        //Reader에 PageSzie, FetchSize를 결정하는 chunkSize 설정 -> JdbcPagingItemReaderBuilder의 경우 pageSize를 지정하지 않으면 오류 발생
        job.setChunkSize(10); // (5)
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    @DisplayName("기간내_Sales가_집계되어_SalesSum이된다 - @StepScope이 없는 경우")
    void batchUnitTestWithoutStepScope() throws Exception {
        // given
        long amount1 = 1000;
        long amount2 = 100;
        long amount3 = 10;
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount1, "1"); // (1) Insert Query 진행 (테스트 환경 구축)
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount2, "2");
        jdbcTemplate.update("insert into sales (order_date, amount, order_no) values (?, ?, ?)", orderDate, amount3, "3");

        JdbcPagingItemReader<SalesSum> reader = job.batchOnlyJdbcReaderTestJobReader(orderDate.format(FORMATTER)); // (2) setUp에서 만든 Job에서 Reader 가져옴
        reader.afterPropertiesSet(); // (3) Reader에서 쿼리 생성 (실행 되지 않을 시 Reader의 쿼리 결과는 Null)

        // when & then
        assertThat(reader.read().getAmountSum()).isEqualTo(amount1 + amount2 + amount3); // (4) group By로 얻은 결과 값이 1개의 Row이면서, 총 합계와 동일한지 검증
        assertThat(reader.read()).isNull(); //(5) 다음에 읽을 데이터가 Null인지 검증
    }

    //테스트 환경 Config 클래스와 같은 역할
    @Configuration
    public static class TestDataSourceConfiguration {

        // (1) Sales 테이블 생성 쿼리
        private static final String CREATE_SQL =
                "create table IF NOT EXISTS `sales` (id bigint not null auto_increment, amount bigint not null, order_date date, order_no varchar(255), primary key (id))";

        // (2) 테스트용 DB 실행 (H2를 이용한 테스트 환경과 유사 -> 인메모리 사용)
        // @SpringBootTest가 이러한 설정을 모두 해주지만, 모든 설정을 해야하기에 수행하는데 오래 걸리므로 단위 테스트에서는 적절하지 않다.
        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseFactory databaseFactory = new EmbeddedDatabaseFactory();
            databaseFactory.setDatabaseType(H2);
            return databaseFactory.getDatabase();
        }

        // (3) DB 초기 작업을 어떻게 진행할 지 결정
        @Bean
        public DataSourceInitializer initializer(DataSource dataSource) {
            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);

            Resource create = new ByteArrayResource(CREATE_SQL.getBytes());
            dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(create));

            return dataSourceInitializer;
        }
    }
}