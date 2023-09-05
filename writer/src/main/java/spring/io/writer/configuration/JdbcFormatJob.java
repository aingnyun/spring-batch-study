//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import spring.io.writer.domain.Customer;
//import spring.io.writer.domain.CustomerItemPreparedStatementSetter;
//
//import javax.sql.DataSource;
//
//
//@EnableBatchProcessing
//@Configuration
//public class JdbcFormatJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public JdbcFormatJob(JobBuilderFactory jobBuilderFactory,
//                      StepBuilderFactory stepBuilderFactory) {
//
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<Customer> customerFileReader(
//            @Value("#{jobParameters['customerFile']}") Resource inputFile) {
//
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("customerItemReader")
//                .resource(inputFile)
//                .delimited()
//                .names(new String[] {"firstName", "middleInitial","lastName", "address", "city", "state", "zip"})
//                .targetType(Customer.class)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JdbcBatchItemWriter<Customer> jdbcBatchItemWriter(DataSource dataSource) throws Exception {
//
//        return new JdbcBatchItemWriterBuilder<Customer>()
//                .dataSource(dataSource)
//                .sql("INSERT INTO CUSTOMER (firstName, " +
//                        "middleInitial, " +
//                        "lastName, " +
//                        "address, " +
//                        "city," +
//                        "state, " +
//                        "zip) VALUES (:firstName, :middleInitial, :lastName, :address, :city, :state, :zip)")
//                .beanMapped()
////                .itemPreparedStatementSetter(new CustomerItemPreparedStatementSetter())
//                .build();
//    }
//
//    @Bean
//    public Step xmlFormatStep() throws Exception {
//        return this.stepBuilderFactory.get("xmlFormatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(jdbcBatchItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job xmlFormatJob() throws Exception {
//        return this.jobBuilderFactory.get("xmlFormatJob1")
//                .start(xmlFormatStep())
//                .build();
//    }
//
//}
