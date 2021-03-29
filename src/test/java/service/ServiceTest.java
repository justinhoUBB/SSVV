package service;

import domain.Student;
import domain.Tema;
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
    public void tc1_addTema(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/test/testStudenti.xml";
        String filenameTema = "fisiere/test/testTeme.xml";
        String filenameNota = "fisiere/test/testNote.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service testService = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema testTema = new Tema("1EN","A test assignment", 8, 6);
        testService.addTema(testTema);
        assertTrue(testService.findTema(("1EN")).equals(testTema));
    }

    @Test
    public void tc2_addTema(){
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/test/testStudenti.xml";
        String filenameTema = "fisiere/test/testTeme.xml";
        String filenameNota = "fisiere/test/testNote.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        Service testService = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Tema testTema = new Tema("1EN","A test assignment", 15, 6);

        String expectedMessage = "Deadlineul trebuie sa fie intre 1-14.";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));

    }

    @Test
    public void tc1_addStudent(){
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
    public void tc2_addStudent(){
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
