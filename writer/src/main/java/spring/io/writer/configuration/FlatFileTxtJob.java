//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import spring.io.writer.domain.Customer;
//
//@EnableBatchProcessing
//@Configuration
//public class FlatFileTxtJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public FlatFileTxtJob(JobBuilderFactory jobBuilderFactory,
//                                StepBuilderFactory stepBuilderFactory) {
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
//    @StepScope
//    public FlatFileItemWriter<Customer> customerItemWriter(
//            @Value("#{jobParameters['outputFile']}") Resource outputFile) {
//
//
//        return new FlatFileItemWriterBuilder<Customer>()
//                .name("customerItemWriter")
//                .resource(outputFile)
////                .formatted()
////                .format("%s %s lives at %s %s in %s, %s.")
//                .delimited()
//                .delimiter(";")
//                .names(new String[] {"firstName", "lastName", "address", "city", "state", "zip"})
//                .build();
//    }
//
//    @Bean
//    public Step formatStep() {
//        return this.stepBuilderFactory.get("formatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(customerItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job formatJob() {
//        return this.jobBuilderFactory.get("formatJob1")
//                .start(formatStep())
//                .build();
//    }
//}
