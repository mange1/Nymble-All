/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author admin
 */
public class DateUsing {

    
    public static void main(String[] args)
    {
        System.out.println("Date : "+DateUsing.getDate());
    }
    
    
    public static String getDate() {
        String date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        java.util.Date date1 = new java.util.Date();
        date = dateFormat.format(date1);
        
        return date;
//        System.out.println("Current Date : " + date);
    }
}


