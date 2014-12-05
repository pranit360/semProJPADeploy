/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.google.gson.Gson;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Credential;

/**
 *
 * @author mady
 */
public class CredentialJpaController implements Serializable {

    private String persistenceFileName = "SEMESTERprojetPU";

    private Gson gson = new Gson();

    public CredentialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Credential credential) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(credential);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findACredential(credential.getUsername()) != null) {
                throw new PreexistingEntityException("Credential " + credential + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Credential addCredential(String json) {
        //make person from Json
        Credential c = gson.fromJson(json, Credential.class);
        System.out.println("#####" + c.toString());
        
        EntityManager em = null;
        em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(c);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }
        System.out.println(c.toString() + " I just got added to DB!");
        return c;
    }

    /* public void edit(Credential credential) throws NonexistentEntityException, Exception {
     EntityManager em = null;
     try {
     em = getEntityManager();
     em.getTransaction().begin();
     credential = em.merge(credential);
     em.getTransaction().commit();
     } catch (Exception ex) {
     String msg = ex.getLocalizedMessage();
     if (msg == null || msg.length() == 0) {
     String id = credential.getUsername();
     if (findCredential(id) == null) {
     throw new NonexistentEntityException("The credential with id " + id + " no longer exists.");
     }
     }
     throw ex;
     } finally {
     if (em != null) {
     em.close();
     }
     }
     }  */

    /* public void destroy(String id) throws NonexistentEntityException {
     EntityManager em = null;
     try {
     em = getEntityManager();
     em.getTransaction().begin();
     Credential credential;
     try {
     credential = em.getReference(Credential.class, id);
     credential.getUsername();
     } catch (EntityNotFoundException enfe) {
     throw new NonexistentEntityException("The credential with id " + id + " no longer exists.", enfe);
     }
     em.remove(credential);
     em.getTransaction().commit();
     } finally {
     if (em != null) {
     em.close();
     }
     }
     } */

    /* public List<Credential> findCredentialEntities() {
     return findCredentialEntities(true, -1, -1);
     }  */

    /* public List<Credential> findCredentialEntities(int maxResults, int firstResult) {
     return findCredentialEntities(false, maxResults, firstResult);
     }  */

    /* private List<Credential> findCredentialEntities(boolean all, int maxResults, int firstResult) {
     EntityManager em = getEntityManager();
     try {
     CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
     cq.select(cq.from(Credential.class));
     Query q = em.createQuery(cq);
     if (!all) {
     q.setMaxResults(maxResults);
     q.setFirstResult(firstResult);
     }
     return q.getResultList();
     } finally {
     em.close();
     }
     }  */
    public Credential findACredential(String username) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Credential.class, username);
        } finally {
            em.close();
        }
    }

//    public String findCredential(String username) {
//        Credential credential = findCredential(username);
////        EntityManager em = getEntityManager();
////        try {
////            return em.find(Credential.class, username);
////        } finally {
////            em.close();
////        }
//        return gson.toJson(credential);
//    }
    
    /*  public int getCredentialCount() {
     EntityManager em = getEntityManager();
     try {
     CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
     Root<Credential> rt = cq.from(Credential.class);
     cq.select(em.getCriteriaBuilder().count(rt));
     Query q = em.createQuery(cq);
     return ((Long) q.getSingleResult()).intValue();
     } finally {
     em.close();
     }
     }  */
    public String checkCredential(String username, String password) {
        Credential credential = findACredential(username);
        String expectedPassword = credential.getPassword();
        if (password.equals(expectedPassword)) {
            return gson.toJson(credential);
        } else {
            return "User was found but the password was wrong";
        }
    }

    
    public String checkACredential(String username) {
        Credential credential = findACredential(username);
//        String expectedPassword = credential.getPassword();
//        if (password.equals(expectedPassword)) {
            return gson.toJson(credential);
//        } else {
//            return "User was found but the password was wrong";
//        }
    }
    
}
