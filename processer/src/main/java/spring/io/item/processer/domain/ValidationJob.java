//package spring.io.item.processer.domain;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.adapter.ItemProcessorAdapter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//
//import org.springframework.batch.item.support.CompositeItemProcessor;
//import org.springframework.batch.item.support.ScriptItemProcessor;
//import org.springframework.batch.item.validator.ValidatingItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//
//import java.util.Arrays;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class ValidationJob {
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<Customer> customerItemReader (
//            @Value("#{jobParameters['customerFile']}")Resource inputFile) {
//
//        return new FlatFileItemReaderBuilder<Customer>()
//                .name("customerItemReader")
//                .delimited()
//                .names(new String[] {"firstName", "middleInitial", "lastName", "address", "city", "state", "zip"})
//                .targetType(Customer.class)
//                .resource(inputFile)
//                .build();
//    }
//
//    @Bean
//    public ItemWriter<Customer> itemWriter() {
//        return (items) -> items.forEach(System.out::println);
//    }
//
////    @Bean
////    public UniqueLastNameValidator validator() {
////        UniqueLastNameValidator uniqueLastNameValidator = new UniqueLastNameValidator();
////        uniqueLastNameValidator.setName("validator");
////
////        return uniqueLastNameValidator;
////    }
////
////    @Bean
////    public ValidatingItemProcessor<Customer> customerValidatingItemProcessor() {
////        return new ValidatingItemProcessor<>(validator());
////    }
//
////    @Bean
////    public BeanValidatingItemProcessor<Customer> customerBeanValidatingItemProcessor() {
////        return new BeanValidatingItemProcessor<>();
////    }
//
//    @Bean
//    public ItemProcessorAdapter<Customer, Customer> upperCaseItemProcessor(UpperCaseNameService service) {
//        ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();
//
//        adapter.setTargetObject(service);
//        adapter.setTargetMethod("upperCase");
//
//        return adapter;
//    }
//
//    @Bean
//    @StepScope
//    public ScriptItemProcessor<Customer, Customer> lowerCaseItemProcesssor(
//            @Value("#{jobParameters['script']}")Resource script) {
//        ScriptItemProcessor<Customer, Customer> itemProcessor = new ScriptItemProcessor<>();
//
//        itemProcessor.setScript(script);
//
//        return itemProcessor;
//    }
//
//    @Bean
//    public CompositeItemProcessor<Customer, Customer> itemProcessor() {
//        CompositeItemProcessor<Customer, Customer> itemProcessor = new CompositeItemProcessor<>();
//
//        itemProcessor.setDelegates(Arrays.asList(customerValidatingItemProcessor(),
//                upperCaseItemProcessor(null),
//                lowerCaseItemProcesssor(null)));
//
//        return itemProcessor;
//    }
//
//    @Bean
//    public UniqueLastNameValidator validator() {
//        UniqueLastNameValidator uniqueLastNameValidator = new UniqueLastNameValidator();
//        uniqueLastNameValidator.setName("validator");
//
//        return uniqueLastNameValidator;
//    }
//
//    @Bean
//    public ValidatingItemProcessor<Customer> customerValidatingItemProcessor() {
//        ValidatingItemProcessor<Customer> itemProcessor = new ValidatingItemProcessor<>(validator());
//
//        itemProcessor.setFilter(true);
//
//        return itemProcessor;
//    }
//
//    @Bean
//    public Step copyFileStep() {
//        return this.stepBuilderFactory.get("copyFileStep")
//                .<Customer, Customer> chunk(5)
//                .reader(customerItemReader(null))
//                .processor(itemProcessor())
//                .writer(itemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job job() throws Exception {
//        return this.jobBuilderFactory.get("job3")
//                .start(copyFileStep())
//                .build();
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ValidationJob.class, "customerFile=/input/customer.csv", "script=/lowerCase.js");
//    }
//
//}
