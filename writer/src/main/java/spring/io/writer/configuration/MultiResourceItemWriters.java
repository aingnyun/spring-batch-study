//package spring.io.writer.configuration;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.FlatFileItemWriter;
//import org.springframework.batch.item.file.MultiResourceItemWriter;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
//import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
//import org.springframework.batch.item.file.transform.FormatterLineAggregator;
//import org.springframework.batch.item.support.CompositeItemWriter;
//import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
//import org.springframework.batch.item.xml.StaxEventItemWriter;
//import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.Resource;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.oxm.xstream.XStreamMarshaller;
//import spring.io.writer.batch.CustomerRecordCountFooterCallback;
//import spring.io.writer.domain.Customer;
//import spring.io.writer.domain.CustomerXmlHeaderCallback;
//
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableBatchProcessing
//@Configuration
//public class MultiResourceItemWriters {
//
//    private JobBuilderFactory jobBuilderFactory;
//
//    private StepBuilderFactory stepBuilderFactory;
//
//    public MultiResourceItemWriters(JobBuilderFactory jobBuilderFactory,
//                                    StepBuilderFactory stepBuilderFactory) {
//
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<Customer> compositeWriterItemReader(
//            @Value("#{jobParameters['customerFile']}")Resource inputFile) {
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
////    @Bean
////    public MultiResourceItemWriter<Customer> multiCustomerFileWriter() throws Exception {
////
////        return new MultiResourceItemWriterBuilder<Customer>()
////                .name("multiCustomerFileWriter")
////                .delegate(delegateItemWriter(new CustomerRecordCountFooterCallback()))
////                .itemCountLimitPerResource(25)
////                .resource(new FileSystemResource("/Users/hymin/Source/Java/writer/src/main/resources/output/customer"))
////                .build();
////    }
//
//    @Bean
//    @StepScope
//    public StaxEventItemWriter<Customer> xmlDelegateItemWriter(@Value("#{jobParameters['outputFile']}") Resource outputFile) throws Exception {
//        Map<String, Class> aliases = new HashMap<>();
//        aliases.put("customer", Customer.class);
//
//        XStreamMarshaller marshaller = new XStreamMarshaller();
//        marshaller.setAliases(aliases);
//        marshaller.afterPropertiesSet();
//
//        return new StaxEventItemWriterBuilder<Customer>()
//                .name("customerItemWriter")
//                .resource(outputFile)
//                .marshaller(marshaller)
//                .rootTagName("customer")
//                .build();
//    }
//
//
//    @Bean
//    public JdbcBatchItemWriter<Customer> jdbcDelgateItemWriter(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Customer>()
//                .namedParametersJdbcTemplate(new NamedParameterJdbcTemplate(dataSource))
//                .sql("INSERT INTO CUSTOMER (firstName, " +
//                        "middleInitial, " +
//                        "lastName, " +
//                        "address, " +
//                        "city," +
//                        "state, " +
//                        "zip) " +
//                        "VALUES (:firstName, :middleInitial, :lastName, :address, :city, :state, :zip)")
//                .beanMapped()
//                .build();
//    }
//
//    @Bean
//    public CompositeItemWriter<Customer> compositeItemWriter() throws Exception {
//        return new CompositeItemWriterBuilder<Customer>()
//                .delegates(Arrays.asList(xmlDelegateItemWriter(null), jdbcDelgateItemWriter(null)))
//                .build();
//    }
//
//
//    @Bean
//    public Step compositeWriterStep() throws Exception {
//        return this.stepBuilderFactory.get("compositeWriterStep")
//                .<Customer, Customer>chunk(10)
//                .reader(compositeWriterItemReader(null))
//                .writer(compositeItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Job compositeWriterJob() throws Exception {
//        return this.jobBuilderFactory.get("compositeWriterJob")
//                .start(compositeWriterStep())
//                .build();
//    }
//}
