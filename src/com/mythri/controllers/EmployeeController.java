package com.mythri.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mythri.dto.Employee;
import com.mythri.exception.UserException;
import com.mythri.service.EmployeeService;

@Controller
public class EmployeeController {

@Autowired
private EmployeeService employeeService;

@RequestMapping("/register")
public ModelAndView showAddEmp() {
Employee employee = new Employee();
return new ModelAndView("register", "command", employee);
}

@RequestMapping(value = "/register", method = RequestMethod.POST)
public ModelAndView addEmp(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
if (result.hasErrors()) {
String message = "Registration failed due to misisng data";
ModelAndView model = new ModelAndView("register", "command", employee);
model.addObject("msg", message);
return model;
}

try {
employeeService.addEmployee(employee);
} catch (UserException e) {
String msg = e.getMessage();
ModelAndView modelAndView = new ModelAndView("register", "command", employee);
modelAndView.addObject("msg", msg);
return modelAndView;
}
ModelAndView model = new ModelAndView("login", "emp", employee);
model.addObject("msg", "Employee Created!");
return model;

}

@RequestMapping("/login")
public String login() throws Exception {
return "login";
}

@RequestMapping(value = "/login", method = RequestMethod.POST)
public ModelAndView login(@ModelAttribute("command") Employee employee, BindingResult result, HttpSession session) {
// make a db call and check if login is valid
Employee validUser = employeeService.getValidEmpByAuth(employee);
if (validUser != null) {
// login success show profile.jsp
session.setAttribute("myId", validUser.getId());
session.setAttribute("loginName", validUser.getLoginName());
session.setAttribute("name", validUser.getName());
ModelAndView modelAndView = new ModelAndView("profile", "emp", validUser);
return modelAndView;

} else {
// login failure show login.jsp
return new ModelAndView("login", "msg", "Invalid Login..");
}

}

// For logout , delete all the data in the session and the show login page with
// msg "Logout successfull"
// controller method when customer clicks on logout link
@RequestMapping("/logout")
public ModelAndView logout(HttpSession session) {
session.removeAttribute("loginName");
session.removeAttribute("name");
session.invalidate();
ModelAndView response = new ModelAndView("login");
response.addObject("msg", "Logout successfull");
return response;
}

// controller method when cutsomer clicks on "MyProfile" link
@RequestMapping(value = "/profile", method = RequestMethod.GET)
public ModelAndView profile(@RequestParam("loginName") String name) {
Employee validUser = employeeService.getEmpByName(name);
if (validUser != null) {
ModelAndView modelAndView = new ModelAndView("profile", "emp", validUser);
return modelAndView;
}
return new ModelAndView("profile", "msg", "No emp exist");
}

// controller method when customer clicks on "show all users" link
// show response using showEmps.jsp
@RequestMapping("/getAllEmps")
public ModelAndView getAllEmps() {
List<Employee> emps = employeeService.getEmployees();
return new ModelAndView("showEmps", "emps", emps);
}

// controller method for click on "Add Emp" Link and show response using
// "showAddEmp.jsp"
@RequestMapping("/addEmp")
public ModelAndView showAddEmpByUser() {
Employee employee = new Employee();
return new ModelAndView("showAddEmp", "command", employee);
}

@RequestMapping(value = "/addEmp", method = RequestMethod.POST)
public ModelAndView addEmpByUser(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {

if (result.hasErrors()) {
String message = "Error while creating emp";// getErrorMsg(result);
ModelAndView model = new ModelAndView("showAddEmp", "command", employee);
model.addObject("msg", message);
return model;
}

try {
employeeService.addEmployee(employee);
} catch (UserException e) {
String msg = e.getMessage();
ModelAndView modelAndView = new ModelAndView("showAddEmp", "command", employee);
modelAndView.addObject("msg", msg);
return modelAndView;
}
ModelAndView model = new ModelAndView("showEmp", "emp", employee);
model.addObject("msg", "Employee Created!");
return model;

}

// controller method when customer clicks on "search emp" link
@RequestMapping("/readUser")
public String showGetEmp() throws Exception {
return "readUser";
}

// controller method when customer clicks on "search" Button
// as there is a input for id use @RequestParam
// show response using readUser.jsp
// if id is valid show show empl details
// if id is invalid show "Employee Not found"

@RequestMapping(value = "/readUser", method = RequestMethod.POST)
public ModelAndView getEmpById(@RequestParam("id") int empId) throws Exception {
Employee employee = employeeService.getEmpById(empId);
ModelAndView modelAndView = new ModelAndView("readUser");
if (employee == null) {
modelAndView.addObject("msg", "Employee Not found");
} else {
modelAndView.addObject("emp", employee);
}
return modelAndView;
}

// controller method when customer clicks on "delete emp" link
@RequestMapping("/deleteEmp")
public String showDeleteEmp() throws Exception {
return "deleteUser";
}

@RequestMapping(value = "/deleteEmp", method = RequestMethod.POST)
public ModelAndView deleteEmp(@RequestParam("id") int userId) throws Exception {
Employee emp = new Employee();
emp.setId(userId);
boolean status = employeeService.deleteEmployee(emp);
if (!status) {
return new ModelAndView("deleteUser", "messageInfo", "Employee Id Not found.");
}
return new ModelAndView("deleteUser", "messageInfo", "Deleted successfully");
}

// method to show curr emp data based on emp id
@RequestMapping("/editEmp")
public ModelAndView showEditEmp(@RequestParam("empId") int empId) {
Employee emp = employeeService.getEmpById(empId);
return new ModelAndView("showEditEmp", "command", emp);
}

@RequestMapping(value = "/editEmp", method = RequestMethod.POST)
public ModelAndView updateEmp(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {

try {
employeeService.updateEmployee(employee);
ModelAndView modelAndView = new ModelAndView("showEmp", "emp", employee);
modelAndView.addObject("msg", "update successful!!");
return modelAndView;
} catch (UserException e) {
String msg = e.getMessage();
ModelAndView modelAndView = new ModelAndView("showEditEmp", "command", employee);
modelAndView.addObject("msg", msg);
return modelAndView;
}

}

}