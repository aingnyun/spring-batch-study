//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.data.mongodb.core.MongoOperations;
//import spring.io.writer.domain.Customer;
//import spring.io.writer.domain.CustomerMG;
//
//import javax.persistence.EntityManagerFactory;
//
//@EnableBatchProcessing
//@Configuration
//public class MongoFormatJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public MongoFormatJob(JobBuilderFactory jobBuilderFactory,
//                        StepBuilderFactory stepBuilderFactory) {
//
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<CustomerMG> customerFileReader(
//            @Value("#{jobParameters['customerFile']}") Resource inputFile) {
//
//        return new FlatFileItemReaderBuilder<CustomerMG>()
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
//                .targetType(CustomerMG.class)
//                .build();
//    }
//
//
//    @Bean
//    public MongoItemWriter<CustomerMG> mongoItemWriter(MongoOperations mongoTemplate) {
//        return new MongoItemWriterBuilder<CustomerMG>()
//                .collection("customers")
//                .template(mongoTemplate)
//                .build();
//    }
//
//
//    @Bean
//    public Step mongoFormatStep() throws Exception {
//        return this.stepBuilderFactory.get("mongoFormatStep")
//                .<CustomerMG, CustomerMG>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(mongoItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job mongoFormatJob() throws Exception {
//        return this.jobBuilderFactory.get("mongoFormatJob")
//                .start(mongoFormatStep())
//                .build();
//    }
//}
