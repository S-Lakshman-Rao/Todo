package com.example2.demo2.repo;

import com.example2.demo2.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TodoRepo extends JpaRepository<TodoItem,Long> , JpaSpecificationExecutor<TodoItem> {


    List<TodoItem> findByuserid(int userid);

}
