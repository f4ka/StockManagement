package nifax.control.model.handler;

import java.util.Iterator;
import java.util.List;
import nifax.control.exception.InvalidCredentials;

/**
 *
 * @author faka
 */
public class UserOperation extends ModelOperation implements IQueries{
    
    private static UserOperation instance = null;
    public UserOperation() {
    }
    
    public static UserOperation getInstance(){
        if(instance == null)
            instance = new UserOperation();
        return instance;
    }
    
    public Object Login(String username, String password) throws InvalidCredentials{
        Object obj =  Select(userLogin)
            .setParameter("username", username)
            .setParameter("password", password)
            .list()
            .get(0);
        if(obj != null)
            return obj;
        else
            throw new InvalidCredentials();
    }
    //to delete
    public void showList(List list){
        Iterator iter = list.iterator();
        if (!iter.hasNext()) {
            System.out.println("No hay n para listar");
        }
    }
    
}
