package ml.codevilla.springboot.transactional;

import ml.codevilla.springboot.student.Student;
import ml.codevilla.springboot.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionalService {

    private final StudentRepository studentRepository;

    @Autowired
    public TransactionalService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void updateStudentTransactional(Long studentId, String name, String email) {
        //first check if record exists
        Student exists = studentRepository.findById(studentId)
                .orElseThrow(
                        ()-> new IllegalStateException("student with id "+studentId+" does not exist")
                );

        if(name!=null &&
                name.length()>0 &&
                !exists.getEmail().equals(email)){
            exists.setName(name);
        }

        //check if email is not the same, if the given new email is not null and of length>0
        if(email!=null &&
                email.length()>0 &&
                !exists.getEmail().equals(email)){
            //then if true, check if email is not already taken
            //throw exception if email is already taken
            Optional<Student> emailExists = studentRepository.findStudentByEmail(email);
            if(emailExists.isPresent())
                throw new IllegalStateException("email already taken");
            exists.setEmail(email);
        }
    }
}
