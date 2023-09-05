//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import spring.io.writer.domain.Customer;
//
//import javax.persistence.EntityManagerFactory;
//
//@EnableBatchProcessing
//@Configuration
//public class JpaImportJob {
//
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public JpaImportJob(JobBuilderFactory jobBuilderFactory,
//                              StepBuilderFactory stepBuilderFactory) {
//
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<Customer> customerFileReader(
//            @Value("#{jobParameters['customerFile']}") Resource inputFile) {
//
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("customerFileReader")
//                .resource(inputFile)
//                .delimited()
//                .names(new String[] {"firstName",
//                        "middleInitial",
//                        "lastName",
//                        "address",
//                        "city",
//                        "state",
//                        "zip"})
//                .targetType(Customer.class)
//                .build();
//    }
//
//    @Bean
//    public JpaItemWriter<Customer> jpaItemWriter(EntityManagerFactory entityManager) {
//        JpaItemWriter<Customer> jpaItemWriter = new JpaItemWriter<>();
//        jpaItemWriter.setEntityManagerFactory(entityManager);
//
//        return jpaItemWriter;
//    }
//
//    @Bean
//    public Step jpaFormatStep() throws Exception {
//        return this.stepBuilderFactory.get("jpaFormatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(jpaItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job jpaFormatJob() throws Exception {
//        return this.jobBuilderFactory.get("jpaFormatJob")
//                .start(jpaFormatStep())
//                .build();
//    }
//}
