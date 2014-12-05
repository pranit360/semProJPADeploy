package Populate;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.Credential;

public class populate {
public static void main(String[] args) {
        String persistenceFileName = "SEMESTERprojetPU";
        ArrayList<Credential> Users = new ArrayList();

        Credential u1= new Credential("Sven", "1234", "student");
        Credential u2 = new Credential("Mada","1234", "student");
        Credential u3 = new Credential("pranit","abcd", "Teacher");

        Users.add(u1);
        Users.add(u2);
        Users.add(u3);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceFileName);
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            for (Credential u : Users) {
                em.persist(u);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            transaction.rollback();
        }
        transaction.commit();
        em.close();
    }
}