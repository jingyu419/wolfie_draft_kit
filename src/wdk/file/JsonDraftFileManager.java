/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.file;


import static wdk.WDK_StartupConstants.PATH_DRAFTS;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import wdk.data.Draft;
import wdk.data.Player;
import wdk.data.Team;

/**
 *This class handles the I/O of Json files.
 * @author jingyu
 */
public class JsonDraftFileManager implements DraftFileManager {
    //JSON FILE READING AND WRITING CONSTANTS
    String JSON_HITTERS = "Hitters";
    String JSON_PITCHERS = "Pitchers";
    //These are all player information
  
    String JSON_PROTEAM ="TEAM";
    String JSON_FIRST_NAME="FIRST_NAME";
    String JSON_LAST_NAME="LAST_NAME";
    String JSON_ASSIGNED_TEAM="ASSIGNED_TEAM";
    String JSON_ASSIGNED_POSITION="ASSIGNED_POSITION";
    String JSON_RUN_WALK="RUN_WALK";
    String JSON_HOMERUNS_SAVES="HOMERUNS_SAVES";
    String JSON_RUNBATTEDIN_STRIKEOUTS="RUNSBATTEDIN_STRIKEOUTS";
    String JSON_SB_ERA="SB_ERA";
    String JSON_BA_WHIP="BA_WHIP";
    String JSON_ESTIMATED_VALUE="ESTIMATED_VALUE";
    String JSON_CONTRACT="CONTRACT";
    String JSON_SALARY="SALARY";
    String JSON_PRIORITY="PRIORITY";
    String JSON_QP="QP";
    String JSON_AB="AB";
    String JSON_R="R";
    String JSON_H="H";
    String JSON_HR="HR";
    String JSON_RBI="RBI";
    String JSON_SB="SB";
    String JSON_NOTES="NOTES";
    String JSON_YEAR_OF_BIRTH="YEAR_OF_BIRTH";
    String JSON_NATION_OF_BIRTH="NATION_OF_BIRTH";
    //pitcher information
    String JSON_IP="IP";
    String JSON_W="W";
    String JSON_ER="ER";
    String JSON_SV="SV";
    String JSON_BB="BB";
    String JSON_K="K";
    String JSON_DRAFT_PICK_NUMBER="DRAFT_PICK_NUMBER";
    //These are for the draft information
    String JSON_DRAFT_NAME="DRAFT_NAME";
    String JSON_CURRENT_TEAM="CURRENT_TEAM";
    String JSON_PLAYER_DATABASE="PLAYER_DATABASE";
    String JSON_FANTASY_TEAMS="FANTASY_TEAMS";
    String JSON_DRAFTED_PLAYERS="DRAFTED_PLAYERS";
    
    //these are team information
    String JSON_TEAM_NAME="TEAM_NAME";
    String JSON_OWNER_NAME="OWNER_NAME";
    String JSON_TEAM_PLAYERS="TEAM_PLAYERS";
    String JSON_TAXI_PLAYERS="TAXI_PLAYERS";
    String JSON_ISCFULL="ISCFULL";
    String JSON_IS1BFULL="IS1BFULL";
    String JSON_ISCIFULL="ISCIFULL";
    String JSON_IS3BFULL="IS3BFULL";
    String JSON_IS2BFULL="IS2BFULL";
    String JSON_ISMIFULL="ISMIFULL";
    String JSON_ISSSFULL="ISSSFULL";
    String JSON_ISOFFULL="ISOFFULL";
    String JSON_ISUFULL="ISUFULL";
    String JSON_ISPFULL="ISPFULL";
    String JSON_COUNTERC="COUNTERC";
    String JSON_COUNTEROF="COUNTEROF";
    String JSON_COUNTERP="COUNTERP";
            
    String JSON_EXT = ".json";
    String SLASH = "/";
    
