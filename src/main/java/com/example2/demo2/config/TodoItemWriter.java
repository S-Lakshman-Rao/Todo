/*
package com.example2.demo2.config;

import java.util.List;

import com.example2.demo2.model.TodoItem;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TodoItemWriter implements ItemWriter<TodoItem> {

    private List<TodoItem> todoItems;

    @Override
    public void write(List<? extends TodoItem> items) throws Exception {
        todoItems.addAll(items);
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

}
*/