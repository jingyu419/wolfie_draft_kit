package wdk.gui;


import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Player;

/**
 * This is the team dialog. This window will open
 * when user wants to add A PLAYER
 * @author jingyu
 */
public class PlayerDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Player player;   
    // GUI CONTROLS FOR OUR DIALOG
    VBox playerPane;
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    
    HBox positionsCheckBox;
    CheckBox checkBoxC;
    CheckBox checkBox1B;;
    CheckBox checkBox3B;
    CheckBox checkBox2B;  
    CheckBox checkBoxSS;
    CheckBox checkBoxOF;
    CheckBox checkBoxP;
   
    HBox decisionButton;
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";  
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name: ";
    public static final String PRO_TEAM_PROMPT = "Pro Team: ";
    public static final String PLAYER_HEADING = "Player Details";    
    public static final String ADD_PLAYER_TITLE = "Add New Player";
    public static final String CHECKBOX_C="C";
    public static final String CHECKBOX_1B="1B";
    public static final String CHECKBOX_2B="2B";
    public static final String CHECKBOX_3B="3B";
    public static final String CHECKBOX_SS="SS";
    public static final String CHECKBOX_OF="OF";
    public static final String CHECKBOX_P ="P";
    
    //these boolean values are used to check we have CI,MI,P in the qualifyingPositions or not
        static   Boolean hasCI=false;
        static   Boolean hasMI=false;
        static   Boolean hasU=false;
    
    
   
    /**
     * Initializes this dialog so that it can be used for ADDING A PLAYER
     * 
     * @param primaryStage The owner of this modal dialog.
     * @param 
     * @param messageDialog
     */
    public PlayerDialog(Stage primaryStage, Draft draft,  MessageDialog messageDialog) {       
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
       
    
        // FIRST OUR CONTAINER
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE first NAME 
        firstNameLabel = new Label(FIRST_NAME_PROMPT);
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        firstNameTextField = new TextField();
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                player.setFirstName(newValue);
        });
                
        // AND THE last name
        lastNameLabel = new Label(LAST_NAME_PROMPT);
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameTextField = new TextField();
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                player.setLastName(newValue);
        });
        
        //And the pro team
        proTeamLabel = new Label(PRO_TEAM_PROMPT);
        proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        proTeamComboBox = new ComboBox();
        proTeamComboBox.getItems().addAll("ATL","AZ","CHC","CIN",
        "COL","LAD","MIA","MIL","MYM","PHI","PIT","SD","SF","STL","WAS");
        //proTeamComboBox.setValue("ATL");
        proTeamComboBox.setOnAction(e ->{
            player.setProTeam((String)(proTeamComboBox.getSelectionModel().getSelectedItem()));
        });
        
        //Now the checkBox
        positionsCheckBox=new HBox(1);
        positionsCheckBox.getChildren().add(new Label("  "));
        checkBoxC = initChildCheckBox(positionsCheckBox,CHECKBOX_C);  
        
        checkBoxC.setOnAction(e ->{
             if(checkBoxC.isSelected())                  
              player.setQualifyPositions(player.getQualifyPositions()+"_C");           
        });
      
        checkBox1B = initChildCheckBox(positionsCheckBox,CHECKBOX_1B);
         checkBox1B.setOnAction(e ->{
               if(checkBox1B.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_1B");
               player.setQualifyPositions(setQP(player.getQualifyPositions()));
        });
        checkBox3B = initChildCheckBox(positionsCheckBox,CHECKBOX_3B);
         checkBox3B.setOnAction(e ->{
               if(checkBox3B.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_3B");
                  player.setQualifyPositions(setQP(player.getQualifyPositions()));
        });
        checkBox2B = initChildCheckBox(positionsCheckBox,CHECKBOX_2B);
          checkBox2B.setOnAction(e ->{
               if(checkBox2B.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_2B");
                  player.setQualifyPositions(setQP(player.getQualifyPositions()));
        });
        checkBoxSS = initChildCheckBox(positionsCheckBox,CHECKBOX_SS);
               checkBoxSS.setOnAction(e ->{
               if(checkBoxSS.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_SS");
                  player.setQualifyPositions(setQP(player.getQualifyPositions()));
        });
        checkBoxOF = initChildCheckBox(positionsCheckBox,CHECKBOX_OF);
            checkBoxOF.setOnAction(e ->{
               if(checkBoxOF.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_OF");
        });
        checkBoxP = initChildCheckBox(positionsCheckBox,CHECKBOX_P);
       checkBoxP.setOnAction(e ->{
               if(checkBoxP.isSelected())   
              player.setQualifyPositions(player.getQualifyPositions()+"_P");
        });
       
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(firstNameLabel, 0, 1, 1, 1);
        gridPane.add(firstNameTextField, 1, 1, 1, 1);
        gridPane.add(lastNameLabel, 0, 2, 1, 1);      
        gridPane.add(lastNameTextField, 1, 2, 1, 1);
        gridPane.add(proTeamLabel, 0 ,3, 1, 1);
        gridPane.add(proTeamComboBox, 1, 3, 1, 1);
        
        playerPane = new VBox();
        playerPane.getChildren().add(gridPane);
        playerPane.getChildren().add(positionsCheckBox);
               
        decisionButton = new HBox(5);
        decisionButton.getChildren().add(completeButton);
        decisionButton.getChildren().add(new Label("    "));
        decisionButton.getChildren().add(cancelButton);
        playerPane.getChildren().add(new Label(""));
        playerPane.getChildren().add(new Label(""));       
        playerPane.getChildren().add(decisionButton);
        playerPane.getChildren().add(new Label(""));
        
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(playerPane);
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
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @return player
     */
    public Player showAddPlayerDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_PLAYER_TITLE);
                         
        // RESET THE PLAYER OBJECT WITH DEFAULT VALUES
        player = new Player();
        player.setProTeam("ATL");
        //reset boolean values
         hasCI=false; hasMI=false;hasU=false;
         //reset checkBox
         checkBoxC.setSelected(false);checkBox1B.setSelected(false);
         checkBox2B.setSelected(false);checkBox3B.setSelected(false);
         checkBoxP.setSelected(false);checkBoxSS.setSelected(false);
         checkBoxOF.setSelected(false);
        // LOAD THE UI STUFF          
        firstNameTextField.setText(player.getFirstName());
        lastNameTextField.setText(player.getLastName());
        proTeamComboBox.getSelectionModel().selectFirst();
        // AND OPEN IT UP
        this.showAndWait();             
            
        return player;
    }
    
    /**
     *This is used to check the complete button is selected or not
     * @return a boolean value
     */
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
   
    private CheckBox initChildCheckBox(Pane container, String text){
        CheckBox cb= new CheckBox(text);
        container.getChildren().add(cb);
        return cb;
    }
    
     private String setQP(String positions){
            String qp=positions;           
            String sign="_";
            String[] tokens = positions.split(sign);
                for(int j=0; j<tokens.length;j++){
                    if(tokens[j].equals("1B") | tokens[j].equals("3B")){
                        if(hasCI==false){
                        qp+="_CI";
                        hasCI=true;                        
                        }
                         if(hasU==false){
                              qp+="_U";
                              hasU=true;
                            }
                    }
                    
                    else if(tokens[j].equals("2B") | tokens[j].equals("SS")){
                        if(hasMI==false){
                        qp+="_MI";
                        hasMI=true;
                            }    
                         if(hasU==false){
                              qp+="_U";
                              hasU=true;
                            }
                                }
                    else if(tokens[j].equals("C") |tokens[j].equals("1B") |tokens[j].equals("2B") |
                            tokens[j].equals("3B") |tokens[j].equals("SS") |tokens[j].equals("OF") ){
                       
                    }
                    
                     }  
                          
            return qp;
        }
}