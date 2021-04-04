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

    String filenameStudent = "fisiere/test/testStudenti.xml";
    String filenameTema = "fisiere/test/testTeme.xml";
    String filenameNota = "fisiere/test/testNote.xml";
    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    Service testService = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);


    @Test
    public void tc1_addTema(){

        Tema testTema = new Tema("1EN","A test assignment", 8, 6);
        testService.addTema(testTema);
        assertTrue(testService.findTema(("1EN")).equals(testTema));
        testService.deleteTema("1EN");
    }

    @Test
    public void tc2_addTema(){

        Tema testTema = new Tema("2EN","A test assignment", 15, 6);

        String expectedMessage = "Deadlineul trebuie sa fie intre 1-14.";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(testService.findTema("2EN") == null);

    }


    @Test
    public void tc3_addTema_invalidNr(){

        Tema testTema = new Tema("","A test assignment", 12, 6);

        String expectedMessage = "Numar tema invalid!";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void tc4_addTema_invalidDescription(){

        Tema testTema = new Tema("2EN","", 12, 6);

        String expectedMessage = "Descriere invalida!";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(testService.findTema("2EN") == null);
    }

    @Test
    public void tc5_addTema_invalidReceive(){

        Tema testTema = new Tema("2EN","A test assignment", 12, 15);

        String expectedMessage = "Saptamana primirii trebuie sa fie intre 1-14.";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(testService.findTema("2EN") == null);
    }

    @Test
    public void tc6_addTema_null() {
        assertThrows(NullPointerException.class, () -> {
            testService.addTema(null);
        });


    }

    @Test
    public void tc1_addStudent(){

        Student testStudent = new Student("234", "testStudent", 933, "test@test.com");
        testService.addStudent(testStudent);
        assertTrue(testService.findStudent("234").equals(testStudent));
        testService.deleteStudent("234");
    }

    @Test
    public void tc2_addStudent(){

        Student testStudent = new Student("", "testStudent", 933, "test@test.com");
        String expectedMessage = "Id incorect!";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addStudent(testStudent);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
