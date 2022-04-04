
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class FormGUI extends BasicDetailGUI{    
    private TextField fatherField, motherField, spouseField;
    private TextField childField, grandChildField;
     
    private Button editDetail, addRelative, saveChanges, cancelChanges;    
    private Button createTree, cancelCreateTree;
    
    private GridPane formLayout;
    
    //person that is currently editing/the root person 
    //to add into a brand new family tree
    Person editPerson;
    
    //initiating the gui class
    //nothing is done
    FormGUI(){
        
    }
    
    
    //generating the full form gui
    public GridPane completeGUI(){
        formLayout = new GridPane();
        
        buildBaseGUI(formLayout);
        
        //all the basic info pane is locked by default
        makeUneditable();
        
        //other additional info cannot be altered
        //only can be changed by the system upon reflecting to the family tree
        Label relativeHeader = new Label("Relative info: ");
        relativeHeader.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 20));
        formLayout.add(relativeHeader, 0, 14);
        
        Label father = new Label("Father: ");
        formLayout.add(father, 0, 15);
        
        fatherField = new TextField();
        fatherField.setEditable(false);
        fatherField.setDisable(true);
        formLayout.add(fatherField, 1, 15);
        
        Label mother = new Label("Mother: ");
        formLayout.add(mother, 0, 16);
        
        motherField = new TextField();
        motherField.setEditable(false);
        motherField.setDisable(true);
        formLayout.add(motherField, 1, 16);
        
        Label spouse = new Label("Spouse: ");
        formLayout.add(spouse, 0, 17);
        
        spouseField = new TextField();
        spouseField.setEditable(false);
        spouseField.setDisable(true);
        formLayout.add(spouseField, 1, 17);
        
        Label child = new Label("Children: ");
        formLayout.add(child, 0, 18);
        
        childField = new TextField();
        childField.setEditable(false);
        childField.setDisable(true);
        formLayout.add(childField, 1, 18);
        
        Label grandChild = new Label("Grand Children: ");
        formLayout.add(grandChild, 0, 19);
        
        grandChildField = new TextField();
        grandChildField.setEditable(false);
        grandChildField.setDisable(true);
        formLayout.add(grandChildField, 1, 19);
        
        
        //this button will unlock the text fields to enable editing
        editDetail = new Button("Edit Details");
        editDetail.setOnAction(evt -> {
                                formLayout.getChildren().remove(editDetail);
                                formLayout.getChildren().remove(addRelative);
                                
                                formLayout.add(saveChanges,0 ,21);
                                formLayout.add(cancelChanges,1 ,21);
                                
                                makeEditable();
        });
        
        //add buttons to the GUI
        formLayout.add(editDetail,0 ,21);
        
        addRelative = new Button("Add Relative");
        formLayout.add(addRelative,1, 21);
        
        saveChanges = new Button("Save Changes");
        
        cancelChanges = new Button("Cancel Changes");
        cancelChanges.setOnAction(event -> cancelChangesAction());
        
        createTree = new Button("Create Tree");
        //createTree.setOnAction(event -> createTree());
        
        cancelCreateTree = new Button("Cancel Creation");
        cancelCreateTree.setOnAction(event -> cancelCreatingTree());
        
        return formLayout;
    }
    
    public Button getSaveButton(){
        return saveChanges;
    }
    
    public Button getAddRelativeButton(){
        return addRelative;
    }
    
    public Button getCreateTree(){
        return createTree;
    }

    public void lockButtons(){
        addRelative.setDisable(true);
        editDetail.setDisable(true);
    }
    
    public void unlockButtons(){
        addRelative.setDisable(false);
        editDetail.setDisable(false);
    }
    
    
    public boolean saveChangesAction(){
        boolean validated = validateDetails();
                                
        if(validated){
            formLayout.getChildren().removeAll(saveChanges,cancelChanges);
                                    
            formLayout.add(editDetail,0 ,21);
            formLayout.add(addRelative,1 ,21);
            editPerson = saveChanges();
            return true;
        }
        
        else{
            formLayout.getChildren().remove(getErrorMsg());
            formLayout.add(getErrorMsg(), 0, 20);
            return false;
        }
    }
    
    public void cancelChangesAction(){
        removeBgColor();
        //reverts, should be the person that is chosen that passes in
        populateForm();
        
        //removes buttons in editing mode and error message if it exists
        formLayout.getChildren().remove(saveChanges);
        formLayout.getChildren().remove(cancelChanges);
        formLayout.getChildren().remove(getErrorMsg());
        
        //adds in the button 
        formLayout.add(editDetail,0 , 21);
        formLayout.add(addRelative,1 ,21);
            
        makeUneditable();
    }
    
    private void makeEditable(){
        getFirstNameTF().setEditable(true);
        getFirstNameTF().setDisable(false);
        
        getLastNameTF().setEditable(true);
        getLastNameTF().setDisable(false);
        
        getMaidenNameTF().setEditable(true);
        getMaidenNameTF().setDisable(false);
        
        getGenderCB().setEditable(true);
        getGenderCB().setDisable(false);
        
        getLifeDescTA().setEditable(true);
        getLifeDescTA().setDisable(false);
        
        getStreetNumTF().setEditable(true);
        getStreetNumTF().setDisable(false);
        
        getStreetNameTF().setEditable(true);
        getStreetNameTF().setDisable(false);
        
        getSuburbTF().setEditable(true);
        getSuburbTF().setDisable(false);
        
        getPostalTF().setEditable(true);
        getPostalTF().setDisable(false);

        
    }
    
    private void makeUneditable(){
        getFirstNameTF().setEditable(false);
        getFirstNameTF().setDisable(true);
        
        getLastNameTF().setEditable(false);
        getLastNameTF().setDisable(true);
        
        getMaidenNameTF().setEditable(false);
        getMaidenNameTF().setDisable(true);
        
        getGenderCB().setEditable(false);
        getGenderCB().setDisable(true);
        
        getLifeDescTA().setEditable(false);
        getLifeDescTA().setDisable(true);
        
        getStreetNumTF().setEditable(false);
        getStreetNumTF().setDisable(true);
        
        getStreetNameTF().setEditable(false);
        getStreetNameTF().setDisable(true);
        
        getSuburbTF().setEditable(false);
        getSuburbTF().setDisable(true);
        
        getPostalTF().setEditable(false);
        getPostalTF().setDisable(true);
    }
    
    public void clearField(){
        getFirstNameTF().clear();
        
        getLastNameTF().clear();
        
        getMaidenNameTF().clear();
        
        getGenderCB().valueProperty().set(null);
        
        getLifeDescTA().clear();
        
        getStreetNumTF().clear();
               
        getStreetNameTF().clear();
        
        getSuburbTF().clear();

        getPostalTF().clear();
        
        fatherField.clear();
        motherField.clear();
        spouseField.clear();
        childField.clear();
        grandChildField.clear();
    }
    
    public Person saveChanges(){   
        makeUneditable();
        return super.saveDetails();

    }
    
    public void populateForm(){
        if(editPerson==null){
            
        }
        else{
            getFirstNameTF().setText(editPerson.getFirstName());
            getLastNameTF().setText(editPerson.getLastName());
            getMaidenNameTF().setText(editPerson.getMaidenName());

            if(editPerson.getGender().name().equalsIgnoreCase("Male")){
                getGenderCB().setValue("Male");
            }
            else{
                getGenderCB().setValue("Female");
            }

            getLifeDescTA().setText(editPerson.getLifeDesc());

            String tempStreetNum = Integer.toString(editPerson.getAddress().getStreetNum());
            String tempPostal = Integer.toString(editPerson.getAddress().getPostalCode());

            getStreetNumTF().setText(tempStreetNum);
            getStreetNameTF().setText(editPerson.getAddress().getStreetName());
            getSuburbTF().setText(editPerson.getAddress().getSuburb());
            getPostalTF().setText(tempPostal);
            
            populateRelativeInfo();
        }
       
    }
    
    public void createTree(){
        
        //clear treeview
        clearField();
        makeEditable();
        formLayout.getChildren().removeAll(editDetail,addRelative);
        formLayout.add(createTree, 0, 21);
        formLayout.add(cancelCreateTree, 1, 21);
        
    }
    
    public boolean rootPersonVerify(){
        
        boolean validated = validateDetails();
                                
        if(validated){
            formLayout.getChildren().removeAll(createTree,cancelCreateTree);
            formLayout.getChildren().remove(getErrorMsg());
                                    
            formLayout.add(editDetail,0 , 21);
            formLayout.add(addRelative,1 ,21);
            //edit person will be set as root in this case
            editPerson = saveChanges();
            unlockButtons();
            return true;
        }
        
        else{
            //in case there are any existing error message exist
            formLayout.getChildren().remove(getErrorMsg());
            formLayout.add(getErrorMsg(), 0, 20);
            return false;
        }
        
    }
    
    public void cancelCreatingTree(){
        clearField();
        removeBgColor();
        makeUneditable();
        formLayout.getChildren().removeAll(createTree,cancelCreateTree);
        formLayout.getChildren().remove(getErrorMsg());
        
        formLayout.add(editDetail,0 , 21);
        formLayout.add(addRelative,1 ,21);
    }
    
    public void setEditPerson(Person person){
        editPerson = person;
    }
    public Person getEditPerson(){
        return editPerson;
    }
    
    public boolean validateDetails(){
        int flag = 1;
        
        if(!super.validateDetails()){
            flag = 0;
        }
        
        Person checkPerson = new Person();
        
        String genderSelect = (String)getGenderCB().getValue();
        
        if(genderSelect.equalsIgnoreCase("Male")){
            if(!(checkPerson.checkEmpty(getMaidenNameTF().getText()))){
                flag = 0;
                getMaidenNameTF().setStyle("-fx-control-inner-background: #FF6666;");
            }
            else{
                getMaidenNameTF().setStyle("");
            }
        }
        
        else if(genderSelect.equalsIgnoreCase("Female")){
            getMaidenNameTF().setStyle("");
        }
        else{
            flag = 0;
            getGenderCB().setStyle("-fx-control-inner-background: #FF6666;");
        }
        
        if(flag == 0){
            return false;
        }
        else{
            return true;
        }
    }
    
    public void populateRelativeInfo(){
        String comma = ", ";
        if(editPerson==null){
            
        }
        else{
            if(editPerson.checkFatherExist()){
                fatherField.setText(editPerson.getFather().toString());
            }
            
            if(editPerson.checkMotherExist()){
                motherField.setText(editPerson.getMother().toString());
            }
            
            if(editPerson.checkSpouseExist()){
                spouseField.setText(editPerson.getSpouse().toString());
            }
            
            if(!(editPerson.getChildrenCount()==0)){
                ArrayList<String> allChildren = editPerson.getAllChildren();
                String children = allChildren.get(0).toString();
                
                
                for(int childCount = 1;childCount<allChildren.size();childCount++){
                    children += comma + allChildren.get(childCount).toString();
                }
                
                childField.setText(children);
            }
            
            if(!(editPerson.getGrandChildrenCount()==0)){
                ArrayList<String> allGrandChildren = editPerson.getAllGrandChildren();
                String grandChildren = allGrandChildren.get(0).toString();
                
                for(int grandChildCount = 1 ; grandChildCount<allGrandChildren.size() ; grandChildCount++){
                    grandChildren += comma + allGrandChildren.get(grandChildCount).toString();
                }
                
                grandChildField.setText(grandChildren);
            }
        }
    }
              
}
