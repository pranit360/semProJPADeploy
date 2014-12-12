/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import control.CredentialJpaController;
import com.google.gson.Gson;
import control.exceptions.NonexistentEntityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Credential;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pranit Anand
 */
public class BackendTests {

    
    CredentialJpaController facade = CredentialJpaController.getFacade();
    Gson gson = new Gson();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SEMESTERprojetPU");
    static EntityManager em = emf.createEntityManager();
    List<Credential> list;

    public BackendTests() {
        list = new ArrayList();
    }

    @BeforeClass

    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
 
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


//    @Test
//    public void testGetPersonAsJson(){
//        Credential p1 = facade.addPersonFromGson(gson.toJson(new Person("bbb", "bbb", "bbb","mail.gmail.com")));
//         Credential p2 = facade.addPersonFromGson(gson.toJson(new Person("VALA", "Mata", "23563","mail.gmail.com")));
//         long nr =p1.getId();
//         long nr2 =p2.getId();
//         list.add(new Person(nr, "bbb", "bbb", "bbb","mail.gmail.com"));
//         list.add(new Person(nr2,"VALA", "Mata", "23563","mail.gmail.com"));
//String result = facade.getPersonsAsJSON();
//String execute=gson.toJson(list);
//        facade.delete(p1.getId());
//        facade.delete(p2.getId());
//assertEquals(execute,result);
//    }
//    
//    @Test
    public void testGetPersonWithId()throws Exception{
     addPersonTest();
    }
    
    
    
    @Test
    //tests the '/create' route
    public void addPersonTest() throws NonexistentEntityException{
    Credential person = facade.addCredential(gson.toJson(new Credential("Dina", "12345", "teacher")));
    String expectedJsonString = gson.toJson(person);
    String actual = facade.checkACredential(person.getUsername());
    facade.destroy(person.getUsername());
    assertEquals(expectedJsonString, actual);
    }
    
    
    @Test
    //tests the '/find' route
    public void findPersonTest() throws NonexistentEntityException{
    Credential person = facade.addCredential(gson.toJson(new Credential("Mina", "12345", "student")));
    String expectedJsonString = gson.toJson(person);
    String actual = facade.checkACredential(person.getUsername());
    facade.destroy(person.getUsername());
    assertEquals(expectedJsonString, actual);
    }
    
    
    
//    @Test
//    public void deletePersonTest()
//    {
//        Credential person = facade.addPersonFromGson(gson.toJson(new Credential("bbb", "bbb", "bbb")));
//    facade.delete(person.getId());
//    String p =facade.getPersonAsJson(person.getId());
//    boolean  cute = true;
//    if(p.equals(null)){
//        cute = false;
//    }
//       assertTrue(cute);
//  
//    }
}
