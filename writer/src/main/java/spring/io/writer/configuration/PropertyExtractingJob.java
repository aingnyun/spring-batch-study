//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.adapter.PropertyExtractingDelegatingItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import spring.io.writer.domain.Customer;
//import spring.io.writer.domain.CustomerService;
//
//public class PropertyExtractingJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public PropertyExtractingJob(JobBuilderFactory jobBuilderFactory,
//                                 StepBuilderFactory stepBuilderFactory) {
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
//    public PropertyExtractingDelegatingItemWriter<Customer> itemWriter(CustomerService customerService) {
//        PropertyExtractingDelegatingItemWriter<Customer> itemWriter =
//                new PropertyExtractingDelegatingItemWriter<>();
//
//        itemWriter.setTargetObject(customerService);
//        itemWriter.setTargetMethod("logCustomerAddress");
//        itemWriter.setFieldsUsedAsTargetMethodArguments(
//                new String[] {"address", "city", "state", "zip"});
//
//        return itemWriter;
//    }
//
//    @Bean
//    public Step formatStep() throws Exception {
//        return this.stepBuilderFactory.get("formatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(itemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job propertiesFormatJob() throws Exception {
//        return this.jobBuilderFactory.get("propertiesFormatJob")
//                .start(formatStep())
//                .build();
//    }
//}
