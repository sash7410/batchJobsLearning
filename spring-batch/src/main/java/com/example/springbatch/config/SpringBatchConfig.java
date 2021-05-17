package com.example.springbatch.config;

import com.example.springbatch.models.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

        @Bean
        public Job job(JobBuilderFactory jobBuilderFactory,
                        StepBuilderFactory stepBuilderFactory,
                       ItemReader<User> itemReader,
                       ItemProcessor<User,User> itemProcessor,
                       ItemWriter<User> itemWriter
                        ){
//try making step bean instead
            Step step =stepBuilderFactory.get("ETL-file-load")
                    .<User,User>chunk(100)
                    .reader(itemReader)
                    .processor(itemProcessor)
                    .writer(itemWriter)
                    .build();
            //name of job etlLoad
            return jobBuilderFactory.get("ETL-Load")
                    .incrementer(new RunIdIncrementer())//wtf?
                    .start(step)
                    .build();
        }
    @Bean//check Resource
    public FlatFileItemReader<User> itemReader(){
            FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<>();
        System.out.println("config 1");
            flatFileItemReader.setResource(new FileSystemResource
                    ("E:/coding1/java codes prac/batchJobsLearning/spring-batch/src/main/resources/users.csv"));
        System.out.println("config 2"+flatFileItemReader);
            flatFileItemReader.setName("CSV-Reader");
        System.out.println("config 3");
            flatFileItemReader.setLinesToSkip(1);
            flatFileItemReader.setLineMapper(lineMapper());
        System.out.println("config 4");
            return flatFileItemReader;
    }
    @Bean
    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "dept", "salary");

        BeanWrapperFieldSetMapper<User> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
        }
}
