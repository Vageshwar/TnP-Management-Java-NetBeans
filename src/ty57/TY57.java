/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ty57;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*; 

/**
 *
 * @author vageshwar
 */
public class TY57 {

    /**
     * @param args the command line arguments
     */
    static String addBinary(String a, String b) 
    { 
          
        // Initialize result 
        String result = "";  
          
        // Initialize digit sum 
        int s = 0;          
  
        // Traverse both strings starting  
        // from last characters 
        int i = a.length() - 1, j = b.length() - 1; 
        while (i >= 0 || j >= 0 || s == 1) 
        { 
              
            // Comput sum of last  
            // digits and carry 
            s += ((i >= 0)? a.charAt(i) - '0': 0); 
            s += ((j >= 0)? b.charAt(j) - '0': 0); 
  
            // If current digit sum is  
            // 1 or 3, add 1 to result 
            result = (char)(s % 2 + '0') + result; 
  
            // Compute carry 
            s /= 2; 
  
            // Move to next digits 
            i--; j--; 
        } 
          
    return result; 
    } 
    public static void main(String[] args) {
    Connection conn;
    String url = "jdbc:postgresql://localhost:5432/blanksales";
    String user = "blank";
    String password = "blank";
    ArrayList<String> city = new ArrayList<String>();
    Hashtable<Integer, String> hm = new Hashtable<Integer, String>(); 
    
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TY57.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connected");
            PreparedStatement pst;
            String sql = "select city from client_master";
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                city.add(rs.getString("city"));
            }
            for(int i = 0;i<city.size();i++)
            {
                String s = city.get(i);
                char c0 =s.charAt(0);
                String b1 = Integer.toBinaryString((int)c0);
                for(int j=1;j<s.length();j++)
                {
                    char c1 =s.charAt(j);
                    String b2 =Integer.toBinaryString((int)c1);
                    b1 = addBinary(b1,b2);
                }
                int hashValue = Integer.parseInt(b1)%5;
                hm.put(hashValue,s); 
            }
            System.out.println(hm);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TY57.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Failed");
        }
       
    
    }
}
