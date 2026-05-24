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
        AddressResponse addressResponse  = restTemplate.getForObject("http://localhost:8083/address/{id}", AddressResponse.class, id);

        employeeResponse.setAddressResponse(addressResponse); // isso ainda esta null mas ja traz todos os atributos do AddresResponse, pois criei um atributo dentro do meu model, ai só estou preenchendo ele com o outro model

        return employeeResponse;

    }

    public Employee create(Employee employee) {
         return employeeRepo.save(employee);
    }
}
