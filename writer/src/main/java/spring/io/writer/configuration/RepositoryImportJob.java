//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
//import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.jsr.item.ItemWriterAdapter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.repository.CrudRepository;
//import spring.io.writer.domain.Customer;
//import spring.io.writer.domain.CustomerMG;
//import spring.io.writer.domain.CustomerService;
//
//@EnableBatchProcessing
//@Configuration
//@EnableJpaRepositories(basePackageClasses = Customer.class)
//public class RepositoryImportJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public RepositoryImportJob(JobBuilderFactory jobBuilderFactory,
//                          StepBuilderFactory stepBuilderFactory) {
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
//
//
//    @Bean
//    public RepositoryItemWriter<Customer> repositoryItemWriter(CrudRepository repository) {
//        return new RepositoryItemWriterBuilder<Customer>()
//                .repository(repository)
//                .methodName("save")
//                .build();
//    }
//
//
//    @Bean
//    public Step repositoryFormatStep() throws Exception {
//        return this.stepBuilderFactory.get("repositoryFormatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(repositoryItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job repositoryFormatJob() throws Exception {
//        return this.jobBuilderFactory.get("repositoryFormatJob")
//                .start(repositoryFormatStep())
//                .build();
//    }
//
//}
