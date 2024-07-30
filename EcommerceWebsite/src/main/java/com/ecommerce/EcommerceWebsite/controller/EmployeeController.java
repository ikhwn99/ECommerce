package com.ecommerce.EcommerceWebsite.controller;

import com.ecommerce.EcommerceWebsite.model.Employee;
import com.ecommerce.EcommerceWebsite.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "employee/index";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee/new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
        Employee employee = employeeService.getEmployeeById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "employee/update_employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/employee";
    }
}

// @RestController
// @RequestMapping("/api/employee")
// public class EmployeeController {
//     @Autowired
//     private EmployeeService employeeService;

//     @GetMapping
//     public String viewHomePage(Model model) {
//         model.addAttribute("listEmployees", employeeService.getAllEmployees());
//         return "employee/index";
//     }

//     @GetMapping("/showNewEmployeeForm")
//     public String showNewEmployeeForm(Model model) {
//         Employee employee = new Employee();
//         model.addAttribute("employee", employee);
//         return "new_employee";
//     }

//     @PostMapping("/saveEmployee")
//     public String saveEmployee(@ModelAttribute("employee") Employee employee) {
//         employeeService.saveEmployee(employee);
//         return "redirect:/";
//     }

//     @PutMapping("/showFormForUpdate/{id}")
//     public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
//         Employee employee = employeeService.getEmployeeById(id);
//         model.addAttribute("employee", employee);
//         return "update_employee";
//     }

//     @DeleteMapping("/{id}")
//     public String deleteEmployee(@PathVariable(value = "id") Long id) {
//         this.employeeService.deleteEmployeeById(id);
//         return "redirect:/";
//     }

// }
