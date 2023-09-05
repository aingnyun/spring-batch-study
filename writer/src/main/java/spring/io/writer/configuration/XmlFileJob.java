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
//import org.springframework.batch.item.xml.StaxEventItemWriter;
//import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.oxm.xstream.XStreamMarshaller;
//import spring.io.writer.domain.Customer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableBatchProcessing
//@Configuration
//public class XmlFileJob {
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public XmlFileJob(JobBuilderFactory jobBuilderFactory,
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
//                .name("customerItemWriter")
//                .resource(inputFile)
//                .delimited()
//                .names(new String[] {"firstName", "middleInitial","lastName", "address", "city", "state", "zip"})
//                .targetType(Customer.class)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public StaxEventItemWriter<Customer> xmlCustomerItemWriter(
//            @Value("#{jobParameters['outputFile']}") Resource outputFile) {
//        Map<String, Class> alias = new HashMap<>();
//        alias.put("customer", Customer.class);
//
//        XStreamMarshaller marshaller = new XStreamMarshaller();
//        marshaller.setAliases(alias);
//        marshaller.afterPropertiesSet();
//
//        return new StaxEventItemWriterBuilder<Customer>()
//                .name("customerItemWriter")
//                .resource(outputFile)
//                .marshaller(marshaller)
//                .rootTagName("customers")
//                .build();
//
//    }
//
//    @Bean
//    public Step xmlFormatStep() {
//        return this.stepBuilderFactory.get("xmlFormatStep")
//                .<Customer, Customer>chunk(10)
//                .reader(customerFileReader(null))
//                .writer(xmlCustomerItemWriter(null))
//                .build();
//    }
//
//    @Bean
//    public Job xmlFormatJob() {
//        return this.jobBuilderFactory.get("xmlFormatJob")
//                .start(xmlFormatStep())
//                .build();
//    }
//
//}
