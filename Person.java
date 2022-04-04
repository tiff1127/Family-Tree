//can only add child, spouse and parents



import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;


public class Person implements Serializable{
    
    //'L' for long data type instead of integer
    public static final long serialVersionUID = 373L;
    
    public static enum Gender{
        Male("Male"), Female("Female");
        
        private final String value;
        
        private Gender(String value){
            this.value = value;
        }
    }//process data here, no need to let gui know the enum
    
    
    private String firstName;
    private String lastName;
    private String maidenName;
    private Gender gender;
    private Address address;
    private String lifeDesc;
    
    private Person father;
    private Person mother;
    private Person spouse;
    
    private ArrayList<Person> children;
    
    Person(){
        children = new ArrayList<>();
    }
    
    
    Person(String first, String last, int gender,Address address,String lifeDesc){
        firstName = first;
        lastName = last;
        this.gender = Gender.values()[gender];
        this.address = address;
        this.lifeDesc = lifeDesc;
        children = new ArrayList<>();
    }
    
    Person(String first, String last, String maiden, int gender,Address address,String lifeDesc){
        firstName = first;
        lastName = last;
        maidenName = maiden;
        this.gender = Gender.values()[gender];
        this.address = address;
        this.lifeDesc = lifeDesc;
        children = new ArrayList<>();
    }
    
    Person(Person person){
        firstName = person.getFirstName();
        lastName = person.getLastName();
        gender = person.getGender();
        address = person.getAddress();
        lifeDesc = person.getLifeDesc();
        children = new ArrayList<>();
        
        if(person.getChildrenCount()!=0){
            ListIterator childItr= person.getChildrenItr(0);
            
            while(childItr.hasNext()){
                this.setChildren((Person)childItr.next());
            }
        }
        
    }
    
    Person(String header){
        firstName = header;
    }
    
    //Debug
    public void addAllChildren(ArrayList<Person> listOfPerson){
        for(int i = 0; i < listOfPerson.size();i++){
            children.add(listOfPerson.get(i));
        }
    }

    
    public String getFirstName() {
        return firstName;
    }

    public boolean setFirstName(String firstName) {
        if(checkEmpty(firstName)){
            return false;
        }
        this.firstName = firstName;
        return true;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean setLastName(String lastName) {
        if(checkEmpty(lastName)){
            return false;
        }
        
        this.lastName = lastName;
        return true;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean setGender(int gender) {
        if(gender == 0){
            this.gender = Gender.Male;
            return true;
        }
        else if(gender == 1){
            this.gender = Gender.Female;
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean setGender(Gender gender){
        if(checkGender(gender)){
            this.gender = gender;
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean checkGender(Gender gender){
        if(gender==null){
            return false;
        }
        
        if(Gender.Male.equals(gender)){
            return true;
        }
        else if(Gender.Female.equals(gender)){
            return true;
        }
        else{
            return false;
        }
    }
    
    /*public boolean checkGender(int gender){
        if(gender==0||gender){
        }
    }*/
    
    public String getMaidenName(){
        return maidenName;
    }
    
    //guys not allowed to have maiden name >:C
    public void setMaidenName(String maidenName){
        this.maidenName = maidenName;
    }

    
    public Address getAddress(){
        return address;
    }
    
    public void setAddress(Address address){
        //this.address.setAddress(address);
    }
    
    public String getLifeDesc(){
        return lifeDesc;
    }
    
    public void setLifeDesc(String lifeDesc){
        //no need to validate lifeDesc :D
        this.lifeDesc = lifeDesc;
    }
    
    public boolean checkEmpty(String input){
        if(input == null|| input.isEmpty()){
            return true;
        }
        
        return false;
    }
    
    public ListIterator getChildrenItr(int index){
        if(index<0||index>=getChildrenCount()){
            return null;
        }
        
        return children.listIterator(index);
    }
        
    public void setChildren(Person child){
        children.add(child);
    }
    
    public int getChildrenCount(){
        return children.size();
    }
    
    public int getGrandChildrenCount(){
        int total = 0;
        for(int i = 0; i<getChildrenCount();i++){
            total += children.get(i).getChildrenCount();
        }
        
        return total;
    }
        
    public boolean isValidMaidenName(String maidenName,String gender){
        if(gender.equalsIgnoreCase("Male")){
            if(checkEmpty(maidenName)){
                return true;
            }
        }
        
        if(gender.equalsIgnoreCase("Female")){
            if(!checkEmpty(maidenName)){
                    return true;
            }
        }
                    
        else{
            if(checkEmpty(maidenName)){
                return true;
            }
        }

        
        return false;
    }
    
    public boolean checkMotherExist(){
        if(mother!=null){
            return true;
        }
        
        return false;
    }
    
    public Person getMother(){
        return mother;
    }
    
    public void setMother(Person mother){
        this.mother = new Person(mother);
        
    }
            
    public Person getFather(){
        return father;
    }
    
    public void setFather(Person father){
        if(!checkFatherExist()){           
            this.father = new Person(father);
        }

    }
    
    public boolean checkFatherExist(){
        if(father!=null){
            return true;
        }
        
        return false;
    }
    
    public Person getSpouse(){
        return spouse;
    }
    
    public void setSpouse(Person spouse){
        spouse.addAllChildren(this.children);
        this.spouse = spouse;
    }
    
    public boolean checkSpouseExist(){
        if(spouse!=null){
            return true;
        }
        
        return false;
    }
    
    public ArrayList<String> getAllChildren(){
        
        ArrayList<String> childNames = new ArrayList();
        
        for(int i = 0;i<children.size();i++){
            Person holder = children.get(i);
            String temp = holder.getFirstName() + " " + holder.getLastName();
            childNames.add(temp);
        }
        
        return childNames;
    }
    
    public ArrayList<String> getAllGrandChildren(){
        ArrayList<String> grandChildNames = new ArrayList();
        
        for(int i = 0;i<children.size();i++){
            Person childHolder = children.get(i);
            
            if(childHolder.getChildrenCount()==0){
                ListIterator grandChildItr = childHolder.getChildrenItr(0);
                while(grandChildItr.hasNext()){
                    Person grandChildHolder = (Person)grandChildItr.next();
                    String temp = grandChildHolder.getFirstName() + " " + grandChildHolder.getLastName();
                    grandChildNames.add(temp);
                }
                
            }
                
        }
        
        return grandChildNames;
    }
      
    
    @Override
    public String toString(){
        String view = null;
        
        if (this.gender == Gender.Male){
            view = "♂ ";
        }
        else if (this.gender == Gender.Female){
            view = "♀ ";
        }
        //header person
        else{
            return firstName;
        }
        
        view += (firstName + " " + lastName);
        
        return view;     
    }
    
}
