
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.TreeItem;

public class FamilyTree implements Serializable{
    //'L' for long data type instead of integer
    public static final long serialVersionUID = 373L;
    
    private Person rootPerson;
    private Person selectedPerson;
    
    FamilyTree(){
       
    }
    
    FamilyTree(Person rootPerson){
        this.rootPerson = rootPerson;
    }
    
    FamilyTree(FamilyTree familyTree){
        //selected person is just for editing purposes
        this.rootPerson = familyTree.getRootPerson();
    }
    
    public void setRootPerson(Person rootPerson){
        this.rootPerson = rootPerson;
    }
        
    public Person getRootPerson(){
        return rootPerson;
    }
    
    public boolean isHeader(Person person){
        if(person.checkGender(person.getGender())){
            return false;
        }
        else{
            return true;
        }
    }
    
    public void addFamilyMember(Person person){
        
    }
    
    public boolean checkMotherExist(){
        return rootPerson.checkMotherExist();
    }
    
    public boolean checkFatherExist(){
        return rootPerson.checkFatherExist();
    }
    
    public Person getFather(){
        return rootPerson.getFather();
    }
    
    public Person getMother(){
        return rootPerson.getMother();
    }
    
    public Person getSpouse(){
        return rootPerson.getSpouse();
    }
   
    public void setSelectedPerson(Person person){
        this.selectedPerson = person;
    }
    
    public Person getSelectedPerson(){
        return selectedPerson;
    }
    
    
}