    /**
     * This method saves all the data associated with a draft to
     * a JSON file.
     * 
     * @param courseToDraft The draft whose data we are saving.
     * 
     * @throws IOException Thrown when there are issues writing
     * to the JSON file.
     */
    @Override
     public void saveDraft(Draft draftToSave) throws IOException{
        // BUILD THE FILE PATH
        String draftListing = "" + draftToSave.getDraftName();
        String jsonFilePath="";
        if(!draftListing.equals("")){
         jsonFilePath = PATH_DRAFTS + SLASH + draftListing + JSON_EXT;
        }
        else{
            draftListing="New Draft";
            jsonFilePath = PATH_DRAFTS+SLASH+draftListing+JSON_EXT;
        }
        
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
        
        //player database array
        JsonArray playerDatabaseArray = makePlayerDatabaseJsonArray(draftToSave.getDataBase());            
        //fantasy teams array
        JsonArray fantasyTeamsArray = makeFantasyTeamsJsonArray(draftToSave.getTeams());
        //drafed players array
        JsonArray draftedPlayersArray = makeDraftedPlayersJsonArray(draftToSave.getDraftedPlayers());
       
        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject draftJsonObject = Json.createObjectBuilder()
                                      .add(JSON_DRAFT_NAME, draftToSave.getDraftName())
                                      .add(JSON_CURRENT_TEAM, draftToSave.getCurrentTeam())
                                      .add(JSON_PLAYER_DATABASE, playerDatabaseArray) 
                                      .add(JSON_DRAFTED_PLAYERS, draftedPlayersArray)
                                      .add(JSON_FANTASY_TEAMS, fantasyTeamsArray)
                                      .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(draftJsonObject); 
     }
    
    /**
     * Loads the draftToLoad argument using the data found in the json file.
     * 
     * @param draftToLoad Draft to load.
     * @param jsonFilePath File containing the data to load.
     * 
     * @throws IOException Thrown when IO fails.
     */
    @Override
    public void loadDraft(Draft draftToLoad, String jsonFilePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE COURSE
        draftToLoad.setDraftName(json.getString(JSON_DRAFT_NAME));
        draftToLoad.setCurrentTeam(json.getString(JSON_CURRENT_TEAM));
       
        //get the players database
           draftToLoad.clearDatabase();
           JsonArray jsonPlayersDatabaseArray = json.getJsonArray(JSON_PLAYER_DATABASE);
           for (int i=0; i<jsonPlayersDatabaseArray.size();i++){
               JsonObject jso = jsonPlayersDatabaseArray.getJsonObject(i);
               Player player = new Player();
                player.setProTeam(jso.getString(JSON_PROTEAM));
                player.setFirstName(jso.getString(JSON_FIRST_NAME));          
                player.setLastName(jso.getString(JSON_LAST_NAME));                       
                player.setAssignedTeam(jso.getString(JSON_ASSIGNED_TEAM));  
                
                player.setAssignedPosition(jso.getString(JSON_ASSIGNED_POSITION));                                   
                player.setRunsWalk(jso.getInt(JSON_RUN_WALK));                            
                player.setHomeRunsSaves(jso.getInt(JSON_HOMERUNS_SAVES));                  
                player.setRunsBattedInStrikeouts(jso.getInt(JSON_RUNBATTEDIN_STRIKEOUTS));                         
                player.setEstimatedValue((jso.getInt(JSON_ESTIMATED_VALUE)));
                player.setContract(jso.getString(JSON_CONTRACT));
                player.setSalary(jso.getInt(JSON_SALARY));
                player.setPriority(jso.getInt(JSON_PRIORITY));                
                player.setQualifyPositions(jso.getString(JSON_QP));
                player.setAtBats(jso.getInt(JSON_AB));
                player.setHits(jso.getInt(JSON_H));
                player.setNotes(jso.getString(JSON_NOTES));
                player.setYearOfBirth(jso.getInt(JSON_YEAR_OF_BIRTH));
                player.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
                player.setBasesOnBalls(jso.getInt(JSON_BB));
                player.setEarnedRuns(jso.getInt(JSON_ER));
                player.setInningsPitched(jso.getInt(JSON_IP));
               player.setStolenBases(jso.getInt(JSON_SB));
            //    player.defaultSetSB_ERA(Float.parseFloat(jso.getString(JSON_SB_ERA)));
                player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                ,player.getStolenBases());
                 player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                     player.getInningsPitched(),player.getQualifyPositions());
                
