package com.tobias.saul.payroll.PayrollRESTful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tobias.saul.payroll.PayrollRESTful.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
