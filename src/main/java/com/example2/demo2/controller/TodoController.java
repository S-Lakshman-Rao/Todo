package com.example2.demo2.controller;



import com.example2.demo2.model.TodoItem;
import com.example2.demo2.queue.QueueConfig;
import com.example2.demo2.repo.TodoRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import com.example2.demo2.queue.Reciever;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value="/todo")


public class TodoController implements CommandLineRunner {
    @Autowired
    private TodoRepo todorepo;


    private final RabbitTemplate rabbitTemplate;
    private final Reciever receiver;

    public TodoController(Reciever receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }
    @GetMapping
    public List<TodoItem> findAll()
    {
        return todorepo.findAll();

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(QueueConfig.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
    @PostMapping
    public TodoItem save(@RequestBody TodoItem todoItem) throws Exception {
        run();
        return todorepo.save(todoItem);

    }

    @PutMapping
    public TodoItem update(@RequestBody TodoItem todoItem)
    {
        return todorepo.save(todoItem);
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id)
    {
        todorepo.deleteById(id);
    }

    @PutMapping(value = "/{id}/{userid}")
    public TodoItem addTodoToUser(@PathVariable Long id, @PathVariable Integer userid, @RequestBody TodoItem todoItem){
        todoItem.setUserid(userid);
        return todorepo.save(todoItem);
    }

    @GetMapping(value = "/{userid}")
    public List<TodoItem> findAllForUser(@PathVariable Integer userid){
        return todorepo.findByuserid(userid);
    }

}