               //then add it to the database
               draftToLoad.addPlayer(player);
           }
           
           //get the drafted Players
           draftToLoad.clearDraftedPlayers();
           JsonArray jsonDraftedPlayersArray = json.getJsonArray(JSON_DRAFTED_PLAYERS);
           for (int i=0; i<jsonDraftedPlayersArray.size();i++){
               JsonObject jso = jsonDraftedPlayersArray.getJsonObject(i);
               Player player = new Player();
                player.setProTeam(jso.getString(JSON_PROTEAM));
                player.setFirstName(jso.getString(JSON_FIRST_NAME));          
                player.setLastName(jso.getString(JSON_LAST_NAME));                       
                player.setAssignedTeam(jso.getString(JSON_ASSIGNED_TEAM));  
                player.setDraftPickNumber(jso.getInt(JSON_DRAFT_PICK_NUMBER));
                player.setAssignedPosition(jso.getString(JSON_ASSIGNED_POSITION));                                   
                player.setRunsWalk(jso.getInt(JSON_RUN_WALK));                            
                player.setHomeRunsSaves(jso.getInt(JSON_HOMERUNS_SAVES));                   
                player.setRunsBattedInStrikeouts(jso.getInt(JSON_RUNBATTEDIN_STRIKEOUTS));                               
                
                player.setEstimatedValue(jso.getInt(JSON_ESTIMATED_VALUE));
                player.setContract(jso.getString(JSON_CONTRACT));
                player.setSalary(jso.getInt(JSON_SALARY));
                player.setPriority(jso.getInt(JSON_PRIORITY));                
                player.setQualifyPositions(jso.getString(JSON_QP));
                player.setAtBats(jso.getInt(JSON_AB));
                player.setHits(jso.getInt(JSON_H));
                player.setNotes(jso.getString(JSON_NOTES));
                player.setYearOfBirth(jso.getInt(JSON_YEAR_OF_BIRTH));
                player.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
                player.setBasesOnBalls(jso.getInt(JSON_BB));
                player.setEarnedRuns(jso.getInt(JSON_ER));
                player.setInningsPitched(jso.getInt(JSON_IP));
               player.setStolenBases(jso.getInt(JSON_SB));
            //    player.defaultSetSB_ERA(Float.parseFloat(jso.getString(JSON_SB_ERA)));
                player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                ,player.getStolenBases());
                 player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                     player.getInningsPitched(),player.getQualifyPositions());
                
               //then add it to the drafted PLAYERs
               draftToLoad.addPlayerToDraftedPlayers(player);
           }
           
        //get the fantasyTeams information
           draftToLoad.clearFantasyTeams();
          JsonArray jsonFantasyTeamsArray = json.getJsonArray(JSON_FANTASY_TEAMS);
           for (int i=0; i<jsonFantasyTeamsArray.size();i++){
              // jsonFantasyTeamsArray.get(i)
               JsonObject jso1 = jsonFantasyTeamsArray.getJsonObject(i);
               Team team = new Team();
               team.setTeamName(jso1.getString(JSON_TEAM_NAME));
               JsonArray jsonTeamPlayersArray = jso1.getJsonArray(JSON_TEAM_PLAYERS);
               
             for (int j=0; j<jsonTeamPlayersArray.size();j++){
               JsonObject jsob = jsonTeamPlayersArray.getJsonObject(j);
               Player player = new Player();
                player.setProTeam(jsob.getString(JSON_PROTEAM));
                player.setFirstName(jsob.getString(JSON_FIRST_NAME));          
                player.setLastName(jsob.getString(JSON_LAST_NAME));                       
                player.setAssignedTeam(jsob.getString(JSON_ASSIGNED_TEAM));                                
                player.setAssignedPosition(jsob.getString(JSON_ASSIGNED_POSITION));                                   
                player.setRunsWalk(jsob.getInt(JSON_RUN_WALK));                            
                player.setHomeRunsSaves(jsob.getInt(JSON_HOMERUNS_SAVES));                                   
                player.setRunsBattedInStrikeouts(jsob.getInt(JSON_RUNBATTEDIN_STRIKEOUTS));           
            //    player.defaultSetSB_ERA(Float.parseFloat(jsob.getString(JSON_SB_ERA)));                            
          
                player.setEstimatedValue((jsob.getInt(JSON_ESTIMATED_VALUE)));
                player.setContract(jsob.getString(JSON_CONTRACT));
                player.setSalary(jsob.getInt(JSON_SALARY));
                player.setPriority(jsob.getInt(JSON_PRIORITY));
                player.setQualifyPositions(jsob.getString(JSON_QP));
                player.setAtBats(jsob.getInt(JSON_AB));
                player.setHits(jsob.getInt(JSON_H));
                player.setNotes(jsob.getString(JSON_NOTES));
                player.setYearOfBirth(jsob.getInt(JSON_YEAR_OF_BIRTH));
                player.setNationOfBirth(jsob.getString(JSON_NATION_OF_BIRTH));
                player.setBasesOnBalls(jsob.getInt(JSON_BB));
                player.setEarnedRuns(jsob.getInt(JSON_ER));
                player.setInningsPitched(jsob.getInt(JSON_IP));
               player.setStolenBases(jsob.getInt(JSON_SB));
               player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                ,player.getStolenBases());
              player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                     player.getInningsPitched(),player.getQualifyPositions());
                
               //then add it to the database
                 team.addPlayer(player);
           }    
             
             JsonArray jsonTaxiPlayersArray = jso1.getJsonArray(JSON_TAXI_PLAYERS);
               
             for (int j=0; j<jsonTaxiPlayersArray.size();j++){
               JsonObject jsob = jsonTaxiPlayersArray.getJsonObject(j);
               Player player = new Player();
                player.setProTeam(jsob.getString(JSON_PROTEAM));
                player.setFirstName(jsob.getString(JSON_FIRST_NAME));          
                player.setLastName(jsob.getString(JSON_LAST_NAME));                       
                player.setAssignedTeam(jsob.getString(JSON_ASSIGNED_TEAM));                                
                player.setAssignedPosition(jsob.getString(JSON_ASSIGNED_POSITION));                                   
                player.setRunsWalk(jsob.getInt(JSON_RUN_WALK));                            
                player.setHomeRunsSaves(jsob.getInt(JSON_HOMERUNS_SAVES));                                   
                player.setRunsBattedInStrikeouts(jsob.getInt(JSON_RUNBATTEDIN_STRIKEOUTS));           
            //    player.defaultSetSB_ERA(Float.parseFloat(jsob.getString(JSON_SB_ERA)));                            
          
                player.setEstimatedValue((jsob.getInt(JSON_ESTIMATED_VALUE)));
                player.setContract(jsob.getString(JSON_CONTRACT));
                player.setSalary(jsob.getInt(JSON_SALARY));
                player.setPriority(jsob.getInt(JSON_PRIORITY));
                player.setQualifyPositions(jsob.getString(JSON_QP));
                player.setAtBats(jsob.getInt(JSON_AB));
                player.setHits(jsob.getInt(JSON_H));
                player.setNotes(jsob.getString(JSON_NOTES));
                player.setYearOfBirth(jsob.getInt(JSON_YEAR_OF_BIRTH));
                player.setNationOfBirth(jsob.getString(JSON_NATION_OF_BIRTH));
                player.setBasesOnBalls(jsob.getInt(JSON_BB));
                player.setEarnedRuns(jsob.getInt(JSON_ER));
                player.setInningsPitched(jsob.getInt(JSON_IP));
               player.setStolenBases(jsob.getInt(JSON_SB));
               player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                ,player.getStolenBases());
              player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                     player.getInningsPitched(),player.getQualifyPositions());
                
               //then add it to the taxiplayers
                 team.addTaxiPlayer(player);
           }   
             
             
               team.setOwnerName(jso1.getString(JSON_OWNER_NAME));
               team.setCFull(jso1.getBoolean(JSON_ISCFULL));
               team.set1BFull(jso1.getBoolean(JSON_IS1BFULL));  
               team.setCIFull(jso1.getBoolean(JSON_ISCIFULL));
               team.set3BFull(jso1.getBoolean(JSON_IS3BFULL));
               team.set2BFull(jso1.getBoolean(JSON_IS2BFULL));                                
               team.setMIFull(jso1.getBoolean(JSON_ISMIFULL));                                 
               team.setSSFull(jso1.getBoolean(JSON_ISSSFULL));                                
               team.setOFFull(jso1.getBoolean(JSON_ISOFFULL));                              
               team.setUFull(jso1.getBoolean(JSON_ISUFULL));                                  
               team.setPFull(jso1.getBoolean(JSON_ISPFULL));                      
               team.defaultSetCounterC(jso1.getInt(JSON_COUNTERC));                                   
               team.defaultSetCounterOF(jso1.getInt(JSON_COUNTEROF));                                    
               team.defaultSetCounterP(jso1.getInt(JSON_COUNTERP));  
               //then add it to the fantasy teams               
               draftToLoad.addTeam(team);
           }
           
            
    }
    
    @Override
    public ArrayList<Player> loadHitters(String jsonFilePath) throws IOException{
        
        //load the JSON file with all the data
      ArrayList<Player> hittersArray = loadHittersArrayFromJSONFile(jsonFilePath, JSON_HITTERS);
     
      return hittersArray;
    }
    
    @Override
    public ArrayList<Player> loadPitchers(String jsonFilePath) throws IOException{
        //load the JSON file with all the data
      ArrayList<Player> pitchersArray = loadPitchersArrayFromJSONFile(jsonFilePath, JSON_PITCHERS);
     
      return pitchersArray;
    }
    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    
  
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }    
    
    // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    //Procedure to load the JSON file.
    //(1) Get the JsonFile and import it to a JsonObject
    //(2) get the JsonArray out from the JsonObject
    //(3) Update the everthing inside the Player class.
    private ArrayList<Player> loadHittersArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<Player> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
       
        for(int i = 0; i< jsonArray.size();i++){
         JsonObject jso = jsonArray.getJsonObject(i);
         Player player = new Player();
         player.setFirstName(jso.getString(JSON_FIRST_NAME));
         player.setLastName(jso.getString(JSON_LAST_NAME));
         player.setProTeam(jso.getString(JSON_PROTEAM));
         player.setQualifyPositions(setQP(jso.getString(JSON_QP)));
       //  player.setQualifyPositions(jso.getString(JSON_QP));
         player.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
         player.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
         player.setAtBats(Integer.parseInt(jso.getString(JSON_AB)));
         player.setRunsWalk(Integer.parseInt(jso.getString(JSON_R)));
         player.setHits(Integer.parseInt(jso.getString(JSON_H)));
         player.setHomeRunsSaves(Integer.parseInt(jso.getString(JSON_HR)));
         player.setRunsBattedInStrikeouts(Integer.parseInt(jso.getString(JSON_RBI)));
         player.setStolenBases(Integer.parseInt(jso.getString(JSON_SB)));
         player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                 ,Integer.parseInt(jso.getString(JSON_SB)));
         player.setNotes(jso.getString(JSON_NOTES));
      //   player.setBattingAverage(player.getHits(), player.getAtBats());         
         player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                 player.getInningsPitched(),player.getQualifyPositions());
         
         items.add(player);
        }
        
        return items;
    }
    
    // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ArrayList<Player> loadPitchersArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<Player> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
       
        for(int i = 0; i< jsonArray.size();i++){
         JsonObject jso = jsonArray.getJsonObject(i);
         Player player = new Player();
          player.setQualifyPositions("P");
         player.setFirstName(jso.getString(JSON_FIRST_NAME));
         player.setLastName(jso.getString(JSON_LAST_NAME));
         player.setProTeam(jso.getString(JSON_PROTEAM));
         player.setInningsPitched(Float.parseFloat(jso.getString(JSON_IP)));
         player.setYearOfBirth(Integer.parseInt(jso.getString(JSON_YEAR_OF_BIRTH)));
         player.setNationOfBirth(jso.getString(JSON_NATION_OF_BIRTH));
         player.setEarnedRuns(Integer.parseInt(jso.getString(JSON_ER)));
         player.setRunsWalk(Integer.parseInt(jso.getString(JSON_W)));
         player.setHits(Integer.parseInt(jso.getString(JSON_H)));
         player.setHomeRunsSaves(Integer.parseInt(jso.getString(JSON_SV)));
         player.setRunsBattedInStrikeouts(Integer.parseInt(jso.getString(JSON_K)));
         player.setBasesOnBalls(Integer.parseInt(jso.getString(JSON_BB)));
         player.setSB_ERA(player.getEarnedRuns(), player.getInningsPitched(),player.getQualifyPositions()
                 ,player.getStolenBases());        
         player.setNotes(jso.getString(JSON_NOTES));
      //   player.setBattingAverage(player.getHits(), player.getAtBats());         
         player.setBA_WHIP(player.getHits(), player.getAtBats(), player.getRunsWalk(), 
                 player.getInningsPitched(),player.getQualifyPositions());
         
         items.add(player);
        }
        
        return items;           
        }
         
    private String setQP(String positions){
            String qp=positions;
           Boolean hasCI=false;
           Boolean hasMI=false;
           Boolean hasP=false;
            String sign="_";
            String[] tokens = positions.split(sign);
                for(int j=0; j<tokens.length;j++){
                    if(tokens[j].equals("1B") | tokens[j].equals("3B")){
                        if(hasCI==false){
                        qp+="_CI";
                        hasCI=true;                        
                        }
                    }
                    
                    else if(tokens[j].equals("2B") | tokens[j].equals("SS")){
                        if(hasMI==false){
                        qp+="_MI";
                        hasMI=true;
                                        }                     
                                }
                    
                     }  
                qp+="_U";
            return qp;
        }
        
 
     // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED SCHEDULE ITEM
    private JsonObject makePlayerJsonObject(Player player) {       
        JsonObject jso = Json.createObjectBuilder().add(JSON_PROTEAM,            player.getProTeam())
                                                   .add(JSON_FIRST_NAME,         player.getFirstName())
                                                   .add(JSON_LAST_NAME,          player.getLastName())
                                                   .add(JSON_ASSIGNED_TEAM, player.getAssignedTeam())
                                                   .add(JSON_ASSIGNED_POSITION,  player.getAssignedPosition())
                                                   .add(JSON_RUN_WALK,         player.getRunsWalk())
                                                   .add(JSON_HOMERUNS_SAVES,          player.getHomeRunsSaves())
                                                   .add(JSON_RUNBATTEDIN_STRIKEOUTS,  player.getRunsBattedInStrikeouts())
                                                   .add(JSON_SB_ERA,          player.getSB_ERA())
                                                   .add(JSON_BA_WHIP,         player.getBA_WHIP())
                                                   .add(JSON_ESTIMATED_VALUE, Math.round(player.getEstimatedValue()*1000.0)/1000.0)
                                                   .add(JSON_CONTRACT, player.getContract())
                                                   .add(JSON_SALARY,          player.getSalary())
                                                   .add(JSON_PRIORITY,         player.getPriority())
                                                   .add(JSON_QP,          player.getQualifyPositions())
                                                   .add(JSON_AB, player.getAtBats())
                                                   .add(JSON_H,          player.getHits())
                                                   .add(JSON_NOTES,         player.getNotes())
                                                   .add(JSON_YEAR_OF_BIRTH,          player.getYearOfBirth())
                                                   .add(JSON_NATION_OF_BIRTH, player.getNationOfBirth())
                                                   .add(JSON_BB,          player.getBasesOnBalls())
                                                   .add(JSON_ER,         player.getEarnedRuns())
                                                   .add(JSON_IP,          player.getInningsPitched())
                                                   .add(JSON_SB, player.getStolenBases())
                                                   .add(JSON_DRAFT_PICK_NUMBER, player.getDraftPickNumber())
                                                   .build();
        return jso;
    }    

    // MAKE AN ARRAY OF SCHEDULE ITEMS
    private JsonArray makePlayerDatabaseJsonArray(ObservableList<Player> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Player player : data) {
            jsb.add(makePlayerJsonObject(player));
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonArray makeDraftedPlayersJsonArray(ObservableList<Player> data){
        JsonArrayBuilder jsb = Json.createArrayBuilder();      
        for (Player player : data) {
            jsb.add(makePlayerJsonObject(player));
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
      // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED SCHEDULE ITEM
    private JsonObject makeTeamJsonObject(Team team) {   
          JsonArray teamPlayers = makePlayerDatabaseJsonArray(team.getTeamPlayers());
          JsonArray taxiPlayers = makePlayerDatabaseJsonArray(team.getTaxiPlayers());
        JsonObject jso = Json.createObjectBuilder().add(JSON_TEAM_NAME,             team.getTeamName())
                                                   .add(JSON_TEAM_PLAYERS,          teamPlayers)
                                                   .add(JSON_TAXI_PLAYERS,          taxiPlayers)
                                                   .add(JSON_OWNER_NAME,             team.getOwnerName())
                                                   .add(JSON_ISCFULL,             team.getCFull())
                                                   .add(JSON_IS1BFULL,             team.get1BFull())
                                                   .add(JSON_ISCIFULL,             team.getCIFull())
                                                   .add(JSON_IS3BFULL,             team.get3BFull())
                                                   .add(JSON_IS2BFULL,             team.get2BFull())
                                                   .add(JSON_ISMIFULL,             team.getMIFull())
                                                   .add(JSON_ISSSFULL,             team.getSSFull())
                                                   .add(JSON_ISOFFULL,             team.getOFFull())
                                                   .add(JSON_ISUFULL,             team.getUFull())
                                                   .add(JSON_ISPFULL,             team.getPFull())
                                                   .add(JSON_COUNTERC,             team.getCounterC())
                                                   .add(JSON_COUNTEROF,             team.getCounterOF())
                                                   .add(JSON_COUNTERP,             team.getCounterP()) 
                                                   .build();
        return jso;
    }    

    // MAKE AN ARRAY OF SCHEDULE ITEMS
    private JsonArray makeFantasyTeamsJsonArray(ObservableList<Team> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Team team : data) {
            jsb.add(makeTeamJsonObject(team));
        }
        JsonArray jA = jsb.build();
        return jA;
    }    
        
}
