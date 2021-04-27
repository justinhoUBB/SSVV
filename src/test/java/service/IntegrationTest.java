package service;

import domain.Nota;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

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
    public void bbt_addStudent(){

        Student testStudent = new Student("234", "testStudent", 933, "test@test.com");
        testService.addStudent(testStudent);
        assertTrue(testService.findStudent("234").equals(testStudent));
        testService.deleteStudent("234");
    }

    @Test
    public void bbt_addTema(){

        Tema testTema = new Tema("2EN","A test assignment", 10, 6);
        testService.addTema(testTema);
        assert testService.findTema("2EN").equals(testTema);
        testService.deleteTema("2EN");
    }


    @Test
    public void bbt_addGrade(){
        Nota nota = new Nota("BBT_Nota", "BBT","BBT",9.0, LocalDate.now());
        testService.addNota(nota, "good Job");
        Nota x = testService.findNota("BBT_Nota");
        assert x.equals(nota);
        testService.deleteNota("BBT_Nota");
    }
    @Test
    public void bbt(){
        Student testStudent = new Student("BBT2", "testStudent", 933, "test@test.com");
        testService.addStudent(testStudent);
        assertEquals(testStudent, testService.findStudent("BBT2"));


        Tema testTema = new Tema("BBT2","A test assignment", 12, 6);
        testService.addTema(testTema);
        assert testService.findTema("BBT2").equals(testTema);

        Nota nota = new Nota("BBT_Nota2", "BBT2","BBT2",9.0, LocalDate.now());
        testService.addNota(nota, "good Job");
        Nota x = testService.findNota("BBT_Nota2");
        assert x.equals(nota);
        testService.deleteNota("BBT_Nota2");
        testService.deleteStudent("BBT2");
        testService.deleteTema("BBT2");

    }

    @Test
    public void inc_addStudent(){

        Student testStudent = new Student("234", "testStudent", 933, "test@test.com");
        testService.addStudent(testStudent);
        assertTrue(testService.findStudent("234").equals(testStudent));
        testService.deleteStudent("234");
    }

    @Test
    public void inc_addTema(){

        Tema testTema = new Tema("2EN","A test assignment", 15, 6);
        inc_addStudent();
        String expectedMessage = "Deadlineul trebuie sa fie intre 1-14.";
        Exception exception =  assertThrows(ValidationException.class, () -> {
            testService.addTema(testTema);
        });
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertTrue(testService.findTema("2EN") == null);
    }
}
