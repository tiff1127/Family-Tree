
import java.io.Serializable;

public class Address implements Serializable{
    //'L' for long data type instead of integer
    public static final long serialVersionUID = 373L;
    
    private int streetNumber;
    private String streetName;
    private String suburb;
    private int postalCode;
    
    Address(){
        
    }
    
    Address(String num, String name, String suburb, String postal){
        streetNumber = Integer.parseInt(num);
        streetName = name;
        this.suburb = suburb;
        postalCode = Integer.parseInt(postal);
    }
    
    Address(int num, String name, String suburb, int postal){
        streetNumber = num;
        streetName = name;
        this.suburb = suburb;
        postalCode = postal;
    }
    
    Address(Address address){
        this.streetNumber = address.streetNumber;
        this.streetName = address.streetName;
        this.suburb = address.suburb;
        this.postalCode = address.postalCode;
    }
    
    public int getStreetNum(){
        return streetNumber;
    }

    public String getStreetName(){
        return streetName;
    }
    
    public String getSuburb(){
        return suburb;
    }
    
    public int getPostalCode(){
        return postalCode;
    }
    
    public boolean checkNumeric(String input){
        if(checkEmpty(input)){
           return false; 
        }
        
        String tempInput = input.trim();
        
        
        if(tempInput.matches("\\d+")){
            return true;
        }
        
        
        
        return false;
    }
    
    public boolean checkOnlyAlphabet(String input){
        if(checkEmpty(input)){
           return false; 
        }
        
        String tempInput = input.trim();
        
        if(tempInput.matches("^[a-zA-Z]*$")){
            return true;
        }
        
        return false;
    }
    
    private boolean checkEmpty(String input){        
        if(input == null || input.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
