package nifax.control.start;

import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import nifax.control.controller.Authentication;
import nifax.control.model.modeler.CategoryOperation;
import nifax.control.model.modeler.ProductOperation;
import nifax.control.model.modeler.QuantityOperation;
import nifax.control.model.modeler.StockOperation;
import nifax.control.model.modeler.StoreOperation;

import nifax.control.exception.InitializeSessionException;
import nifax.control.exception.InvalidCredentialsException;
import nifax.control.hibernate.HibernateUtil;
import nifax.control.model.*;
import nifax.control.model.modeler.HQLOperation;
import nifax.control.data.IQueries;

/**
 *
 * @author faka
 */
public class NifaxControl implements IQueries{

    private static final Logger logger = Logger.getLogger(NifaxControl.class.getName());

    public static void main(String[] args) throws InvalidCredentialsException, InitializeSessionException {
        Authentication auth = Authentication.getInstance();
        try {
            UserEmployee usr;
            HQLOperation modelOperation = HQLOperation.getInstance();
            usr = new UserEmployee(
                "f4ka",
                "1234",
                new Employee(
                    "Facundo",
                    "Gomez Algazan",
                    "+5493816534690"
                )
            );
            modelOperation.Insert(usr);
            // the following lines will be added temporally til we have an UI to 
            // enter the required fields
            Scanner in = new Scanner(System.in);
            String user;
            String pass;
            do {
                logger.info("Ingresar Usuario");
                user = in.next();
                logger.info("Ingresar Password");
                pass = in.next();
            } while (!auth.LogIn(new UserEmployee(user, pass)));
            logger.info("El Usuario fue logueado con éxito");
            modelOperation.Insert(auth.getSession());
            
            //Loading scenario
            //Add categories
            modelOperation.Insert(new Category("Audio"));
            modelOperation.Insert(new Category("Iluminacion"));
            //Add prices
            modelOperation.Insert(new Price("Lista1", 1.85));
            modelOperation.Insert(new Price("Lista2", 1.75));
            modelOperation.Insert(new Price("Lista3", 1.55));
            modelOperation.Insert(new Price("Lista4", 1.25));
            modelOperation.Insert(new Price("Lista5", 1.05));
            //Add stores
            modelOperation.Insert(new Store("Deposito central"));
            modelOperation.Insert(new Store("Deposito alternativo"));
            //Add quantities type
            modelOperation.Insert(new Quantity("Bulto"));
            modelOperation.Insert(new Quantity("Paquete"));

            //Get category list from db
            Map<String, Category> CategoryList = CategoryOperation.getInstance().List();
            //Get quantity list from db
            Map<String, Quantity> QuantityList = QuantityOperation.getInstance().List();
            //Add product
            String productDesc = "Foco 12V";
            double cost = 76.40;
            Product product = ProductOperation.getInstance().Add(
                productDesc, 
                cost, 
                CategoryList.get("Audio")
            );
            //Add quantity
            double quantityValue = 12.0;
            Set<ProductQuantity> productQuantities = new HashSet<>();
            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProduct(product);
            productQuantity.setTypeQuantity(QuantityList.get("Bulto"));
            productQuantity.setQuantity(quantityValue);
            productQuantities.add(productQuantity);
            product.setProductQuantities(productQuantities);

            modelOperation.Insert(product);
            logger.info("Producto insertado correctamente");
            //Get product list from db
            Map<String, Product> productList = ProductOperation.getInstance().List();
            //Get store list from db
            Map<String, Store> storeList = StoreOperation.getInstance().List();
            //Add stock
            String description = "STOCK";
            double quantityStock = 500;
            StockOperation.getInstance().Add(
                description, 
                quantityStock, 
                QuantityList.get("Bulto"), 
                productList.get("Foco 12V"), 
                storeList.get("Deposito central")
            );
        } finally {
            Authentication.getInstance().LogOut(Authentication.getInstance().getSession());
            HibernateUtil.getSessionFactory().close();
        }
    }
}
