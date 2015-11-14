package wdk.gui;

import javafx.collections.ObservableList;
import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;

/**
 * This is the team dialog. This window will open
 * when user wants to add A PLAYER
 * @author jingyu
 */
public class PlayerEditDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player player; 
    ObservableList<Team> teams;
    Draft draft;
    // GUI CONTROLS FOR OUR DIALOG
   
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    ImageView faceView;
    VBox personInfo;
    ImageView nationView;    
    Label fullNameLabel;
    Label qpLabel;
    Label fantasyTeamLabel;
    ComboBox fantasyTeamComboBox;
    Label positionLabel;
   ComboBox positionComboBox;
    Label contractLabel;
    ComboBox contractComboBox;
    Label salaryLabel;
    TextField salaryTextField;
   
    HBox decisionButton;
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    //check if image exist or not
//    Boolean imageNotExisted;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";  
    public static final String CANCEL = "Cancel";
    public static final String FANTASY_TEAM_PROMPT = "Fantasy Team: ";
    public static final String POSITION_PROMPT = "Position: ";
    public static final String CONTRACT_PROMPT = "Contract: ";
    public static final String SALARY_PROMPT = "Salary($): ";
    public static final String PLAYER_HEADING = "Player Details";    
    public static final String EDIT_PLAYER_TITLE = "Edit Player";
    public static final String PATH_FLAGS = "./images/flags/";
    public static final String PATH_FACE_IMAGE = "./images/players/";
 
  

    /**
     * Initializes this dialog so that it can be used for ADDING A PLAYER
     * 
     * @param primaryStage The owner of this modal dialog.
     * @param 
     * @param messageDialog
     */
    public PlayerEditDialog(Stage primaryStage, Draft initDraft,  MessageDialog messageDialog) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
       
        //set boolean value
    //    imageNotExisted=true;
     //get all created teams
        teams=initDraft.getTeams();       
        draft=initDraft;
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        
        //now the player face images
        faceView = new ImageView();
        //now for the personInfo
        personInfo = new VBox(10);
        nationView = new ImageView();        
        fullNameLabel = new Label();
        fullNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL); 
        qpLabel = new Label();
        qpLabel.getStyleClass().add(CLASS_PROMPT_LABEL); 
        personInfo.getChildren().addAll(nationView,fullNameLabel,qpLabel);
        
        // NOW THE fantasy Team
        fantasyTeamLabel = new Label(FANTASY_TEAM_PROMPT);
        fantasyTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        fantasyTeamComboBox = new ComboBox();
       
        fantasyTeamComboBox.setOnAction(e ->{
            player.setAssignedTeam((String) fantasyTeamComboBox.getSelectionModel().getSelectedItem());
            updatePositionComboBox();
        });
        
       
        //And the position
        positionLabel = new Label(POSITION_PROMPT);
        positionLabel.getStyleClass().add(CLASS_PROMPT_LABEL);    
        positionComboBox = new ComboBox();
        
        positionComboBox.setOnAction(e -> {            
            player.setAssignedPosition((String)positionComboBox.getSelectionModel().getSelectedItem());
        });
        //And the contract
        contractLabel = new Label(CONTRACT_PROMPT);
        contractLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
         contractComboBox = new ComboBox();
       //  contractComboBox.getItems().addAll("S2","S1","X");  
      //   contractComboBox.setValue("S2");
         contractComboBox.setOnAction(e ->{
            player.setContract((String)(contractComboBox.getSelectionModel().getSelectedItem()));
        });
        
        // AND THE salary
        salaryLabel = new Label(SALARY_PROMPT);
        salaryLabel.getStyleClass().add(CLASS_PROMPT_LABEL); 
       salaryTextField = new TextField();
        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
              if(newValue.equals(""))
                  player.setSalary(0);
              else
                player.setSalary(Integer.parseInt(newValue));
       });
                        
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerEditDialog.this.selection = sourceButton.getText();
            PlayerEditDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(faceView, 0, 1, 1, 1);
        gridPane.add(personInfo, 1, 1, 1, 1);
        gridPane.add(fantasyTeamLabel, 0, 2, 1, 1);
        gridPane.add(fantasyTeamComboBox, 1, 2, 1, 1);
        gridPane.add(positionLabel, 0, 3, 1, 1);      
        gridPane.add(positionComboBox, 1, 3, 1, 1);
        gridPane.add(contractLabel, 0 ,4, 1, 1);
        gridPane.add(contractComboBox, 1, 4, 1, 1);
        gridPane.add(salaryLabel, 0, 5, 1, 1);
        gridPane.add(salaryTextField,1,5,1,1);
        
               
        decisionButton = new HBox();
        decisionButton.getChildren().add(completeButton);      
        decisionButton.getChildren().add(cancelButton);
       
        gridPane.add(decisionButton, 1,6,1,1);
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    /**
     *This is used to get a player
     * @return Player
     */
    public Player getPlayer() { 
        return player;
    }
    
   
    /**
     *This is used to edit a Player
     * @param itemToEdit
     */
    public void showEditPlayerDialog(Player itemToEdit) {
        // SET THE DIALOG TITILE
        setTitle(EDIT_PLAYER_TITLE);
        
        // LOAD THE player ITEM INTO OUR LOCAL OBJECT
        player = new Player();        
        player.setNationOfBirth(itemToEdit.getNationOfBirth());
        player.setQualifyPositions(itemToEdit.getQualifyPositions());
        player.setFirstName(itemToEdit.getFirstName());
        player.setLastName(itemToEdit.getLastName());
       //player.setAssignedPosition(itemToEdit.getAssignedPosition()); 
        //player.setAssignedPosition("NONE");     
        //set the gui value to default value
     
        //In order to total clear comboBox and re-assignment value to it
        //I need valueProperty().set(null) and getItems().clear()
        //both function is enough
        fantasyTeamComboBox.valueProperty().set(null);
       // fantasyTeamComboBox.getSelectionModel().clearSelection();
        fantasyTeamComboBox.getItems().clear();
            fantasyTeamComboBox.getItems().add("Free Agent");
         for(int z=0; z<teams.size();z++){
            fantasyTeamComboBox.getItems().add(teams.get(z).getTeamName());
        }
         positionComboBox.valueProperty().set(null);
        positionComboBox.getSelectionModel().clearSelection();
        positionComboBox.getItems().clear();
        positionComboBoxSetItems(player.getQualifyPositions());
        contractComboBox.valueProperty().set(null);
        contractComboBox.getSelectionModel().clearSelection();
        contractComboBox.getItems().clear();
         contractComboBox.getItems().addAll("S2","S1","X"); 
        fantasyTeamComboBox.getSelectionModel().selectFirst();
        positionComboBox.getSelectionModel().selectFirst();       
        contractComboBox.getSelectionModel().selectFirst();
        salaryTextField.setText("");
        
        // AND THEN INTO OUR GUI
        loadGUIData();               
        // AND OPEN IT UP
        this.showAndWait();
    }
    
    /**
     *This is used to check the complete button is selected or not
     * @return a boolean value
     */
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
         
    /**
     *This is used to load GUI data
     * 
     */
    public void loadGUIData() {
        // LOAD THE UI STUFF    
        String lastFirstName=player.getLastName()+player.getFirstName();
    //    imageNotExisted=true;
    //    for(int i=0;i<draft.getDataBase().size();i++){
     //       if(draft.getDataBase().get(i).getFirstName().equals(player.getFirstName())
      //        && draft.getDataBase().get(i).getLastName().equals(player.getLastName()))
      //                    imageNotExisted=false;
     //   }
   //     if(imageNotExisted)
    //     faceView.setImage(new Image("file:" + PATH_FACE_IMAGE + "AAA_PhotoMissing.jpg"));
  //      else
            faceView.setImage(getFaceImage(lastFirstName)); 
        nationView.setImage(getFlag(player.getNationOfBirth()));
        fullNameLabel.setText(player.getFirstName()+"  "+player.getLastName());
        qpLabel.setText(player.getQualifyPositions());       
          
    }
    //use to find the source of the flag
    private Image getFlag(String nation){
        String imagePath  = "file:" + PATH_FLAGS + nation+".png";
        Image img = new Image(imagePath);
        return img;
    }
    //use to find the face image of players
    private Image getFaceImage(String fullName){
         String imagePath  = "file:" + PATH_FACE_IMAGE + fullName+".jpg";
        Image img = new Image(imagePath);
        return img;
    }
    
    private void positionComboBoxSetItems(String qp){
          String sign="_";
            String[] tokens = qp.split(sign);
                for(int j=0; j<tokens.length;j++){
                    positionComboBox.getItems().add(tokens[j]);
                    }
                  positionComboBox.getSelectionModel().selectFirst(); 
    }
    //Assign the correct values to position comboBox.
    private void updatePositionComboBox(){
         positionComboBox.valueProperty().set(null);
        positionComboBox.getSelectionModel().clearSelection();
        positionComboBox.getItems().clear();
        if(((String) fantasyTeamComboBox.getSelectionModel().getSelectedItem()).equals("Free Agent")){
            positionComboBoxSetItems(player.getQualifyPositions());
        }
        else{
            Team team=draft.findTeam((String)fantasyTeamComboBox.getSelectionModel().getSelectedItem());
               assignedValueToComboBox(team,player.getQualifyPositions());
        }
    }
    //Update the correct comboBox value to the position comboBox based on the 
    //positions limitations
    private void assignedValueToComboBox(Team team,String qp){
          String sign="_";
            String[] tokens = qp.split(sign);
                for(int j=0; j<tokens.length;j++){
                    if(tokens[j].equals("1B")&&team.get1BFull()==true){                        
                    }
                    else if (tokens[j].equals("3B")&&team.get3BFull()==true){                        
                    }
                    else if (tokens[j].equals("C")&&team.getCounterC()>=2){                        
                    }
                     else if (tokens[j].equals("OF")&&team.getCounterOF()>=5){                        
                    }
                     else if (tokens[j].equals("P")&&team.getCounterP()>=9){                        
                    }                    
                     else if (tokens[j].equals("CI")&&team.getCIFull()==true){                        
                    }
                     else if (tokens[j].equals("2B")&&team.get2BFull()==true){                        
                    }
                     else if (tokens[j].equals("MI")&&team.getMIFull()==true){                        
                    }
                     else if (tokens[j].equals("SS")&&team.getSSFull()==true){                        
                    }                  
                     else if (tokens[j].equals("U")&&team.getUFull()==true){                        
                    }                                                                              
                    else{
                    positionComboBox.getItems().add(tokens[j]);
                    }
                    
                    }
                  positionComboBox.getSelectionModel().selectFirst(); 
                  if(positionComboBox.getSelectionModel().isEmpty())
                      player.setAssignedPosition("NONE");
    }
}