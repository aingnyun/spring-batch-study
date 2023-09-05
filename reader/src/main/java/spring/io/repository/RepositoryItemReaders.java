package spring.io.repository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@EnableBatchProcessing
@SpringBootApplication
public class RepositoryItemReaders {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public ItemReaderAdapter<Customer> customerItemReader(CustomerService customerService) {
//        ItemReaderAdapter<Customer> adapter = new ItemReaderAdapter<>();
//        adapter.setTargetObject(customerService);
//        adapter.setTargetMethod("getCustomer");
//
//        return adapter;
//    }

    @Bean
    public CustomerService customerItemReaders() {
        CustomerService customerItemReader = new CustomerService();

        customerItemReader.setName("customerItemReader");

        return customerItemReader;
    }

//    @Bean
//    @StepScope
//    public RepositoryItemReader<Customer> customerItemReader(CustomerRepository repository,
//                                                             @Value("#{jobParameters['city']}") String city) {
//        return new RepositoryItemReaderBuilder<Customer>()
//                .name("customerItemReader")
//                .arguments(Collections.singletonList(city))
//                .methodName("findByCity")
//                .repository(repository)
//                .sorts(Collections.singletonMap("lastName", Sort.Direction.ASC))
//                .build();
//    }


    @Bean
    public ItemWriter itemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public EmptyInputStepFailer emptyFileFailer() {
        return new EmptyInputStepFailer();
    }

    @Bean
    public Step copyFileStep() {
        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(10)
                .reader(customerItemReaders())
//                .writer(outputWriter(null))
                .listener(emptyFileFailer())
                .build();
    }

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job20")
				.start(copyFileStep())
				.build();
	}


    public static void main(String[] args) {
        SpringApplication.run(RepositoryItemReaders.class);
    }

}
