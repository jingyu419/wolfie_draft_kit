package wdk.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import wdk.gui.MessageDialog;
import wdk.gui.YesNoCancelDialog;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wdk.data.Draft;
import wdk.data.DraftDataManager;
import wdk.data.Player;
import wdk.data.Team;
import wdk.gui.WDK_GUI;

/**
 *This class is used to open a dialog for user to add/remove/edit/move a team
 * @author jingyu
 */
public class DraftScreenController {       
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
     // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;   
    public volatile boolean running=false;
    public static int counter=0;
    /**
     *This is the constructor for the class
     * @param initPrimaryStage
     * @param draft
     * @param initMessageDialog
     * @param initYesNoCancelDialog
     */
    public DraftScreenController(Stage initPrimaryStage, Draft draft, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {  
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
         properties = PropertiesManager.getPropertiesManager();
      

    }
    
    /**
     *This methood is used to draft one best player best on the estimated value
     * @param gui
     */        
    public void handleDraftOnePlayerRequest(WDK_GUI gui) {
        
        DraftDataManager ddm = gui.getDataManager();
        Draft draft = ddm.getDraft();
        ObservableList<Player> draftedPlayers=draft.getDraftedPlayers();
        ObservableList<Player> dataBase=draft.getDataBase();
        ObservableList<Team> teams=draft.getTeams();
        
        //calculate the total players all teams need
        int totalRemainSpots=0;
        for(Team t: teams)
            totalRemainSpots+=t.getPlayersNeeded();
       
        Boolean isPlaced=false;//used to check if this player insert to the team or not
        int i=0;//used to go through the dataBase
        //First, I need to find which fantasy team needs players
        if(totalRemainSpots!=0){
        for(Team t: teams){
            if(t.getPlayersNeeded()>0 && isPlaced==false){
            //second,I have to find the highest estimated value player, and assigned to a team
                Collections.sort(dataBase, new Comparator<Player>(){           
                  public int compare(Player p1, Player p2){                              
                   return (p1.getEstimatedValue()<p2.getEstimatedValue())?1:
                   (p1.getEstimatedValue()>p2.getEstimatedValue())?-1:0;
           }
       });
                //I need to check if the team need this player or not
                //then I need to check the isPlaced, when it is true, stop
                //make sure the player is not added to all the teams
                while(isPlaced==false){
                if(playerCanBeIntertToTheTeam(i,t,dataBase)>=1){//return true
                isPlaced=true;
                int positionNumber=playerCanBeIntertToTheTeam(i,t,dataBase);
                Player insertPlayer=dataBase.get(i);
                insertPlayer.setContract("S2");
                insertPlayer.setSalary(1);
                insertPlayer.setAssignedTeam(t.getTeamName());
             //   insertPlayer.setDraftPickNumber(draftNumber);
                if(positionNumber==1){ 
                    insertPlayer.setAssignedPosition("C");
                    draft.assignedCToTeam(t.getTeamName(), 1);
                }
                else if(positionNumber==2){ 
                    insertPlayer.setAssignedPosition("1B");
                    draft.assigned1BToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==3){ 
                    insertPlayer.setAssignedPosition("3B");
                    draft.assigned3BToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==4){ 
                    insertPlayer.setAssignedPosition("CI");
                    draft.assignedCIToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==5){ 
                    insertPlayer.setAssignedPosition("2B");
                    draft.assigned2BToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==6){ 
                    insertPlayer.setAssignedPosition("SS");
                    draft.assignedSSToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==7){ 
                    insertPlayer.setAssignedPosition("MI");
                    draft.assignedMIToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==8){ 
                    insertPlayer.setAssignedPosition("OF");
                    draft.assignedOFToTeam(t.getTeamName(), 1);
                }
                else if(positionNumber==9){ 
                    insertPlayer.setAssignedPosition("U");
                    draft.assignedUToTeam(t.getTeamName(), true);
                }
                else if(positionNumber==10){ 
                    insertPlayer.setAssignedPosition("P");
                    draft.assignedPToTeam(t.getTeamName(), 1);
                }
                 t.addPlayer(insertPlayer);
                 draft.assignPointsToTeams();
                 draft.addPlayerToDraftedPlayers(insertPlayer);
                 //draftedPlayers.add(insertPlayer);
                 dataBase.remove(i);
                }
                else 
                    i++;
                }                                     
        //Third, update the estimated value 
              // draft.calculateEstimatedValue();
            }
        }
        }
        //here I add the taxi players to the tables
        else{
             for(Team t: teams){
            if(t.getTaxiPlayersNumber()<8 &&isPlaced==false){
              
             //random pick a player
                isPlaced=true;
                int max=dataBase.size();
                int min=0;
                 Random rand = new Random();
                int randomNum = rand.nextInt((max - min) + 1) + min;
                Player insertPlayer=dataBase.get(randomNum);
                insertPlayer.setContract("X");
                insertPlayer.setSalary(1);
                insertPlayer.setAssignedTeam(t.getTeamName());  
                //assign a position to the taxi player
                String qp=insertPlayer.getQualifyPositions();
                  if(!qp.equals("P")){
                         String sign="_";
                         String[] tokens = qp.split(sign);
                          insertPlayer.setAssignedPosition(tokens[0]);
                }
                  else
                      insertPlayer.setAssignedPosition("P");
                  
                 draft.assignedTaxiNumberToTeam(t.getTeamName(), 1); 
                 t.addTaxiPlayer(insertPlayer);
                 draft.addPlayerToDraftedPlayers(insertPlayer);
                 //draftedPlayers.add(insertPlayer);
                 dataBase.remove(0);
                
                //Third, update the estimated value 
         //        draft.calculateEstimatedValue();
                }                                                                  
            }
        }        
        
        
    }
      /**
     *This methood is used to automatically draft players
     * @param gui
     */      
   
    public void handleStartDraftRequest(WDK_GUI gui) {   
        //reset the running signal to true
        running=true;
        //create a task, the method call must be implemented
            Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception { 
            DraftDataManager ddm = gui.getDataManager();
            Draft draft = ddm.getDraft();        
            ObservableList<Team> teams=draft.getTeams();
                //calculate the total players all teams need
         
             int totalRemainSpots=0;
               for(Team t: teams){
                   totalRemainSpots+=t.getPlayersNeeded();                
                   totalRemainSpots+=(8-t.getTaxiPlayersNumber());
               }            
                for(int i=0;i<totalRemainSpots;i++){                    
                    while(running){
                        counter++;
                        if(counter!=0 && counter%31==0)
                            running=false;
                       Thread.sleep(700); 
                        // UPDATE
                        Platform.runLater(new Runnable() {                         
                            public void run() {                                         
                                handleDraftOnePlayerRequest(gui);                                                                                      
                            }     
                        });           
                }    
               //      if(i==totalRemainSpots)
               //         handlePauseDraftRequest();// running=false;
                }
               
                return null;
          
            }            
        };
         
        Thread thread = new Thread(task); 
      //  thread.setDaemon(false);
        thread.start();                                                 
    }

    
        /**
     *This methood is used to stop drafting players
     * @param gui
     */        
    public void handlePauseDraftRequest() {           
            running=false;
    }
    
