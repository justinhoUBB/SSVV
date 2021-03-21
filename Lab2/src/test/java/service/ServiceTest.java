package service;

import domain.Student;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {


    @Test
    public void testAddStudent(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/test/testStudenti.xml";
        String filenameTema = "fisiere/test/testTeme.xml";
        String filenameNota = "fisiere/test/testNote.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student testStudent = new Student("234", "testStudent", 933, "test@test.com");
        service.addStudent(testStudent);
        assertTrue(service.findStudent("234").equals(testStudent));
    }

    @Test
    public void testAddStudentShouldFail(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/test/testStudenti.xml";
        String filenameTema = "fisiere/test/testTeme.xml";
        String filenameNota = "fisiere/test/testNote.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student testStudent = new Student("", "testStudent", 933, "test@test.com");
        String expectedMessage = "Id incorect!";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            service.addStudent(testStudent);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
