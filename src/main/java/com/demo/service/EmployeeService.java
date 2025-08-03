package com.demo.service;

import java.sql.ResultSet;
import java.util.List;

import com.demo.dao.EmployeeDAO;
import com.demo.model.Employee;

public class EmployeeService {
	EmployeeDAO employeeDAO;

	public EmployeeService() {
		this.employeeDAO = new EmployeeDAO();
	}

	/**
	 * Find all employees affiliated to a department
	 * 
	 * @param List of column labels
	 * @param List of employees associated to a department
	 */
	public void listOfEmployeesAssociatedToDepartment(
			List<String> columnLabels, 
			List<Employee> employeesAssociatedToDepartment)
	{
		this.employeeDAO.getEmployeesAssociatedToDepartment(
				columnLabels, 
				employeesAssociatedToDepartment
		);
	}

	/**
	 * Find all employees NOT affiliated to a department
	 * 
	 * @param List of column labels
	 * @param List of employees NOT associated to a department
	 */
	public void listOfEmployeesNotAssociatedToDepartment(
			List<String> columnLabels, 
			List<Employee> employeesNotAssociatedToDepartment) 
	{
		this.employeeDAO.getEmployeesNotAssociatedToDepartment(
				columnLabels, 
				employeesNotAssociatedToDepartment
		);
	}

	/**
	 * 4 Find the Average salary of all employees
	 * 
	 * @return ResultSet of the average salary of all employees
	 */
	public double averageEmployeesSalary() {
		return this.employeeDAO.getAverageSalaryOfAllEmployees();
	}

	/**
	 * 5 Find the Max and Min salaries of employees
	 * 
	 * @return ResultSet of the max and min salaries
	 */
	public ResultSet maxAndMinSalaries() {
		return this.employeeDAO.getMaximumAndMinimumSalaries();
	}

	/**
	 * 6 Find the second Max salary
	 * 
	 * @return ResultSet of the second max salary
	 */
	public ResultSet secondmaximumSalary() {
		return this.employeeDAO.getSecondMaximumSalary();
	}

	/**
	 * Find the employees earning the second maximum salary
	 * 
	 * @param List of column labels
	 * @param List of employees earning the second maximum salary
	 */
	public void employeesEarningSecondMaximumSalary(
			List<String> columnLabels, 
			List<Employee> employeesEarningSecondMaximumSalary)
	{
		this.employeeDAO.getEmployeesEarningSecondMaximumSalary(
				columnLabels,
				employeesEarningSecondMaximumSalary
		);
	}

	/**
	 * 12 Add a new employee
	 * 
	 * @param fullname employee's firstname and lastname
	 * @param age      employee's age
	 * @param salary   employee's salary
	 * @return 1 if the employee is added, 0 otherwise.
	 */
	public int addNewEmployee(String fullname, int age, double salary) {
		int employeesAdded = this.employeeDAO.addEmployee(fullname, age, salary);
		return employeesAdded;
	}

	/**
	 * Delete an employee from the database
	 * 
	 * @param employeeID
	 * @return true if employee is deleted, false otherwise.
	 */
	public Boolean deleteEmployee(int employeeID) {
		return this.employeeDAO.deleteEmployee(employeeID);
	}

	/**
	 * Return the number of employees updated
	 * 
	 * @param employeeID
	 * @param name
	 * @param age
	 * @param salary
	 * @return number of updated records in the DB. 
	 */
	public int updateEmployee(int employeeID, String name, int age, double salary) {
		return this.employeeDAO.updateEmployee(employeeID, name, age, salary);
	}

	/**
	 * Get all employees
	 * 
	 * @return list of employees
	 */
	public List<Employee> getEmployeeList() {
		return this.employeeDAO.getEmployees();
	}
}
