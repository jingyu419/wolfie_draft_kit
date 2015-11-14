/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdk.data;

import java.util.Collections;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jingyu
 */
public class Team {
    final StringProperty teamName;
    String ownerName;
    ObservableList<Player> teamPlayers;
    ObservableList<Player> taxiPlayers;
    final IntegerProperty playersNeeded;
    final IntegerProperty moneyLeft;
    final IntegerProperty personPerValue;
    final IntegerProperty run;
    final IntegerProperty homeRuns;
    final IntegerProperty RBI;
    final FloatProperty SB;
    final FloatProperty BA;
    final IntegerProperty walk;
    final IntegerProperty strikeouts;
    final IntegerProperty save;
    final FloatProperty ERA;
    final FloatProperty whip;
    final FloatProperty totalPoints;
    
    Boolean isCFull;
    Boolean is1BFull;//is One B (1B)    
    Boolean isCIFull;
    Boolean is3BFull;
    Boolean is2BFull;
    Boolean isMIFull;
    Boolean isSSFull;
    Boolean isOFFull;
    Boolean isUFull;
    Boolean isPFull;
    int counterC;
    int counterOF;
    int counterP;
    int taxiPlayersNumber;
    
    
    public static final String DEFAULT_TEAM_NAME="<ENTER TEAM>";
    public static final String DEFAULT_OWNER_NAME="<ENTER OWNER>";
    public static final int DEFAULT_PLAYERNEEDED =23;
    public static final int DEFAULT_TAXIPLAYERSNUMBER=0;
    public static final int DEFAULT_MONEYLEFT=260;
    public static final int DEFAULT_PERSONPERVALUE=0;
    public static final int DEFAULT_R=0;
    public static final int DEFAULT_HR=0;
    public static final int DEFAULT_RBI=0;
    public static final float DEFAULT_SB=0;
    public static final float DEFAULT_BA=0;
    public static final int DEFAULT_W=0;
    public static final int DEFAULT_K=0;
    public static final int DEFAULT_SV=0;
    public static final float DEFAULT_ERA=0;
    public static final float DEFAULT_WHIP=0;
    public static final float DEFAULT_TOTALPOINTS=0;
    
    
    public Team(){
        teamName=new SimpleStringProperty(DEFAULT_TEAM_NAME);
        ownerName=DEFAULT_OWNER_NAME;
        
        teamPlayers= FXCollections.observableArrayList(); 
        taxiPlayers=FXCollections.observableArrayList();
        isCFull=false; is1BFull=false; isCIFull=false;
        is3BFull=false;is2BFull=false; isMIFull=false;
        isSSFull=false;isOFFull=false; isUFull=false;
        isPFull=false;
        taxiPlayersNumber=DEFAULT_TAXIPLAYERSNUMBER;
        counterC=0; counterOF=0; counterP=0;
        playersNeeded = new SimpleIntegerProperty(DEFAULT_PLAYERNEEDED);
        moneyLeft = new SimpleIntegerProperty(DEFAULT_MONEYLEFT);
        personPerValue = new SimpleIntegerProperty(DEFAULT_PERSONPERVALUE);
        run = new SimpleIntegerProperty(DEFAULT_R);
        homeRuns = new SimpleIntegerProperty(DEFAULT_HR);
        RBI = new SimpleIntegerProperty(DEFAULT_RBI);
        SB = new SimpleFloatProperty(DEFAULT_SB);
        BA = new SimpleFloatProperty(DEFAULT_BA);
        walk = new SimpleIntegerProperty(DEFAULT_W);
        strikeouts = new SimpleIntegerProperty(DEFAULT_K);
        save = new SimpleIntegerProperty(DEFAULT_SV);
        ERA = new SimpleFloatProperty(DEFAULT_ERA);
        whip = new SimpleFloatProperty(DEFAULT_WHIP);
        totalPoints = new SimpleFloatProperty(DEFAULT_TOTALPOINTS);
    }
    
    public String getTeamName(){
        return teamName.get();
    }
    
    public void setTeamName(String name){
        teamName.set(name);
    }
    
    public StringProperty teamName(){
        return teamName;
    }
    
    public String getOwnerName(){
        return ownerName;
    }
    
