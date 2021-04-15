package com.mythri.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mythri.dto.Employee;

@Repository("empDao")
public class EmployeeDao {

@Autowired
private SessionFactory sessionFactory;

public void saveEmployee(final Employee employee) {
Session session = sessionFactory.openSession();
Transaction transaction = session.getTransaction();
transaction.begin();
session.save(employee);// 1 row in emp table
transaction.commit();
session.close();
}

public boolean isEmployeeExists(String str) {
Session session = sessionFactory.openSession();
Query q = session.createQuery("select count(*) from Employee where loginname=:inputName");
q.setParameter("inputName", str);
long count = (Long) q.uniqueResult();
session.close();
return count >= 1 ? true : false;
}

public Employee getValidEmpByAuth(Employee employee) {
Session session = sessionFactory.openSession();
Query q = session.createQuery("from Employee e where e.loginName=:eName and e.password=:ePass");
q.setParameter("eName", employee.getLoginName());
q.setParameter("ePass", employee.getPassword());
Employee validEmp = (Employee) q.uniqueResult();
session.close();
return validEmp;
}

public Employee searchByName(String name) {
Session session = sessionFactory.openSession();
Query q = session.createQuery("from Employee e where loginName=:name");
q.setParameter("name", name);
Employee validEmp = (Employee) q.uniqueResult();
session.close();
return validEmp;
}

public List<Employee> getAllEmployees() {
Session session = sessionFactory.openSession();
Criteria c = session.createCriteria(Employee.class);
List<Employee> list = (List<Employee>) c.list();
session.close();
return list;
}

public Employee getEmpById(int id) {
Session session = sessionFactory.openSession();
Query q = session.createQuery("from Employee e where id=:id");
q.setParameter("id", id);
Employee validEmp = (Employee) q.uniqueResult();
session.close();
return validEmp;
}

public boolean deleteEmployee(final Employee employee) {
Session session = sessionFactory.openSession();
Transaction transaction = session.getTransaction();
transaction.begin();
session.delete(employee);
transaction.commit();
session.close();
return true;
}

public void updateEmployee(final Employee newEmpDetails) {
Session session = sessionFactory.openSession();
Transaction transaction = session.getTransaction();
transaction.begin();

// get current data
Employee empFromDB = getEmpById(newEmpDetails.getId());

// update with new data
copyEmp(newEmpDetails, empFromDB);

session.merge(empFromDB);
transaction.commit();
session.close();
}

private void copyEmp(Employee source, Employee target) {
if (StringUtils.isNotEmpty(source.getName())) {
target.setName(source.getName());
}
if (StringUtils.isNotEmpty(source.getlName())) {
target.setlName(source.getlName());
}
if (StringUtils.isNotEmpty(source.getEmail())) {
target.setEmail(source.getEmail());
}
target.setAge(source.getAge());
target.setSalary(source.getSalary());
}

}