package com.example2.demo2.controller;



import com.example2.demo2.dto.TodoDTO;
import com.example2.demo2.model.TodoItem;
import com.example2.demo2.queue.QueueConfig;
import com.example2.demo2.repo.TodoRepo;
import com.example2.demo2.service.TodoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import com.example2.demo2.queue.Reciever;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/todo")
public class TodoController  {

    private final TodoService todoService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job readTodoItemsJob;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoDTO> findAll() {
        return todoService.findAll();
    }

    @PostMapping
    public TodoDTO save(@RequestBody TodoDTO todoDto) throws Exception {
        return todoService.save(todoDto);
    }

    @PutMapping
    public TodoDTO update(@RequestBody TodoDTO todoDto) {
        return todoService.update(todoDto);
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id) {
        todoService.deleteById(id);
    }

    @PutMapping(value = "/{id}/{userid}")
    public TodoDTO addTodoToUser(@PathVariable Long id, @PathVariable Integer userid, @RequestBody TodoDTO todoDto) {
        return todoService.addTodoUser(id, userid, todoDto);
    }

    @GetMapping(value = "/{userid}")
    public List<TodoDTO> findAllForUser(@PathVariable Integer userid) {
        return todoService.findByuserid(userid);
    }

    @PostMapping("/batch")
    public void importCsvToDBJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
          jobLauncher.run(readTodoItemsJob, jobParameters);
    }
}