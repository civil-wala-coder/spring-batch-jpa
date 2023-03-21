package mysql.batchjpa.config;

import mysql.batchjpa.model.MultiUsers;
import mysql.batchjpa.repository.MulitUsersRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig  {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MulitUsersRepo repo;


    @Bean
    public FlatFileItemReader<MultiUsers> reader(){
        System.out.println("reader:::::");
        FlatFileItemReader<MultiUsers> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/records.csv"));

        reader.setLineMapper(getLineMapper());
        reader.setLinesToSkip(1);
        return reader;
    }

    private LineMapper<MultiUsers> getLineMapper() {
        System.out.println("getLineMapper:::::");
        DefaultLineMapper<MultiUsers> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"id","firstName","lastName","email","gender"});
        lineTokenizer.setIncludedFields(new int[]{0,1,2,3,4});

        BeanWrapperFieldSetMapper<MultiUsers> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(MultiUsers.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    @Bean
    public ItemProcessor<MultiUsers,MultiUsers> processor(){
        System.out.println("processor::::::::");
        return new UserItemProcessor();
    }



    @Bean
    public RepositoryItemWriter<MultiUsers> writer(){
        System.out.println("writer:::::::::;");
        RepositoryItemWriter<MultiUsers> writer = new RepositoryItemWriter<>();
        writer.setRepository(this.repo);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public Job importUserJob(){
        System.out.println("Job::::::");
        return this.jobBuilderFactory.get("importCustomers")
                .flow(step1())
                .end().build();
    }


    @Bean
    public Step step1(){
        System.out.println("step1::::::::;");
        return this.stepBuilderFactory.get("get1")
                .<MultiUsers,MultiUsers>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }



}
