package bank.atm;

import java.sql.*;  
///connecting jdbc
public class conn {
     Connection c;
    Statement s;
    public conn(){  
        try{  
            //register the driver
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            //create connection 
            c =DriverManager.getConnection("jdbc:mysql:///bankmanagementsystem","root","Yashwanth@24");    
            //create statement
            s =c.createStatement(); 
            
        }catch(Exception e){ 
            System.out.println(e);
        }  
    }  
}
