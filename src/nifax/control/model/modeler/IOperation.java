package nifax.control.model.modeler;

import org.hibernate.Query;

/**
 *
 * @author faka
 */
public interface IOperation {
    Boolean Insert(Object obj);
    Query Select(String AQuery);
    Boolean Update(Object obj);
    Boolean Delete(Object obj);
}