package spring.io.item.processer.domain;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ScriptItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;


@EnableBatchProcessing
@SpringBootApplication
public class ClassifierCompositeJob {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(
            @Value("#{jobParameters['customerFile']}") Resource inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .delimited()
                .names(new String[] {"firstName",
                        "middleInitial",
                        "lastName",
                        "address",
                        "city",
                        "state",
                        "zip"})
                .targetType(Customer.class)
                .resource(inputFile)
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public ItemProcessorAdapter<Customer, Customer> upperCaseItemProcessor(UpperCaseNameService service) {
        ItemProcessorAdapter<Customer, Customer> adapter = new ItemProcessorAdapter<>();

        adapter.setTargetObject(service);
        adapter.setTargetMethod("upperCase");

        return adapter;
    }

    @Bean
    @StepScope
    public ScriptItemProcessor<Customer, Customer> lowerCaseItemProcessor(
            @Value("#{jobParameters['script']}") Resource script) {

        ScriptItemProcessor<Customer, Customer> itemProcessor =
                new ScriptItemProcessor<>();

        itemProcessor.setScript(script);

        return itemProcessor;
    }

    @Bean
    public Classifier classifier() {
        return new ZipCodeClassifier(upperCaseItemProcessor(null),
                lowerCaseItemProcessor(null));
    }

    @Bean
    public ClassifierCompositeItemProcessor<Customer, Customer> itemProcessor() {
        ClassifierCompositeItemProcessor<Customer, Customer> itemProcessor =
                new ClassifierCompositeItemProcessor<>();

        itemProcessor.setClassifier(classifier());

        return itemProcessor;
    }

    @Bean
    public Step copyFileStep() {

        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(5)
                .reader(customerItemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {

        return this.jobBuilderFactory.get("job")
                .start(copyFileStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClassifierCompositeJob.class, "customerFile=/input/customer.csv", "script=/lowerCase.js");
    }
}