    private int playerCanBeIntertToTheTeam(int value, Team team, ObservableList<Player> data){
        //0->NO POSITION 1->C  2->1B  3->3B  4->CI 5->2B 6->SS 7->MI 8->OF 9->U 10->P
        int whichPosition=0;
         //(1)create 10 enable signals to check player qualify positions
        Boolean enableC=false; Boolean enable1B=false; Boolean enable3B=false;
        Boolean enableCI=false; Boolean enable2B=false; Boolean enableSS=false;
        Boolean enableMI=false;Boolean enableOF=false; Boolean enableU=false;Boolean enableP=false;
        Player player=data.get(value);
        String qp=player.getQualifyPositions();
        if(!qp.equals("P")){
            String sign="_";
            String[] tokens = qp.split(sign);
                for(int j=0; j<tokens.length;j++){
                    if(tokens[j].equals("C")) enableC=true;
                    else if(tokens[j].equals("1B")) enable1B=true;
                    else if(tokens[j].equals("3B")) enable3B=true;
                    else if(tokens[j].equals("CI")) enableCI=true;
                    else if(tokens[j].equals("2B")) enable2B=true;
                    else if(tokens[j].equals("SS")) enableSS=true;
                    else if(tokens[j].equals("MI")) enableMI=true;
                    else if(tokens[j].equals("OF")) enableOF=true;
                    else if(tokens[j].equals("U")) enableU=true;               
                    }
        }
        else
            enableP=true;
        
           //try to put the player into the team
          if(team.getCounterC()<2 &&enableC==true &&whichPosition==0)
              whichPosition=1;          
          else if (!team.get1BFull() &&enable1B==true &&whichPosition==0)
              whichPosition=2;
          else if (!team.get3BFull() &&enable3B==true &&whichPosition==0)
              whichPosition=3;
          else if (!team.getCIFull() &&enableCI==true &&whichPosition==0)
              whichPosition=4;
          else if (!team.get2BFull() &&enable2B==true &&whichPosition==0)
              whichPosition=5;
          else if (!team.getSSFull() &&enableSS==true &&whichPosition==0)
              whichPosition=6;
          else if (!team.getMIFull() &&enableMI==true &&whichPosition==0)
              whichPosition=7;
          else if (team.getCounterOF()<5&& enableOF==true &&whichPosition==0)
              whichPosition=8;
          else if (!team.getUFull() &&enableU==true &&whichPosition==0)
              whichPosition=9;
          else if (team.getCounterP()<9 &&enableP==true &&whichPosition==0)
              whichPosition=10;
          
        return whichPosition;
    }
    
    public void setRunning(Boolean value){
        running=value;
    }
}
