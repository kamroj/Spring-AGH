package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentController {

    @RequestMapping(value="/Students")
    public String listStudents(Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null)
            return "redirect:/Login";

        model.addAttribute("students", DatabaseConnector.getInstance().getStudent());

        return "studentList";
    }

    @RequestMapping(value="/AddStudent")
    public String displayAddStudentForm(Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null)
            return "redirect:/Login";

        return "studentForm";
    }

    @RequestMapping(value="/CreateStudent", method=RequestMethod.POST)
    public String createStudent(@RequestParam(value="studentName", required=false) String name,
                               @RequestParam(value="studentSurname", required=false) String surname,
                               @RequestParam(value="studentPesel", required=false) String pesel,
                               Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null)
            return "redirect:/Login";

        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setPesel(pesel);

        DatabaseConnector.getInstance().addStudnet(student);
        model.addAttribute("students", DatabaseConnector.getInstance().getStudent());
        model.addAttribute("message", "Nowy uczeń został dodany");

        return "studentList";
    }

    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST)
    public String deleteStudent(@RequestParam(value="studentId", required=false) String studentId,
                               Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null)
            return "redirect:/Login";

        DatabaseConnector.getInstance().deleteStudent(studentId);
        model.addAttribute("students", DatabaseConnector.getInstance().getStudent());
        model.addAttribute("message", "Uczeń został usunięty");

        return "studentList";
    }


}