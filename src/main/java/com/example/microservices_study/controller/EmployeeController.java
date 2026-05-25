package com.example.microservices_study.controller;

import com.example.microservices_study.model.Employee;
import com.example.microservices_study.model.EmployeeResponse;
import com.example.microservices_study.repository.EmployeeRepository;
import com.example.microservices_study.service.EmployeeService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Handler;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "{id}" ,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> findById(@PathVariable("id") Long id){ //o PathVariable vai conseguir ler esse parametro e armazenar dentro do int id
        EmployeeResponse employee = employeeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
        //Ultiliza o ResponseEntitiiy como uma boa pratica para retornar o status correto
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeResponse> findAll(){
        return employeeService.findAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee create(@RequestBody Employee employee){ //Transfroma o JSON que veio da resposta HTTP em um objeto java(employee.setName(...)
        return employeeService.create(employee);
    }

    @PutMapping(value = "{id}",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee update(@PathVariable("id") Long id, @RequestBody Employee employee){
        return employeeService.update(employee);
    }

    @DeleteMapping(value = "{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") Long id){
        employeeService.delete(id);
    }
}
