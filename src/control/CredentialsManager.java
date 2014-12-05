package control;

import control.exceptions.NotFoundException;
import java.util.List;
import model.Credential;


public interface CredentialsManager {
    public List<Credential> findCredentialEntities();
    
    public String findCredential(String name) throws NotFoundException;
    
    public Credential addCredential() throws NotFoundException;
    
    public Credential deleteCredential() throws NotFoundException;
}
