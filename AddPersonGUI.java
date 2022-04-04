
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;

public class AddPersonGUI extends BasicDetailGUI{

    private ComboBox relationField;
    
    private Label relation;
    
    private Button saveRelative, cancelButton;
        
    private final String relationType[] = {"Father","Mother","Spouse","Children"};
    
    //straight away add in here :3
    private Person rootPerson;
    
    AddPersonGUI(Person rootPerson){
        
        this.rootPerson = rootPerson;
        
    }
    
    public void completeGUI(){
        
        Stage popup = new Stage();       
        GridPane formLayout = new GridPane();
        Scene scene = new Scene(formLayout, 500, 500);
        
        popup.setTitle("Adding a relative...");
        popup.initModality(Modality.APPLICATION_MODAL);
        
        
        buildBaseGUI(formLayout);
        

        //for addperson gui exclusive
        relation = new Label("Relation :");
        formLayout.add(relation, 0, 13);
        
        
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(relationType));
        if(rootPerson.checkFatherExist()){
            list.remove("Father");
        }
        
        if(rootPerson.checkMotherExist()){
            list.remove("Mother");
        }
        
        if(rootPerson.checkSpouseExist()){
            list.remove("Spouse");
        }
        
        String leftRelation[] = list.toArray(new String[0]);
        
        //if(root)
        
        relationField = new ComboBox(FXCollections.observableArrayList(leftRelation));
        formLayout.add(relationField, 1, 13);
        
        saveRelative = new Button("Save relative");
        saveRelative.setOnAction(evt -> {
            boolean validated = validateDetails();
            
            if(validated){
                Person personToSave = super.saveDetails();
               
                addPerson(personToSave);

                popup.close();
            }
            
            else{
                formLayout.getChildren().remove(getErrorMsg());
                formLayout.add(getErrorMsg(), 0, 14);
            }
            
        });
                
        formLayout.add(saveRelative, 0, 15);
        
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(evt -> popup.close());
        formLayout.add(cancelButton, 1, 15);
        
        
        popup.setScene(scene);
        popup.showAndWait();
        
    }
  
    private void addPerson(Person person){
        String selection = (String)relationField.getValue();
        
        if(selection.equals("Father")){
            //set rootperson as children
            person.setChildren(rootPerson);
            
            //set person as mother's spouse if exist
            if(rootPerson.checkMotherExist()){
                rootPerson.getMother().setSpouse(person);
            }
            
            //set father relationship with rootperson
            rootPerson.setFather(person);
            
        }
        
        if(selection.equals("Mother")){
            //set root person as children
            person.setChildren(rootPerson);
            
            //set person as father's spouse if exist
            if(rootPerson.checkFatherExist()){
                rootPerson.getFather().setSpouse(person);
            }
            
            //set mother relationship with rootperson
            rootPerson.setMother(person);
        }
        
        if(selection.equals("Spouse")){
            //sets spouse for both of them
            person.setSpouse(rootPerson);
            rootPerson.setSpouse(person);
        }
        
        if(selection.equals("Children")){
            //sets children for spouse as well if exist
            if(rootPerson.checkSpouseExist()){
                rootPerson.getSpouse().setChildren(person);
            }
            
            //set children for root person
            rootPerson.setChildren(person);
        }
        
    }
    
    public Person getChangedPerson(){
        return rootPerson;
    }
    
    public boolean validateDetails(){
        int flag = 1;
        int genderFlag = 1;
        
        Person checkPerson = new Person();
        
        if(!super.validateDetails()){
            flag = 0;
        }
        String relationSelect = (String)relationField.getValue();
        String genderSelect = (String)getGenderCB().getValue();
        
        if(checkPerson.checkEmpty(genderSelect)){
            genderFlag=0;
        }
        if(!checkPerson.isValidMaidenName(getMaidenNameTF().getText(),genderSelect)){
            getMaidenNameTF().setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            getMaidenNameTF().setStyle("");
        }
            
        if(genderFlag==1){
            if(checkPerson.checkEmpty(relationSelect)){
                    relationField.setStyle("-fx-control-inner-background: #FF6666;");
                    flag = 0;
            }
            else{
                if(relationSelect.equals("Father")){
                    if(genderSelect.equals("Female")){
                        genderFlag = 0;
                    }
                }
                else if(relationSelect.equals("Mother")){
                    if(genderSelect.equals("Male")){
                        genderFlag = 0;
                    }
                }
                else if(relationSelect.equals("Spouse")){
                    if(rootPerson.getGender().toString().equals(genderSelect)){
                        genderFlag = 0;
                    }
                }
            }
        }
        
        if(genderFlag == 0){
            getGenderCB().setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            getGenderCB().setStyle("");
        }
        
        if(flag == 1){
            return true;
        }
        
        return false;
    }
}
