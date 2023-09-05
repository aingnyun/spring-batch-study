//package spring.io.ItemReader;
//
//import org.hibernate.SessionFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.HibernateCursorItemReader;
//import org.springframework.batch.item.database.HibernatePagingItemReader;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.database.StoredProcedureItemReader;
//import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
//import org.springframework.batch.item.database.builder.HibernatePagingItemReaderBuilder;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.batch.item.database.builder.StoredProcedureItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
//import org.springframework.jdbc.core.SqlParameter;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.sql.Types;
//import java.util.Collections;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class JdbcCursorJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//
//	@Bean
//	@StepScope
//	public StoredProcedureItemReader<Customer> customerItemReader(
//			DataSource dataSource,
//			@Value("#{jobParameters['city']}") String city) {
//
////		HibernatePagingItemReaderBuilder
//		return new StoredProcedureItemReaderBuilder<Customer>()
//				.name("customerItemReader")
//				.dataSource(dataSource)
//				.procedureName("customer_list")
//				.parameters(new SqlParameter[]{new SqlParameter("cityOption", Types.VARCHAR)})
//				.preparedStatementSetter(new ArgumentPreparedStatementSetter(new Object[] {city}))
//				.rowMapper(new CustomerRowMapper())
//				.build();
//	}
//
//
////	@Bean
////	@StepScope
////	public HibernatePagingItemReader<Customer> customerItemReader(
////			EntityManagerFactory entityManagerFactory,
////			@Value("#{jobParameters['city']}") String city) {
//////		HibernatePagingItemReaderBuilder
////		return new HibernatePagingItemReaderBuilder<Customer>()
////				.name("customerItemReader")
////				.sessionFactory(entityManagerFactory.unwrap(SessionFactory.class))
////				.queryString("from Customer where city = :city")
////				.parameterValues(Collections.singletonMap("city", city))
////				.pageSize(1)
////				.build();
////	}
//
////	@Bean
////	public JdbcCursorItemReader<Customer> customerItemReader(DataSource dataSource) {
////		return new JdbcCursorItemReaderBuilder<Customer>()
////				.name("customerItemReader")
////				.dataSource(dataSource)
////				.sql("select * from customer where city = ?")
////				.rowMapper(new CustomerRowMapper())
////				.preparedStatementSetter(citySetter(null))
////				.build();
////	}
//
////	@Bean
////	@StepScope
////	public JdbcPagingItemReader<Customer> customerItemReader(DataSource dataSource,
////															 PagingQueryProvider queryProvider,
////															 @Value("#{jobParameters['city']}") String city) {
////
////		Map<String, Object> parameterValues = new HashMap<>(1);
////		parameterValues.put("city", city);
////
////		return new JdbcPagingItemReaderBuilder<Customer>()
////				.name("customerItemReader")
////				.dataSource(dataSource)
////				.queryProvider(queryProvider)
////				.parameterValues(parameterValues)
////				.pageSize(10)
////				.rowMapper(new CustomerRowMapper())
////				.build();
////	}
////
////	@Bean
////	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(DataSource dataSource) {
////		SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
////
////		factoryBean.setDataSource(dataSource);
////		factoryBean.setSelectClause("select *");
////		factoryBean.setFromClause("from Customer");
////		factoryBean.setWhereClause("where city = :city");
////		factoryBean.setSortKey("lastName");
////
////		return factoryBean;
////	}
//
////	@Bean
////	@StepScope
////	public ArgumentPreparedStatementSetter citySetter(
////			@Value("#{jobParameters['city']}") String city) {
////
////		return new ArgumentPreparedStatementSetter(new Object[] {city});
////	}
//
//	@Bean
//	public ItemWriter<Customer> itemWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//
//	@Bean
//	public Step copyFileStep() {
//		return this.stepBuilderFactory.get("copyFileStep")
//				.<Customer, Customer>chunk(10)
//				.reader(customerItemReader(null, null))
//				.writer(itemWriter())
//				.build();
//	}
//
//	@Bean
//	public Job job() {
//		return this.jobBuilderFactory.get("job15")
//				.start(copyFileStep())
//				.build();
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(JdbcCursorJob.class, "city=Chicago");
//	}
//
//}