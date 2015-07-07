package nifax.control.model.handler;

import java.util.Iterator;
import java.util.List;
import nifax.control.exception.InvalidCredentialsException;

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
    
    public Object Login(String username, String password) throws InvalidCredentialsException{
        Object obj = null;
        List<Object> list = Select(userLogin)
            .setParameter("username", username)
            .setParameter("password", password)
            .list();
        if(!list.isEmpty())
            return list.get(0);
        else
            throw new InvalidCredentialsException();
    }
    //to delete
    public void showList(List list){
        Iterator iter = list.iterator();
        if (!iter.hasNext()) {
            System.out.println("No hay n para listar");
        }
    }
    
}
