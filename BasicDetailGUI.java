
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class BasicDetailGUI {
    
    private TextField firstNameField, lastNameField, maidenNameField;
    private TextField streetNumField, streetNameField, suburbField, postalField;
    private ComboBox genderField;
    private TextArea lifeDescField;
    
    private Label personalHeader, addressHeader;
    private Label firstName, lastName, maidenName;
    private Label streetNum, streetName, suburb, postal;
    private Label gender;
    private Label lifeDesc;
    
    private Text errorMsg;
   

    BasicDetailGUI(){
        
    }
    
    
    //getting the base GUI in a gridpane that is passed in
    public void buildBaseGUI(GridPane aLayout){
        aLayout.setHgap(10);
        aLayout.setVgap(5);
        aLayout.setPadding(new Insets(10,10,10,10));
        
        errorMsg = new Text("Field(s) are not complete");
        
        personalHeader = new Label("Person info: ");
        personalHeader.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 20));
        aLayout.add(personalHeader, 0 ,1);
        
        
        firstName = new Label("First Name: ");
        aLayout.add(firstName, 0, 2);
        
        firstNameField = new TextField();
        firstNameField.setPromptText("George");
        aLayout.add(firstNameField, 1, 2);
        
        
        lastName = new Label("Last Name: ");
        aLayout.add(lastName, 0, 3);
        
        lastNameField = new TextField();
        lastNameField.setPromptText("Anderson");
        aLayout.add(lastNameField, 1, 3);
        
        maidenName = new Label("Maiden Name: ");
        aLayout.add(maidenName, 0, 4);
        
        maidenNameField = new TextField();
        maidenNameField.setPromptText("Hendson (If Male, just leave empty)");
        aLayout.add(maidenNameField, 1, 4);
        
        final String genderType[] = {"Male","Female"};
        
        gender = new Label("Gender");
        aLayout.add(gender, 0, 5);
        
        genderField = new ComboBox(FXCollections.observableArrayList(genderType));
        aLayout.add(genderField, 1, 5);
        
        lifeDesc = new Label("Describe Yourself: ");
        aLayout.add(lifeDesc, 0, 6);
        
        lifeDescField = new TextArea();
        lifeDescField.setPrefColumnCount(20);
        lifeDescField.setPromptText("About yourself");
        aLayout.add(lifeDescField, 1, 6);
        
        addressHeader = new Label("Address info: ");
        addressHeader.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 20));
        aLayout.add(addressHeader, 0, 8);
        
        streetNum = new Label("Street Number: ");
        aLayout.add(streetNum, 0, 9);
        
        streetNumField = new TextField();
        streetNumField.setPromptText("Eg. 12");
        aLayout.add(streetNumField, 1, 9);
        
        streetName = new Label("Street Name: ");
        aLayout.add(streetName, 0, 10);
        
        streetNameField = new TextField();
        streetNameField.setPromptText("Eg. Hill");
        aLayout.add(streetNameField, 1, 10);
        
        suburb = new Label("Suburb: ");
        aLayout.add(suburb, 0, 11);
        
        suburbField = new TextField();
        suburbField.setPromptText("Eg. Texas");
        aLayout.add(suburbField, 1, 11);
        
        postal = new Label("Postal Code: ");
        aLayout.add(postal, 0, 12);
        
        postalField = new TextField();
        postalField.setPromptText("Eg. 82213");
        aLayout.add(postalField, 1, 12);
        
    }
    
    public boolean validateDetails(){
        //dummy objects to use the method inside
        Person checkPerson = new Person();
        Address checkAddress = new Address();
        int flag = 1;
        
        
        //reverts back to white if valid
        if(checkPerson.checkEmpty(firstNameField.getText())){
            firstNameField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            firstNameField.setStyle("");
        }
        
        if(checkPerson.checkEmpty(lastNameField.getText())){
            lastNameField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            lastNameField.setStyle("");
        }
        
        //gender and maiden name checks will be performed at respective childs
        //life description is not necessary
        
        if(!checkAddress.checkNumeric(streetNumField.getText())){
            streetNumField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            streetNumField.setStyle("");
        }
        
        if(!checkAddress.checkOnlyAlphabet(streetNameField.getText())){
            streetNameField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            streetNameField.setStyle("");
        }
        
        if(!checkAddress.checkOnlyAlphabet(suburbField.getText())){
            suburbField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            suburbField.setStyle("");
        }
        
        if(!checkAddress.checkNumeric(postalField.getText())){
            postalField.setStyle("-fx-control-inner-background: #FF6666;");
            flag = 0;
        }
        else{
            postalField.setStyle("");
        }
        
        if(flag == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void removeBgColor(){
        firstNameField.setStyle("");
        lastNameField.setStyle("");
        maidenNameField.setStyle("");
        genderField.setStyle("");
        lifeDescField.setStyle("");
        streetNumField.setStyle("");
        streetNameField.setStyle("");
        suburbField.setStyle("");
        postalField.setStyle("");    
        
    }
    
    public Person saveDetails(){
        Person save = new Person(firstNameField.getText(),lastNameField.getText(),genderField.getSelectionModel().getSelectedIndex()
                            ,new Address(streetNumField.getText(),streetNameField.getText(),suburbField.getText(),postalField.getText()),
                            lifeDescField.getText());
        if(genderField.getValue().toString().equalsIgnoreCase("Female")&& save.checkSpouseExist()==true){
            save.setMaidenName(maidenNameField.getText());
        }
        
        return save;
       
    }
    
    
    public TextField getFirstNameTF(){
        return firstNameField;
    }
    
    public TextField getLastNameTF(){
        return lastNameField;
    }
    
    public TextField getMaidenNameTF(){
        return maidenNameField;
    }
    
    public ComboBox getGenderCB(){
        return genderField;
    }
    
    public TextArea getLifeDescTA(){
        return lifeDescField;
    }
    
    public TextField getStreetNumTF(){
        return streetNumField;
    }
    
    public TextField getStreetNameTF(){
        return streetNameField;
    }
    
    public TextField getSuburbTF(){
        return suburbField;
    }
    
    public TextField getPostalTF(){
        return postalField;
    }
    
    public Text getErrorMsg(){
        return errorMsg;
    }
    
}
