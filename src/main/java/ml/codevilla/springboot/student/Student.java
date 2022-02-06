package ml.codevilla.springboot.student;

/*Create Entity Class
    [1]-create fields for the table
    [2]-create empty constructor/NoArgsConstructor
    [3]-create all elements constructor/AllArgsConstructor
    [4]-create constructor without id field
    [5]-create getters and setters/@Getters @Setters
    [6]-Override toString method.
    [20]- @Entity @Table
    [21] For id field add:
         @Id
         @SequenceGenerator(name,sequenceName,allocationSize)
         @GeneratedValue(strategy, generator)
    [21]-Run the application, JPA should in Action now.
    [22]-in order to access JPA methods and Classes,we need:
      A Repository INTERFACE.-> Create StudentRepository
    [32]Always import java.persistence.* because:
        if you change the provider from Hibernate, everything will still work.
        Annotate age field with @Transient
        Remove age field from constructors
        Remove age argument from CommandLineRunner and everywhere it has been passed
        Calculate age in getAge getter method for age field:
            return Period.between(this.dob,LocalDate.now()).getYears;
    [33]-Restart Server to check if it works.
    [34]-Take note to check and confirm that :
        Age field is no longer maintained in our database
        The api shows JSON will correct and updated ages.
        It is still maintained within our Entity, not in the Table
    [35]-POST MAPPING:
        you want to save a payload from a client?
        you want to pass that payload across.
        you want to check if email/record already exists?
        if it doesn't -> (then we save the student to database)
        if it does ->(throw an exception and not save)
    [36]-go to -> StudentController
    [40]-create the addNewStudentMethod,
       print student object for now.
    [41] test the POSTMAPPING:
        you can use http generator in IDE or PostMan
        go to -> StudentController




*/


import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    @Transient
    private int age;
    private LocalDate dob;

    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age  +
                ", dob=" + dob +
                '}';
    }
}
