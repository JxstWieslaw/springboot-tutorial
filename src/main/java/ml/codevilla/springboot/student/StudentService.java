package ml.codevilla.springboot.student;

/*Student Service Class
    [12]-create Service Class @Service
    [13]-copy business logic methods in Controller to Service
    [14]-create reference to service class
        ->go to StudentController
    [28]-private final StudentRepository studentRepository;
        @Autowired instance into constructor of StudentService.
        use . dot operator to access functions
        e.g. studentRepository.findAll() returns List<T> by default.
    [29]-to preload students:
        You need to insert records at the start of server.
        create Class for this feature: go to ->StudentConfig
    [45]-invoke repository method for validation and store it in an Optional.
    [46]-check for condition:
        if(Optional.isPresent) returns true then throw an exception
            throw new IllegalStateException("email taken).
            Set error.messages-include property in Properties File to always.
        else
            save student.
    [47]-Start the server check if PostMapping is Working.
    [48]-DELETE MAPPING - create deleteMapping endpoint in Controller
        go to-> StudentController
   [[52]-Within deleteStudent method:
        invoke .existsById as it returns a boolean...in this case
        findById will also work in the case, except it returns an object of that Student.
    [53]- if (student does not exist)
                throw new IllegalStateException(
                    "student with id " + studentId + "does not exist");
           else
                studentRepository.deleteById(studentId);
    [54]-Start Server and test the DELETE MAPPING
    [55]-PUT MAPPING :
        go to->Student Controller
    [57]-create updateStudent function
        By using the @Transactional, it means you will not use JpQL
        Use the setters and getters to update records

        first check if record exists :use studentRepo.findById(studentId).orElseThrow(()->new IllegalStateException("not present");
            if it exists
        check if name is not the same, if the given new name is not null and of length>0
            then if true, studentObject.setName(name);
        check if email is not the same, if the given new email is not null and of length>0
            then if true, check if email is not already taken
                if (.isPresent) returns true
                    throw exception if email is already taken
                else
                    studentObject.setEmail(email);
    [58]-Restart Server and check if method is correctly implemented.
    [59]-Implement Testing, its important
    [60]-Create deployment .jar file Maven Clean...Maven Install
    [61]-Run it using java -jar
    [62]-Use the terminal to gain experience
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    //dependency injected  repository
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        //first check if record exists
        Student studentExists= studentRepository.findById(studentId)
                .orElseThrow(()-> new IllegalStateException("student with id"+studentId+" does not exist")
        );
        //check if name is not the same, if the given new name is not null and of length>0
        if(name != null &&
                name.length()>0 &&
                !Objects.equals(studentExists.getName(),name)){
            studentExists.setName(name);
        }

        //check if email is not the same, if the given new email is not null and of length>0
        if(email != null &&
                email.length()>0 &&
                !Objects.equals(studentExists.getEmail(),email)){
            //then if true, check if email is not already taken
            //throw exception if email is already taken
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentExists.getEmail());
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            studentExists.setEmail(email);
        }

    }
}
