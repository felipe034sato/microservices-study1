package com.example.microservices_study.service;

import com.example.microservices_study.model.AddressResponse;
import com.example.microservices_study.model.Employee;
import com.example.microservices_study.model.EmployeeResponse;
import com.example.microservices_study.repository.EmployeeRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    public EmployeeResponse findById(Long id) {

//        employee --> employeeResponse
        Employee employee = employeeRepo.findById(id).get();
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);//Take this object and create another one with the same data
//
//        EmployeeResponse employeeResponse = new EmployeeResponse();
//        employeeResponse.setId(employee.getId());
//        employeeResponse.setName(employee.getName());
//        employeeResponse.setEmail(employee.getEmail());
//        employeeResponse.setBloodGroup(employee.getBloodGroup());

        //set data by making a rest api call
        AddressResponse addressResponse = restTemplate.getForObject("http://localhost:8083/address/{id}", AddressResponse.class, id);

        employeeResponse.setAddressResponse(addressResponse); // isso ainda esta null mas ja traz todos os atributos do AddresResponse, pois criei um atributo dentro do meu model, ai só estou preenchendo ele com o outro model

        return employeeResponse;

    }

    // Sem DTO
//    public List<Employee> findAll() {
//        List<Employee> employee = employeeRepo.findAll();
//        return employee;
//    }

    public List<EmployeeResponse> findAll() {
        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeResponse> array = new ArrayList<EmployeeResponse>();
        for (Employee employee : employees) {
            EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
            array.add(employeeResponse);
        }
        return array;
    }


    public Employee create(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee update(Employee employee) {
        Employee entity = employeeRepo.findById(employee.getId()).get();

        entity.setName(employee.getName());
        entity.setEmail(employee.getEmail());
        entity.setBloodGroup(employee.getBloodGroup());

        return employeeRepo.save(entity);

    }

    public void delete(Long id) {
        Employee entity = employeeRepo.findById(id).get();

        employeeRepo.delete(entity);
    }
}

