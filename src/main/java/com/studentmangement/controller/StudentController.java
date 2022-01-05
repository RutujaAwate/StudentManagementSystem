package com.studentmangement.controller;

import com.studentmangement.entity.Student;
import com.studentmangement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    

    @RequestMapping("/students")
    public String getAllStudents(Model model){
        model.addAttribute("students" , studentService.getAllStudents());
        return "students";
    }

    //create student object to hold student form data
    @RequestMapping("/students/new")
    public String createStudentForm(Model model){
        Student student = new Student();
        model.addAttribute("student" , student);
        return "create_student";
    }

    //handle update student method
    @RequestMapping(method = RequestMethod.POST, value="/students")
    public String saveStudent(@ModelAttribute("student") Student student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }
    @RequestMapping("/students/edit/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model){
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @RequestMapping(method = RequestMethod.POST,value="/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,Model model){
        Student existStudent= studentService.getStudentById(id);
        existStudent.setId(id);
        existStudent.setFirstName(student.getFirstName());
        existStudent.setLastName(student.getLastName());
        existStudent.seteMail(student.geteMail());

        //save updated data
        studentService.updateStudent(existStudent);
        return "redirect:/students";
    }
    //handle delete method
    @RequestMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }

}
