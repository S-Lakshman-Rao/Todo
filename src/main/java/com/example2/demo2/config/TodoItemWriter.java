
package com.example2.demo2.config;

import java.util.List;

import com.example2.demo2.model.TodoItem;
import com.example2.demo2.service.TodoService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class TodoItemWriter implements ItemWriter<TodoItem> {

    @Autowired
    private TodoService todoService;

    @Override
    public void write(List<? extends TodoItem> items) throws Exception {
        for(TodoItem item: items){
            todoService.saveToDo(item);
        }
    }

}
