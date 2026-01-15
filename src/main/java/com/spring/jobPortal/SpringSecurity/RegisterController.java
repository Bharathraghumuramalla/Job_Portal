package com.spring.jobPortal.SpringSecurity;

//import com.spring.jobPortal.security.*;
import com.spring.jobPortal.Entity.Employee;
import com.spring.jobPortal.Entity.Student;
import com.spring.jobPortal.Repository_package.EmployeeRepository;
import com.spring.jobPortal.Repository_package.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/register")
public class RegisterController {

    private final MyUserRepository repo;
    private final PasswordEncoder encoder;
    private final EmployeeRepository Erepo;
    private final StudentRepository Srepo;


    public RegisterController(MyUserRepository repo, PasswordEncoder encoder, EmployeeRepository Erepo, StudentRepository Srepo) {
        this.repo = repo;
        this.encoder = encoder;
        this.Erepo = Erepo;
        this.Srepo = Srepo;
    }
    @PostMapping("/employee")
    @Transactional
    public String registerEmployee(@RequestBody @Valid EmployeeRegisterDTO request) {

        if (repo.existsByUserName(request.getUsername())) {
            return "User already exists";
        }

        MyUser user = new MyUser();
        user.setUserName(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ROLE_EMPLOYEE");
        repo.save(user);

        Employee emp = new Employee();
        emp.setEmployeeName(request.getEmployeeName());
        emp.setAge(request.getAge());
        emp.setUser(user);
        Erepo.save(emp);
        return "Employee registered";
    }

    @PostMapping("/student")
    @Transactional
    public String registerStudent(@RequestBody StudentRegisterDTO request) {

        if (repo.existsByUserName(request.getUsername())) {
            return "User already exists";
        }

        MyUser user = new MyUser();
        user.setUserName(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("ROLE_STUDENT");
        repo.save(user);

        Student student  = new Student();
        student.setStudentName(request.getStudentName());
        student.setCollege(request.getCollege());
        student.setAge(request.getAge());
        student.setYearOfPassing(request.getYearOfPassing());
        student.setEmail(request.getEmail());
        student.setUser(user);
        Srepo.save(student);

        return "Student registered";
    }
}
