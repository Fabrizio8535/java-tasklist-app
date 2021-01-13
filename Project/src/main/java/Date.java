/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author k1723548
 */

public class Date 
{
    private int day;
    private int month;
    private int year;

    
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    Date() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    @Override
    public String toString()
    {
       return this.day + "/" + this.month + "/" + this.year;
    }
    
    public String dayToString()
    {
       return ""+ this.day;
    }
    
    public String monthToString()
    {
       return ""+ this.month;
    }
    
    public String yearToString()
    {
       return ""+ this.year;
    }
    
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
