package com.example2.demo2.config;
import com.example2.demo2.model.TodoItem;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TodoItemProcessor todoItemProcessor;

    @Autowired
    private TodoItemWriter todoItemWriter;

    @Bean
    public Job readTodoItemsJob() {
        return jobBuilderFactory.get("readTodoItemsJob")
                .start(readTodoItemsStep())
                .build();
    }

    @Bean
    public Step readTodoItemsStep() {
        return stepBuilderFactory.get("readTodoItemsStep")
                .<TodoItem, TodoItem> chunk(10)
                .reader(todoItemReader())
                .processor(todoItemProcessor)
                .writer(todoItemWriter)
                .build();
    }

    @Bean
    public ItemReader<TodoItem> todoItemReader() {
        FlatFileItemReader<TodoItem> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("/Users/slakshmanrao/Downloads/demo2/src/main/resources/todo-items.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(todoItemLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<TodoItem> todoItemLineMapper() {
        DefaultLineMapper<TodoItem> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"title", "done", "userid"});
        BeanWrapperFieldSetMapper<TodoItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TodoItem.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

//    @Bean
//    public TodoItemProcessor todoItemProcessor() {
//        return new TodoItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<TodoItem> todoItemWriter() {
//        return new TodoItemWriter();
//    }
}




