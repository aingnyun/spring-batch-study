//package spring.io.ItemReader;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.json.JacksonJsonObjectReader;
//import org.springframework.batch.item.json.JsonItemReader;
//import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//
//import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.List;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class CustomerJob {
//
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    @StepScope
//    public JsonItemReader<Customer> customerFileReader(
//            @Value("#{jobParameters['customerFile']}") Resource inputFile) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
//
//        JacksonJsonObjectReader<Customer> jsonObjectReader = new JacksonJsonObjectReader<>(Customer.class);
//        jsonObjectReader.setMapper(objectMapper);
//
//        return new JsonItemReaderBuilder<Customer>()
//                .name("customerFileReader")
//                .jsonObjectReader(jsonObjectReader)
//                .resource(inputFile)
//                .build();
//    }
//
//    @Bean
//    public ItemWriter itemWriter() {
//        return (items) -> items.forEach(System.out::println);
//    }
//
//    @Bean
//    public Step copyFileStep() {
//        return this.stepBuilderFactory.get("copyFileStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(itemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job job() {
//        return this.jobBuilderFactory.get("job8")
//                .start(copyFileStep())
//                .build();
//    }
//
//
//    public static void main(String[] args) {
//        List<String> realArgs = Collections.singletonList("customerFile=/input/customer.json");
//
//        SpringApplication.run(CustomerJob.class, realArgs.toArray(new String[1]));
//    }
//}
