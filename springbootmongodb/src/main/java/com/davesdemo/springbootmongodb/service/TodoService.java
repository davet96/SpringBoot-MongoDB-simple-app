package com.davesdemo.springbootmongodb.service;

import com.davesdemo.springbootmongodb.exception.TodoCollectionException;
import com.davesdemo.springbootmongodb.model.TodoDTO;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface TodoService {

    public void createTodo(TodoDTO todo)throws ConstraintViolationException, TodoCollectionException;

    public List<TodoDTO> getAllTodos();

    public TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;

    public void deleteTodoById(String id) throws TodoCollectionException;
}