    public void setOwnerName(String name){
        ownerName=name;
    }
     /**
     *This method tells us how to add players
     * @param player
     */
    public void addPlayer(Player player){   
        if(getPlayersNeeded()>=0){
        teamPlayers.add(player); 
        setPlayersNeeded(getPlayersNeeded()-1);
        setMoneyLeft(getMoneyLeft()-player.getSalary());
        if(getPlayersNeeded()!=0){
            setPersonPerValue((getMoneyLeft())/getPlayersNeeded()); 
         //setPersonPerValue((float)(Math.round(getPersonPerValue()*1000.0)/1000.0));
        }
        else
            setPersonPerValue(-1);
        if(!player.getQualifyPositions().equals("P")){
         setRun(getRun()+player.getRunsWalk());
         setHomeRuns(getHomeRuns()+player.getHomeRunsSaves());
         setRBI(getRBI()+player.getRunsBattedInStrikeouts());
         setSB(getSB()+player.getSB_ERA());       
       // setBA((getBA()*(22-getPlayersNeeded())+player.getBA_WHIP())/(23-getPlayersNeeded()));
         //setBA((float)(Math.round(getBA()*1000.0)/1000.0));
        }
     else{
         setWalk(getWalk()+player.getRunsWalk());
         setSave(getSave()+player.getHomeRunsSaves());
         setStrikeouts(getStrikeouts()+player.getRunsBattedInStrikeouts());
      //   setERA((getERA()*(22-getPlayersNeeded())+player.getSB_ERA())/(23-getPlayersNeeded()));
       //  setERA((float)(Math.round(getERA()*1000.0)/1000.0));
        // setWHIP((getWHIP()*(22-getPlayersNeeded())+player.getBA_WHIP())/(23-getPlayersNeeded()));   
       //  setWHIP((float)(Math.round(getWHIP()*1000.0)/1000.0));
        }        
        Collections.sort(teamPlayers);     
        }
    }
    
    public void addTaxiPlayer(Player player){      
            taxiPlayers.add(player);  
             Collections.sort(taxiPlayers); 
    }
       
     /**
     *This method return database
     * @return ObservableList<Player>
     */
    public ObservableList<Player> getTeamPlayers(){
        return teamPlayers;
    }
    
    public ObservableList<Player> getTaxiPlayers(){
        return taxiPlayers;
    }
    public void setTeamPlayers(ObservableList<Player> players){        
        teamPlayers=players;
    }
    public void setTaxiPlayers(ObservableList<Player> players){
        taxiPlayers=players;
    }
    /**
     *this method is used to remove a player
     * @param player
     */
    public void removePlayer(Player player){
            
        setPlayersNeeded(getPlayersNeeded()+1);
        setMoneyLeft(getMoneyLeft()+player.getSalary());       
        setPersonPerValue((getMoneyLeft())/getPlayersNeeded());      
        if(!player.getQualifyPositions().equals("P")){
         setRun(getRun()-player.getRunsWalk());
         setHomeRuns(getHomeRuns()-player.getHomeRunsSaves());
         setRBI(getRBI()-player.getRunsBattedInStrikeouts());
         setSB(getSB()-player.getSB_ERA());       
       // setBA((getBA()*(22-getPlayersNeeded())+player.getBA_WHIP())/(23-getPlayersNeeded()));
         //setBA((float)(Math.round(getBA()*1000.0)/1000.0));
        }
     else{
         setWalk(getWalk()-player.getRunsWalk());
         setSave(getSave()-player.getHomeRunsSaves());
         setStrikeouts(getStrikeouts()-player.getRunsBattedInStrikeouts()); 
        }                    
        teamPlayers.remove(player);
    }
    public void removeTaxiPlayer(Player player){
        taxiPlayers.remove(player);
    }
    
    /**
     *This method clear the team, make it to the original mode
     */
    public void clearTeam(){
       teamPlayers.clear();
    }       
    
    public void set1BFull(Boolean value){
        is1BFull=value;
    }
    
    public Boolean get1BFull(){
        return is1BFull;
    }
    
     public void setCFull(Boolean value){
        isCFull=value;
    }
         
    public Boolean getCFull(){
        return isCFull;
    }
    
     public void setCIFull(Boolean value){
        isCIFull=value;
    }
    
    public Boolean getCIFull(){
        return isCIFull;
    }
     public void set3BFull(Boolean value){
        is3BFull=value;
    }
    
    public Boolean get3BFull(){
        return is3BFull;
    }
     public void set2BFull(Boolean value){
        is2BFull=value;
    }
    
