package wdk.gui;


import static wdk.gui.WDK_GUI.CLASS_HEADING_LABEL;
import static wdk.gui.WDK_GUI.CLASS_PROMPT_LABEL;
import static wdk.gui.WDK_GUI.PRIMARY_STYLE_SHEET;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wdk.data.Draft;
import wdk.data.Team;

/**
 * This is the team dialog. This window will open
 * when user wants to add/remove/move/edit a team
 * @author jingyu
 */
public class TeamDialog  extends Stage {
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Team team;    
    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label teamNameLabel;
    TextField teamNameTextField;
    Label ownerNameLabel;
    TextField ownerNameTextField;
   
    Button completeButton;
    Button cancelButton;
    
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    
    // CONSTANTS FOR OUR UI

   
        public static final String COMPLETE = "Complete";

   
    public static final String CANCEL = "Cancel";

   
    public static final String TEAM_NAME_PROMPT = "Name: ";

   
    public static final String OWNER_NAME_PROMPT = "Owner: ";
   
    
    public static final String FANTASY_TEAM_HEADING = "Fantasy Team Details";

    
    public static final String ADD_FANTASY_TEAM_TITLE = "Add New Fantasy Team";

    
    public static final String EDIT_FANTASY_TEAM_TITLE = "Edit Fantasy Team";
    /**
     * Initializes this dialog so that it can be used for either adding
     * new Team or editing existing ones.
     * 
     * @param primaryStage The owner of this modal dialog.
     * @param 
     * @param messageDialog
     */
    public TeamDialog(Stage primaryStage, Draft draft,  MessageDialog messageDialog) {       
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
        headingLabel = new Label(FANTASY_TEAM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE TEAM NAME 
        teamNameLabel = new Label(TEAM_NAME_PROMPT);
        teamNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        teamNameTextField = new TextField();
        teamNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                team.setTeamName(newValue);
        });
                
        // AND THE OWNER
        ownerNameLabel = new Label(OWNER_NAME_PROMPT);
        ownerNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerNameTextField = new TextField();
        ownerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                team.setOwnerName(newValue);
        });
        
        
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            TeamDialog.this.selection = sourceButton.getText();
            TeamDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(teamNameLabel, 0, 1, 1, 1);
        gridPane.add(teamNameTextField, 1, 1, 1, 1);
        gridPane.add(ownerNameLabel, 0, 2, 1, 1);      
        gridPane.add(ownerNameTextField, 1, 2, 1, 1);
        gridPane.add(completeButton, 0, 3, 1, 1);
        gridPane.add(cancelButton, 1, 3, 1, 1);

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
     *This is used to get a team
     * @return Team
     */
    public Team getTeam() { 
        return team;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @return team
     */
    public Team showAddTeamDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_FANTASY_TEAM_TITLE);
        
        // RESET THE TEAM OBJECT WITH DEFAULT VALUES
        team = new Team();
   
        // LOAD THE UI STUFF          
        teamNameTextField.setText(team.getTeamName());
        ownerNameTextField.setText(team.getOwnerName());
        // AND OPEN IT UP
        this.showAndWait();  
        return team;
    }
    
    /**
     *This is used to load GUI data
     * 
     */
    public void loadGUIData() {
        // LOAD THE UI STUFF 
        teamNameTextField.setText(team.getTeamName());
        ownerNameTextField.setText(team.getOwnerName());
    }
    
    /**
     *This is used to check the complete button is selected or not
     * @return a boolean value
     */
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    /**
     *Thi is used to edit a team
     * @param itemToEdit
     */
    public void showEditTeamDialog(Team itemToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_FANTASY_TEAM_TITLE);
        
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        team = new Team();
        team.setTeamName(itemToEdit.getTeamName());
        team.setOwnerName(itemToEdit.getOwnerName());
        
        // AND THEN INTO OUR GUI
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}