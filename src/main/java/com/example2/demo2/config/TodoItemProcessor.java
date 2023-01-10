
package com.example2.demo2.config;

import com.example2.demo2.model.TodoItem;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class TodoItemProcessor implements ItemProcessor<TodoItem, TodoItem> {

    @Override
    public TodoItem process(TodoItem todoItem) throws Exception {
        return todoItem;
    }

}
