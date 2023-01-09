package com.example2.demo2.service;

import com.example2.demo2.dto.TodoDTO;
import com.example2.demo2.model.TodoItem;
import com.example2.demo2.queue.QueueConfig;
import com.example2.demo2.queue.Reciever;
import com.example2.demo2.repo.TodoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class TodoService implements CommandLineRunner {

    private final TodoRepo todoRepo;
    private final RabbitTemplate rabbitTemplate;
    private final Reciever receiver;

    private ModelMapper mapper;

    public TodoService(Reciever receiver, RabbitTemplate rabbitTemplate, TodoRepo todoRepo,ModelMapper mapper) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.todoRepo = todoRepo;
        this.mapper=mapper;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(QueueConfig.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    public List<TodoDTO> findAll() {
        List<TodoItem> todoItems = todoRepo.findAll();
        return Arrays.asList(mapper.map(todoItems,TodoDTO[].class));
        /*
        return todoItems.stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());

         */
    }

    public TodoDTO save(TodoDTO todoDto) throws Exception {
        run();

        TodoItem todoItem=mapper.map(todoDto, TodoItem.class);
        TodoItem savedTodoItem = todoRepo.save(todoItem);
        /*
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle(todoDto.getTitle());
        todoItem.setDone(todoDto.isDone());
        todoItem.setUserid(todoDto.getUserid());
        TodoItem savedTodoItem = todoRepo.save(todoItem);
         */
        return new TodoDTO(savedTodoItem);
    }

    public TodoDTO update(TodoDTO todoDto) {
        TodoItem todoItem = todoRepo.findById(todoDto.getId()).orElse(null);
        if (todoItem == null) {
            return null;
        }
        todoItem=mapper.map(todoDto, TodoItem.class);
        TodoItem savedTodoItem = todoRepo.save(todoItem);
        /*
        todoItem.setTitle(todoDto.getTitle());
        todoItem.setDone(todoDto.isDone());
        todoItem.setUserid(todoDto.getUserid());
        TodoItem savedTodoItem = todoRepo.save(todoItem);
         */
        return new TodoDTO(savedTodoItem);
    }

    public void deleteById(Long id) {
        todoRepo.deleteById(id);
    }

    public TodoDTO addTodoUser(Long id, Integer userid, TodoDTO todoDto) {
        TodoItem todoItem = todoRepo.findById(id).orElse(null);
        if (todoItem == null) {
            return null;
        }
        todoItem.setUserid(userid);
        TodoItem savedTodoItem = todoRepo.save(todoItem);
        return new TodoDTO(savedTodoItem);
    }

    public List<TodoDTO> findByuserid(Integer userid) {
        List<TodoItem> todoItems = todoRepo.findByuserid(userid);
        return Arrays.asList(mapper.map(todoItems,TodoDTO[].class));
        /*
        return todoItems.stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());

         */
    }
}