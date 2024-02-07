package com.davesdemo.springbootmongodb.service;

import com.davesdemo.springbootmongodb.exception.TodoCollectionException;
import com.davesdemo.springbootmongodb.model.TodoDTO;
import com.davesdemo.springbootmongodb.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoCollectionException{
      Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
      if (todoOptional.isPresent()) {
          throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
      } else {
          todo.setCreatedAt(new Date(System.currentTimeMillis()));
          todoRepository.save(todo);
      }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
       List<TodoDTO> todos =  todoRepository.findAll();
       if (todos.size() > 0 ){
           return todos;
       } else {
           return new ArrayList<TodoDTO>();
       }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepository.findById(id);
        if(!optionalTodo.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
            return optionalTodo.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> updateWithId = todoRepository.findById(id);
        Optional<TodoDTO> updateWithSameName = todoRepository.findByTodo(todo.getTodo());
        if (updateWithId.isPresent()){

            if (updateWithSameName.isPresent() && !updateWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            TodoDTO todoUpdate = updateWithId.get();

            todoUpdate.setTodo(todo.getTodo());
            todoUpdate.setDescription(todo.getDescription());
            todoUpdate.setCompleted(todo.getCompleted());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoUpdate);
        }else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void deleteTodoById(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional = todoRepository.findById(id);
        if (todoDTOOptional.isEmpty()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
            todoRepository.deleteById(id);
        }
    }
}
