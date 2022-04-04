
import java.util.ListIterator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class TreeGUI {
    private TreeView<Person> displayTree;
    private Button changeRoot;
    private FamilyTree base;
    
    TreeGUI(FamilyTree base){
        this.base = base;
        displayTree = new TreeView<>();
        initNewTree(base.getRootPerson());
        changeRoot = new Button("Change to root");
    }
    
    TreeGUI(){
        displayTree = new TreeView<>();
        changeRoot = new Button("Change to root");
    }
    
    public GridPane setBaseGUI(){
        GridPane gridMgr = new GridPane();
        gridMgr.setPadding(new Insets(10,10,10,10));
        
        gridMgr.add(displayTree, 0, 0);
        
        gridMgr.add(changeRoot, 0, 2);
        
        return gridMgr;
    }

    public Button getChangeRoot(){
        return changeRoot;
    }
    
    public void initEmptyTree(){
        displayTree = new TreeView<>();
    }
    
    //dummy treeview
    private void initNewTree(Person rootPerson){
        initEmptyTree();
        base = new FamilyTree(rootPerson);
        
        
        TreeItem<Person> root = new TreeItem<>(rootPerson);
        
        root.setExpanded(true);
        displayTree.setRoot(root);
        
        
    }
    
    
    public void refreshTree(){
        initEmptyTree();
        TreeItem<Person> root = new TreeItem<>(base.getRootPerson());
        
        TreeItem<Person> pHeader = parentsHeader();
        TreeItem<Person> sHeader = spouseHeader();
        TreeItem<Person> cHeader = childrenHeader();
        TreeItem<Person> dad = null, mum = null, spouse = null, children = null;
        
        displayTree.setRoot(root);
        
        if(base.checkFatherExist()||base.checkMotherExist()){
            root.getChildren().add(pHeader);
            if(base.checkFatherExist()){
                dad = new TreeItem<>(base.getFather());
                pHeader.getChildren().add(dad);
            }
            if(base.checkMotherExist()){
                mum = new TreeItem<>(base.getMother());
                pHeader.getChildren().add(mum);
            }
        }
        
        if(base.getRootPerson().checkSpouseExist()){
            root.getChildren().add(sHeader);
            spouse = new TreeItem<>(base.getSpouse());
            sHeader.getChildren().add(spouse);
        }
        
        if(!(base.getRootPerson().getChildrenCount()==0)){
            root.getChildren().add(cHeader);
            ListIterator childItr = base.getRootPerson().getChildrenItr(0);
            while(childItr.hasNext()){
                TreeItem<Person> subSHeader = spouseHeader();
                TreeItem<Person> subCHeader = childrenHeader();
                
                Person childHolder = (Person)childItr.next();

                TreeItem<Person> child = new TreeItem<>(childHolder);
                cHeader.getChildren().add(child);
                if(childHolder.checkSpouseExist()){
                    child.getChildren().add(subSHeader);
                    spouse = new TreeItem<>(childHolder.getSpouse());
                    subSHeader.getChildren().add(spouse);
                }
                
                if(!(childHolder.getChildrenCount()==0)){
                    child.getChildren().add(subCHeader);
                    ListIterator grandChildItr = childHolder.getChildrenItr(0);
                    while(grandChildItr.hasNext()){
                        TreeItem<Person> grandChild = new TreeItem<>((Person)grandChildItr.next());
                        subCHeader.getChildren().add(grandChild);
                    }
                }

            }
        }
        
        
    }
    
    public void setFamilyTree(FamilyTree familyTree){
        base = new FamilyTree(familyTree);
    }
    
    public FamilyTree getFamilyTree(){
        return base;
    }
    
    public TreeView<Person> getTreeView(){
        return displayTree;
    }
    
    private TreeItem<Person> parentsHeader(){
        return new TreeItem<>(new Person("Parents"));
    }
    
    private TreeItem<Person> spouseHeader(){
        return new TreeItem<>(new Person("Spouse"));
    }
    
    private TreeItem<Person> childrenHeader(){
        return new TreeItem<>(new Person("Children"));
    }
   
}
