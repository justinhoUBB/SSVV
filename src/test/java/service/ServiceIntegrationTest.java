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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceIntegrationTest {

    String filenameStudent = "fisiere/test/Studenti.xml";
    String filenameTema = "fisiere/test/Teme.xml";
    String filenameNota = "fisiere/test/Note.xml";
    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    Service testService = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    @Test
    public void IT_addTema(){
        Tema testTema = new Tema("3EN","A test assignment", 11, 11);
        testService.addTema(testTema);
        assertTrue(testService.findTema(("3EN")).equals(testTema));
    }

    @Test
    public void IT_addStudent(){

        Student testStudent = new Student("23", "testStudent", 933, "test@test.com");
        testService.addStudent(testStudent);
        assertTrue(testService.findStudent("23").equals(testStudent));
    }

    @Test
    public void IT_addGrade(){
        Nota testNota = new Nota("GR1", "23", "3EN", 10,   LocalDate.now());
        testService.addNota(testNota, "Good job");
        assertTrue(testService.findNota("GR1")!= null);
        testService.deleteNota("GR1");
        testService.deleteStudent("23");
        testService.deleteTema("3EN");
    }

    @Test
    public void IT_integrationTest(){

        Tema testTema = new Tema("3EN","A test assignment", 11, 11);
        Student testStudent = new Student("23", "testStudent", 933, "test@test.com");
        Nota testNota = new Nota("GR1", "23", "3EN", 10,   LocalDate.now());

        testService.addTema(testTema);
        testService.addStudent(testStudent);
        testService.addNota(testNota, "Good job");

        assertTrue(testService.findTema(("3EN")).equals(testTema));
        assertTrue(testService.findStudent("23").equals(testStudent));
        assertTrue(testService.findNota("GR1")!= null);

        testService.deleteNota("GR1");
        testService.deleteTema("2EN");
        testService.deleteStudent("23");
    }

}
