import java.io.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;     
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainApp extends Application{
    private FormGUI formInit;
    private TreeGUI treeInit;
    private AddPersonGUI popupInit;
    private FamilyTree familyTree;
    private BorderPane basePane;
    
    @Override
    public void start(Stage stage){
        
        stage.setTitle("TreeView Example");
        
        //defining overall flow
        basePane = new BorderPane();                
        Scene editScene = new Scene(basePane,750,750);
        
        //first scene(editing mode)
        //gets header 'Family Tree Application'
        VBox title = setTitle();
        
        //initializes button
        HBox buttonLayout = new HBox(10);
        buttonLayout.setPadding(new Insets(15,0,10,0));
        
        Button loadTree = new Button("Load a tree");
        Button saveTree = new Button("Save tree");
        Button createTree = new Button("Create new tree");
        
        //file choosers will be open for both of the buttons to either
        //load or save files
        loadTree.setOnAction(evt -> loadFile(stage));
        saveTree.setOnAction(evt -> saveFile(stage));
        
        //this will enable users to enter information of a person
        //and treated as rootperson by default in a new family tree
        createTree.setOnAction(evt -> createNewTree());
        
        //adds all the button into the hbox
        buttonLayout.getChildren().setAll(loadTree,saveTree,createTree);        
        
        //initializes a vbox to hold both the title and buttons
        VBox header = new VBox();
        header.getChildren().setAll(title,buttonLayout);
        
        //set the title and buttons to the top of the border pane
        basePane.setTop(header);
        
        //initializing an empty tree by default and also a button
        //see 'TreeGUI.java'
        treeInit = new TreeGUI();
        GridPane treeMgr = treeInit.setBaseGUI();
        treeInit.getTreeView().setOnMouseClicked(e -> showSelection(e));
        treeInit.getChangeRoot().setOnAction(e -> changeRootPerson());
        
        //sets the empty tree and button into the left of the border pane
        basePane.setLeft(treeMgr);
        
        //initializing a form that displays the basic information of a person
        //when necessary
        formInit = new FormGUI();
        GridPane formLay = formInit.completeGUI();
        formInit.lockButtons();
        
        //sets the appropriate button's action event here to incoporate 
        //information from other GUI classes
        formInit.getSaveButton().setOnAction(e->saveChanges());
        formInit.getAddRelativeButton().setOnAction(e->getAddPersonGUI());
        formInit.getCreateTree().setOnAction(e->{
                                                GridPane newTree = confirmNewCreation();
                                                if(newTree!=null){
                                                    basePane.setLeft(null);
                                                    basePane.setLeft(newTree);
                                                    
                                                }
                                            });
        
        //sets the entire form on the center of the base border pane
        basePane.setCenter(formLay);
        
        stage.setScene(editScene);
        stage.show();
    }
    
    //The title to the header
    public VBox setTitle(){
        Label headerMsg = new Label("Family Tree Application");
        headerMsg.setFont(new Font(20));
                
        VBox title = new VBox(5);
        title.getChildren().add(headerMsg);
        
        return title;
    }
    

    private void saveChanges(){
        if(formInit.saveChangesAction()){
            //family tree.updateSelectedPerson()
        }
    }
    
    private void getAddPersonGUI(){
        popupInit = new AddPersonGUI(treeInit.getFamilyTree().getRootPerson());
        popupInit.completeGUI();
        
        //refreshes tree gui
        treeInit.refreshTree();
        basePane.setLeft(null);
        basePane.setLeft(treeInit.setBaseGUI());
        treeInit.getTreeView().setOnMouseClicked(e -> showSelection(e));
        treeInit.getChangeRoot().setOnAction(e -> changeRootPerson());
        
    }
    
    private void loadFile(Stage stage){
        FileChooser chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().add(new ExtensionFilter("Binary File","*.dat"));
        File file = chooseFile.showOpenDialog(stage);
        
        if(file != null){
            getFileContent(file);
        }
    }
    
    private void saveFile(Stage stage){
        FileChooser saveFile = new FileChooser();
        saveFile.getExtensionFilters().add(new ExtensionFilter("Binary File","*.dat"));
        File file = saveFile.showSaveDialog(stage);
        
        if(file != null){
            setFileContent(file);
        }
    }
    
    private void setFileContent(File file){
        Alert alert = new Alert(AlertType.ERROR);
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file+".dat"));
            out.writeObject(familyTree);          
            
        }
        catch(FileNotFoundException e){
            alert.setTitle("File not found");
            alert.setHeaderText("Program cannot find the file specified");
            
            alert.showAndWait();
        }
        catch(IOException ex){
            alert.setTitle("I/O Error");
            alert.setHeaderText("Error occurred while reading from the file");
            
            alert.showAndWait();
        }
        
    }
    
    private void getFileContent(File file){
        Alert alert = new Alert(AlertType.ERROR);
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getPath()));
            familyTree = (FamilyTree)in.readObject();
            
            treeInit.setFamilyTree(familyTree);
            treeInit.refreshTree();
            basePane.setLeft(null);
            basePane.setLeft(treeInit.setBaseGUI());
            treeInit.getTreeView().setOnMouseClicked(e -> showSelection(e));
            treeInit.getChangeRoot().setOnAction(e -> changeRootPerson());
            formInit.unlockButtons();
        }
        catch(IOException ex){
            alert.setTitle("I/O Error");
            alert.setHeaderText("Error occurred while reading from the file");
            
            alert.showAndWait();
        }
        catch(ClassNotFoundException e){
            alert.setTitle("Class Error");
            alert.setHeaderText("Class File disappeared !?!?");
            
            alert.showAndWait();
        }
        catch(ClassCastException cce){
            alert.setTitle("Class Casting Error");
            alert.setHeaderText("Are u sure you imported the correct thing???");
            
            alert.showAndWait();
        }
            
            
    }
    
    private void createNewTree(){
        formInit.createTree();
    }
    
    private GridPane confirmNewCreation(){
        if(formInit.rootPersonVerify()){
            
            familyTree = new FamilyTree(formInit.getEditPerson());
            treeInit = new TreeGUI(familyTree);
            treeInit.getTreeView().setOnMouseClicked(e -> showSelection(e));
            treeInit.getChangeRoot().setOnAction(e -> changeRootPerson());
            return treeInit.setBaseGUI();
        }

        return null;        
    }
    
    private void showSelection(Event e){
        TreeView<Person> prog = (TreeView<Person>)e.getSource();
        TreeItem<Person> selectItem = prog.getSelectionModel().getSelectedItem();

        //if null just keep previous person's details showing
        if (selectItem == null){
            
        }
        
        else{
            //check if it is a header
            if(!familyTree.isHeader(selectItem.getValue())){
                familyTree.setSelectedPerson(selectItem.getValue());
                formInit.clearField();
                formInit.setEditPerson(selectItem.getValue());
                formInit.populateForm();
            }
        }

    }
    
    private void changeRootPerson(){
        familyTree.setRootPerson(familyTree.getSelectedPerson());
        
        treeInit.setFamilyTree(familyTree);
        treeInit.refreshTree();
        basePane.setLeft(null);
        basePane.setLeft(treeInit.setBaseGUI());
        treeInit.getTreeView().setOnMouseClicked(e -> showSelection(e));
        treeInit.getChangeRoot().setOnAction(e -> changeRootPerson());
        
    }
    
    public static void main(String[]argv){
        launch(argv);
    }
}
