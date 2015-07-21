package nifax.control.start;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import nifax.control.controller.Authentication;
import nifax.control.model.modeler.CategoryOperation;
import nifax.control.model.modeler.ProductOperation;
import nifax.control.model.modeler.MeasureOperation;
import nifax.control.model.modeler.StockOperation;
import nifax.control.model.modeler.StoreOperation;
import nifax.control.exception.InitializeSessionException;
import nifax.control.exception.InvalidCredentialsException;
import nifax.control.hibernate.HibernateUtil;
import nifax.control.model.*;
import nifax.control.model.modeler.HQLOperation;
import nifax.control.data.IQueries;
import nifax.control.model.modeler.OfferOperation;
import nifax.control.model.modeler.RestorationOperation;
import nifax.control.model.modeler.SaleOperation;
import nifax.control.model.modeler.TypeSaleDocOperation;

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
            //Add measures
            modelOperation.Insert(new Measure("Bulto"));
            modelOperation.Insert(new Measure("Paquete"));
            modelOperation.Insert(new Measure("Unidad"));
   
            //Add type of sale's document
            TypeSaleDocOperation.getInstance().add("Ticket");
            TypeSaleDocOperation.getInstance().add("Factura ");
            TypeSaleDocOperation.getInstance().add("Factura electronica");
            
            //Get category list from db
            Map<String, Category> CategoryList = CategoryOperation.getInstance().List();
            //Get quantity list from db
            Map<String, Measure> measureList = MeasureOperation.getInstance().List();
            //Add product 1
            String productDesc = "Foco 12V";
            double cost = 76.40;
            
            //Genering ProductMeasure for product - rules.
            List<ProductMeasure> measures  =new ArrayList<ProductMeasure>();
            
            double quantityValue = 12.0;
            
            ProductMeasure productMeasure = new ProductMeasure();
            productMeasure.setMeasure(measureList.get("Bulto"));
            productMeasure.setQuantity(quantityValue);
            measures.add(productMeasure);
            
            quantityValue=24;
            ProductMeasure productMeasure2 = new ProductMeasure();
            productMeasure2.setMeasure(measureList.get("Paquete"));
            productMeasure2.setQuantity(quantityValue);
            measures.add(productMeasure2);
            
            //Add product 
            ProductOperation.getInstance().Add(productDesc,cost,CategoryList.get("Audio"),measures);
            
            //Add product 2
             productDesc = "Lampara";
             cost = 16.10;
            
            //Genering ProductMeasure for product - rules.
            measures  =new ArrayList<ProductMeasure>();
            
            quantityValue = 32.0;
            
            productMeasure = new ProductMeasure();
            productMeasure.setMeasure(measureList.get("Bulto"));
            productMeasure.setQuantity(quantityValue);
            measures.add(productMeasure);
            
            //Add product
            ProductOperation.getInstance().Add(productDesc,cost,CategoryList.get("Audio"),measures);
            
            //Get product list from db
            Map<String, Product> productList = ProductOperation.getInstance().List();
            //Get store list from db
            Map<String, Store> storeList = StoreOperation.getInstance().List();
            //Add stock
            
            // Stock Product 1
            String description = "STOCK";
            double quantityStock = 500;
            StockOperation.getInstance().Add(description, 
                quantityStock, 
                measureList.get("Unidad"), 
                productList.get("Foco 12V"), 
                storeList.get("Deposito central")
            );
            
            // Stock Product 2
            quantityStock = 120;
            StockOperation.getInstance().Add(description, 
                quantityStock, 
                measureList.get("Unidad"), 
                productList.get("Lampara"), 
                storeList.get("Deposito central")
            );
            
            //Add Offer 
            //Offer Product 1
            OfferOperation.getInstance().add("Oferta mes mayo",10,20,
                    measureList.get("Unidad"),productList.get("Foco 12V") );
            
            //Add Restoration 
            //Restoration Product 1
            RestorationOperation.getInstance().add(
                    "Rep al mes de abril",
                    900.0,
                    560.0,
                    140.0,
                    measureList.get("Unidad"),
                    productList.get("Foco 12V"),
                    storeList.get("Deposito central")
                            );
            
            RestorationOperation.getInstance().add(
                    "Rep al mes de abril",
                    600.0,
                    350.0,
                    100.0,
                    measureList.get("Unidad"),
                    productList.get("Foco 12V"),
                    storeList.get("Deposito alternativo")
                            );
            
            //Add Sale.
            double itemQuantity;
            double itemPrice ;
            Product itemProduct;
            
            List<SaleDocProduct> saleDocProducts  =new ArrayList<SaleDocProduct>();
                        
            // Sale Product 1   
            itemQuantity = 240;
            itemPrice = 3.5;
            itemProduct=productList.get("Foco 12V");
            
            SaleDocProduct saleDocProduct = new SaleDocProduct();
            saleDocProduct.setProduct(itemProduct);
            saleDocProduct.setQuantity(itemQuantity);
            saleDocProduct.setPrice(itemPrice);
            saleDocProducts.add(saleDocProduct);
            
            // Sale Product 2   
            itemQuantity = 130;
            itemPrice = 6.25;
            itemProduct=productList.get("Lampara");
            
            SaleDocProduct saleDocProduct2 = new SaleDocProduct();
            saleDocProduct2.setProduct(itemProduct);
            saleDocProduct2.setQuantity(itemQuantity);
            saleDocProduct2.setPrice(itemPrice);
            saleDocProducts.add(saleDocProduct2);
            
            Calendar calendar = Calendar.getInstance();

            //Get typSaleDoc list from db
            Map<String, TypeSaleDoc> typeSaleDocList = StoreOperation.getInstance().List();
            
            SaleOperation.getInstance().add(calendar.getTime(),
                    usr,
                    typeSaleDocList.get("Ticket"),
                    saleDocProducts
                            );
            
        } finally {
            Authentication.getInstance().LogOut(Authentication.getInstance().getSession());
            HibernateUtil.getSessionFactory().close();
        }
    }
}
