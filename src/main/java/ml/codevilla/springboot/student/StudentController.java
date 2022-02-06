package ml.codevilla.springboot.student;
/*Create ControllerClass
    [7]-create the api layer @RestController or @Controller
    [8]-do sample endpoint routing use @GetMapping, return String
        Print text on web pages as means of testing
    [9]-do sample JSON @ResponseBody @RequestBody using @GetMapping
    [10]-do @GetMapping endpoint to return List<Student>
    return List.of(new Student(pass arguments),...)
    [11]-Notice that so far business logic is in Controller Class.
        Such is not recommended. It should be moved to a Service Class.
    [15]-inject StudentService into StudentController class
        i.e. private final StudentService studentService
        Call business logic methods from StudentService
        using . dot operator
    [16]-Set up properties file for connection to database
        and others. Creat database. Attach Driver for database.
    [17]-Give privileges to correct users and authenticate.
    [18]-Connect to Database plugin to run queries internally.
        Use of Terminal is allowed, within IDE there is a terminal.
    [19]-Setup Jpa and @Entity @Table -> go to Student Class
    [37]-POST MAPPING create the @PostMapping endpoint
    [38]-the method registerNewStudent will :
        invoke the .addNewStudent function
        from StudentService using studentService reference
        pass student as argument for addNewStudent method
        to pass JSON object, map the object using @RequestBody to (Student student)
    [39]-go to StudentService, create the addNewStudentMethod,
        print student object for now.
    [42]-use of .save() is usually necessary
        when checking for conditions and after decision it is executed
    [43]-create custom function that returns an Optional in StudentRepository
        go to ->StudentRepository
    [49]-DELETE MAPPING create deleteStudent method
        add path variable endpoint
        add @PathVariable mapping to Long studentId passed
        we can also enforce whether it's not null or null...
    [50]-the method deleteStudent will :
        invoke the .deleteStudent function
        from StudentService using studentService reference
        pass studentId as argument for deleteStudent method
    [51]-go to ->StudentService and invoke deletion.
    [55]-PUT MAPPING  create updateStudent method
        add path variable endpoint
        add @PathVariable mapping to Long studentId passed
        @RequestParam to map name and email
    [56]-the method updateStudent will :
        invoke the .updateStudent function
        from StudentService using studentService reference
        pass studentId, name and email as argument for updateStudent method
    [57] go to->Student Service

*/
import ml.codevilla.springboot.transactional.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= "api/v1/student")
public class StudentController {

    //dependency injected into constructor of Controller class
    private final StudentService studentService;
    private final TransactionalService transactionalService;

    @Autowired
    public StudentController(StudentService studentService, TransactionalService transactionalService) {
        this.studentService = studentService;
        this.transactionalService = transactionalService;
    }

//    @PutMapping(path= "{studentId}")
//    public void updateStudentTransactional(@PathVariable Long studentId,
//                                @RequestParam (required = false) String name,
//                                @RequestParam (required = false) String email){
//        transactionalService.updateStudentTransactional(studentId,name,email);
//    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @PutMapping(path="{studentId}")
    public void updateStudent(@PathVariable Long studentId,
                              @RequestParam (required = false) String name,
                              @RequestParam (required = false) String email){
        studentService.updateStudent(studentId,name,email);
    }

}