    public Boolean get2BFull(){
        return is2BFull;
    }
     public void setMIFull(Boolean value){
        isMIFull=value;
    }
    
    public Boolean getMIFull(){
        return isMIFull;
    }
     public void setSSFull(Boolean value){
        isSSFull=value;
    }
    
    public Boolean getSSFull(){
        return isSSFull;
    }
     public void setOFFull(Boolean value){
        isOFFull=value;
    }
    
    public Boolean getOFFull(){
        return isOFFull;
    }
     public void setUFull(Boolean value){
        isUFull=value;
    }
    
    public Boolean getUFull(){
        return isUFull;
    }
     public void setPFull(Boolean value){
        isPFull=value;
    }
    
    public Boolean getPFull(){
        return isPFull;
    }
    
    public int getCounterC(){
        return counterC;
    }
    
    public void defaultSetCounterC(int value){
        counterC=value;
    }
    public void setCounterC(int value){
        counterC+=value;
    }
    
     public int getCounterOF(){
        return counterOF;
    }
    
    public void setCounterOF(int value){
        counterOF+=value;
    }
    public void defaultSetCounterOF(int value){
        counterOF=value;
    }
     public int getCounterP(){
        return counterP;
    }
    
    public void setCounterP(int value){
        counterP+=value;
    }
    
    public void defaultSetCounterP(int value){
        counterP=value;
    }
    
     public int getPlayersNeeded(){
        return playersNeeded.get();
    }
    
    public void setPlayersNeeded(int value){
        playersNeeded.set(value);
    }
    
    public IntegerProperty playersNeededProperty(){
        return playersNeeded;
    }
    
    public int getMoneyLeft(){
        return moneyLeft.get();
    }
    
    public void setMoneyLeft(int value){
        moneyLeft.set(value);
    }
    public IntegerProperty moneyLeftProperty(){
        return moneyLeft;
    }
    
    public int getPersonPerValue(){
        return personPerValue.get();
    }
    public void setPersonPerValue(int value){
        personPerValue.set(value);
    }
    public IntegerProperty personPerValueProperty(){
        return personPerValue;   
    }
    
    public int getRun(){
        return run.get();
    }
    public void setRun(int value){
        run.set(value);
    }
    public IntegerProperty runProperty(){
        return run;
    }
    
    public int getHomeRuns(){
        return homeRuns.get();
    }
    public void setHomeRuns(int value){
        homeRuns.set(value);
    }
    public IntegerProperty homeRunsProperty(){
        return homeRuns;
    }
    
     public int getRBI(){
        return RBI.get();
    }
    public void setRBI(int value){
        RBI.set(value);
    }
    public IntegerProperty RBIProperty(){
        return RBI;
    }
    
     public float getSB(){
        return homeRuns.get();
    }
    public void setSB(float value){
        SB.set(value);
    }
    public FloatProperty SBProperty(){
        return SB;
    }
    
     public float getBA(){
        return BA.get();
    }
    public void setBA(float value){
        BA.set(value);
    }
    public FloatProperty BAProperty(){
        return BA;
    }
    
     public int getWalk(){
        return walk.get();
    }
    public void setWalk(int value){
        walk.set(value);
    }
    public IntegerProperty walkProperty(){
        return walk;
    }
    
     public int getStrikeouts(){
        return strikeouts.get();
    }
    public void setStrikeouts(int value){
        strikeouts.set(value);
    }
    public IntegerProperty strikeoutsProperty(){
        return strikeouts;
    }
    
    public int getSave(){
       return save.get();
    }
    public void setSave(int value){
        save.set(value);
    }
    public IntegerProperty saveProperty(){
        return save;
    }
     public float getERA(){
        return ERA.get();
    }
    public void setERA(float value){
        ERA.set(value);
    }
    public FloatProperty ERAProperty(){
        return ERA;
    }
    
     public float getWHIP(){
        return whip.get();
    }
    public void setWHIP(float value){
        whip.set(value);
    }
    public FloatProperty whipProperty(){
        return whip;
    }
    
     public float getTotalPoints(){
        return totalPoints.get();
    }
    public void setTotalPoints(float value){
        totalPoints.set(value);
    }
    public FloatProperty totalPointsProperty(){
        return totalPoints;
    }
    
    public int getTaxiPlayersNumber(){
        return taxiPlayersNumber;
    }
    public void setTaxiPlayersNumber(int value){
        taxiPlayersNumber++;
    }
}
