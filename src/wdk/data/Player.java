
package wdk.data;


import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *This class holds all the information about a player
 * @author jingyu
 */
public class Player implements Comparable{
    final IntegerProperty draftPickNumber;
    final StringProperty firstName;
    final StringProperty lastName;   
    final StringProperty proTeam;
    final StringProperty assignedTeam;
    final StringProperty qualifyPositions;
    final StringProperty assignedPosition;
    final IntegerProperty yearOfBirth;
    final StringProperty nationOfBirth;
    final IntegerProperty atBats;
    final IntegerProperty runs_walk;
    final IntegerProperty hits;
    final IntegerProperty homeRuns_saves;
    final IntegerProperty runsBattedIn_strikeouts;
    final IntegerProperty stolenBases;
    //final FloatProperty earnedRunAverage;
    final FloatProperty SB_ERA;
    final StringProperty notes;
    final IntegerProperty earnedRuns;
    final FloatProperty inningsPitched;
   // final FloatProperty battingAverage;
    //final FloatProperty whip;
    final FloatProperty BA_WHIP;
    final IntegerProperty basesOnBalls;
    final FloatProperty estimatedValue;
    final StringProperty contract;
    final IntegerProperty salary;
    

    
    public static final int DEFAULT_DRAFT_PICK_NUMBER=1;
    public static final String DEFAULT_FIRSTNAME = "<Enter first name>";
    public static final String DEFAULT_LASTNAME = "<Enter last Name>";
    public static final String DEFAULT_PROTEAM = "";   
    public static final String DEFAULT_ASSIGNEDTEAM ="NONE";
    public static final String DEFAULT_QUALIFYPOSITIONS = "";
    public static final String DEFAULT_ASSIGNEDPOSITION ="NONE";
    public static final int DEFAULT_YEAROFBIRTH =1993;
    public static final String DEFAULT_NATIONOFBIRTH = "USA";
    public static final int DEFAULT_ATBATS =0;
    public static final int DEFAULT_RUNS_WALK =0;
    public static final int DEFAULT_HITS =0;
    public static final int DEFAULT_HOMERUNS_SAVES =0;
    public static final int DEFAULT_RUNSBATTEDIN_STRIKEOUTS =0;
    public static final int DEFAULT_STOLENBASES =0;
    //public static final float DEFAULT_ERA=0;
     public static final float DEFAULT_SB_ERA=0;
    public static final String DEFAULT_NOTES ="NONE";
    public static final int DEFAULT_EARNEDRUNS =0;
    public static final float DEFAULT_INNINGSPITCHED =0;
   // public static final float DEFAULT_BATTINGAVERAGE =0;
   // public static final float DEFAULT_WHIP=0;
     public static final float DEFAULT_BA_WHIP=0;
    public static final int DEFAULT_BASESONBALLS =0;
    public static final float DEFAULT_ESTIMATEDVALUE=0;
    public static final String DEFAULT_CONTRACT="NONE";
    public static final int DEFAULT_SALARY=0;
    
    //Used to determine where this player should be put in a table
    public int priority=0;
    
        //Ranks for each category
    public int rank_R_W;
    public int rank_HR_SV;
    public int rank_RBI_W;
    public int rank_SB_ERA;
    public int rank_BA_WHIP;
    public float averageRank;
    
    /**
     *This is the default constructor
     */
    public Player(){
        draftPickNumber = new SimpleIntegerProperty(DEFAULT_DRAFT_PICK_NUMBER);
        firstName= new SimpleStringProperty(DEFAULT_FIRSTNAME);
        lastName= new SimpleStringProperty(DEFAULT_LASTNAME);
        proTeam= new SimpleStringProperty(DEFAULT_PROTEAM);
        assignedTeam=new SimpleStringProperty(DEFAULT_ASSIGNEDTEAM);
        qualifyPositions= new SimpleStringProperty(DEFAULT_QUALIFYPOSITIONS);
        assignedPosition= new SimpleStringProperty(DEFAULT_ASSIGNEDPOSITION);
        yearOfBirth=new SimpleIntegerProperty(DEFAULT_YEAROFBIRTH);
        nationOfBirth=new SimpleStringProperty(DEFAULT_NATIONOFBIRTH);
        atBats=new SimpleIntegerProperty(DEFAULT_ATBATS);
        runs_walk=new SimpleIntegerProperty(DEFAULT_RUNS_WALK);
        hits=new SimpleIntegerProperty(DEFAULT_HITS);
        homeRuns_saves=new SimpleIntegerProperty(DEFAULT_HOMERUNS_SAVES);
        runsBattedIn_strikeouts=new SimpleIntegerProperty(DEFAULT_RUNSBATTEDIN_STRIKEOUTS);
        stolenBases=new SimpleIntegerProperty(DEFAULT_STOLENBASES);
        //earnedRunAverage= new SimpleFloatProperty(DEFAULT_ERA);
        SB_ERA= new SimpleFloatProperty(DEFAULT_SB_ERA);
        notes= new SimpleStringProperty(DEFAULT_NOTES);
        earnedRuns=new SimpleIntegerProperty(DEFAULT_EARNEDRUNS);
        inningsPitched=new SimpleFloatProperty(DEFAULT_INNINGSPITCHED);
      //  battingAverage=new SimpleFloatProperty(DEFAULT_BATTINGAVERAGE);
      //  whip= new SimpleFloatProperty(DEFAULT_WHIP);
        BA_WHIP= new SimpleFloatProperty(DEFAULT_BA_WHIP);
        basesOnBalls=new SimpleIntegerProperty(DEFAULT_BASESONBALLS);
        estimatedValue= new SimpleFloatProperty(DEFAULT_ESTIMATEDVALUE);
        contract = new SimpleStringProperty(DEFAULT_CONTRACT);
        salary = new SimpleIntegerProperty(DEFAULT_SALARY);
        priority=0;
        rank_R_W=0;
        rank_HR_SV=0;
        rank_RBI_W=0;
        rank_SB_ERA=0;
        rank_BA_WHIP=0;
        averageRank=0;
    }
    
 
    public void reset(){    
    }
  
    public int getDraftPickNumber(){
        return draftPickNumber.get();
    }
    public void setDraftPickNumber(int initDraftPickNumber){
        draftPickNumber.set(initDraftPickNumber);
    }
    public IntegerProperty draftPickNumberProperty(){
        return draftPickNumber;
    }
    public String getFirstName(){
        return firstName.get();
    }
    
  
    public void setFirstName(String initFirstName){
        firstName.set(initFirstName);
    }
    
   
    public StringProperty firstNameProperty(){
        return firstName;
    }
    
   
    public String getLastName(){
        return lastName.get();
    }
    
    public void setLastName(String initLastName){
        lastName.set(initLastName);
    }
   
    public StringProperty lastNameProperty(){
        return lastName;
    }
    
    
    public String getProTeam(){
        return proTeam.get();
    }
    
   
    public void setProTeam(String initProTeam){
        proTeam.set(initProTeam);
    }
    
    
    public StringProperty proTeamProperty(){
        return proTeam;
    }

    public String getAssignedTeam(){
        return assignedTeam.get();
    }
    public void setAssignedTeam(String initTeam){
        assignedTeam.set(initTeam);
                
    }
    public StringProperty assignedTeamProperty(){
        return assignedTeam;
    }
    public String getQualifyPositions(){
        return qualifyPositions.get();
    }
    
    public void setQualifyPositions(String initQualifyPositions){
         qualifyPositions.set(initQualifyPositions);                
    }

    public StringProperty qualifyPositionsProperty(){
        return qualifyPositions;
    }
    
    public String getAssignedPosition(){
        return assignedPosition.get();
    }
    
    public void setAssignedPosition(String initPosition){
         assignedPosition.set(initPosition);
         
         if(initPosition.equals("C"))
             priority=1;
         else if (initPosition.equals("1B"))
             priority=2;
         else if (initPosition.equals("CI"))
             priority=3;
         else if (initPosition.equals("3B"))
             priority=4;
         else if (initPosition.equals("2B"))
             priority=5;
         else if (initPosition.equals("MI"))
             priority=6;
         else if (initPosition.equals("SS"))
             priority=7;
         else if (initPosition.equals("OF"))
             priority=8;
         else if (initPosition.equals("U"))
             priority=9;
         else if (initPosition.equals("P"))
             priority=10;
         else
             priority=0;
            
    }

    public StringProperty assignedPositionProperty(){
        return assignedPosition;
    }
    
  
    public int getYearOfBirth(){
        return yearOfBirth.get();
    }
    
 
    public IntegerProperty yearOfBirthProperty(){
        return yearOfBirth;
    }

   
    public void setYearOfBirth(int initYearOfBirth){
        yearOfBirth.set(initYearOfBirth);
    }
    
    
    public String getNationOfBirth(){
       return nationOfBirth.get();
    }
    
   
    public StringProperty nationOfBirth(){
        return nationOfBirth;
    }

    /**
     *
     * @param nation
     */
    public void setNationOfBirth(String nation){
        nationOfBirth.set(nation);
    }
    
    public int getAtBats(){
        return atBats.get();
    }
    public IntegerProperty atBatsProperty(){
        return atBats;
    }

    /**
     *
     * @param bats
     */
    public void setAtBats(int bats){
        atBats.set(bats);
    }
    
    /**
     *
     * @return
     */
    public int getRunsWalk(){
        return runs_walk.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty runs_walkProperty(){
        return runs_walk;
    }

    /**
     *
     * @param run
     */
    public void setRunsWalk(int run){
        runs_walk.set(run);
    }
    
    public int getHits(){
        return hits.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty hitsProperty(){
        return hits;
    }

    /**
     *
     * @param intHits
     */
    public void setHits(int intHits){
        hits.set(intHits);
    }
    
    /**
     *
     * @return
     */
    public int getHomeRunsSaves(){
        return homeRuns_saves.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty homeRuns_savesProperty(){
        return homeRuns_saves;
    }

    /**
     *
     * @param home
     */
    public void setHomeRunsSaves(int home){
        homeRuns_saves.set(home);
    }
    
    /**
     *
     * @return
     */
    public int getRunsBattedInStrikeouts(){
        return runsBattedIn_strikeouts.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty runsBattedIn_strikeoutsProperty(){
        return runsBattedIn_strikeouts;
    }
    public void setRunsBattedInStrikeouts(int rbi){
        runsBattedIn_strikeouts.set(rbi);
    }
    
    /**
     *
     * @return
     */
    public int getStolenBases(){
        return stolenBases.get();
    }
    public IntegerProperty stolenBasesProperty(){
        return stolenBases;
    }

    /**
     *
     * @param sb
     */
    public void setStolenBases(int sb){
        stolenBases.set(sb);
    }
    
    public String getNotes(){
        return notes.get();
    }

    public StringProperty notesProperty(){
        return notes;
    }
    public void setNotes(String note){
        notes.set(note);
    }
    
    /**
     *
     * @return
     */
    public int getEarnedRuns(){
        return earnedRuns.get();
    }

    /**
     *
     * @return
     */
    public IntegerProperty earnedRunsProperty(){
        return earnedRuns;
    }

    /**
     *
     * @param er
     */
    public void setEarnedRuns(int er){
        earnedRuns.set(er);
    }
    
    /**
     *
     * @return
     */
    public float getInningsPitched(){
        return inningsPitched.get();
    }

    /**
     *
     * @return
     */
    public FloatProperty inningsPitchedProperty(){
        return inningsPitched;
    }

    /**
     *
     * @param ip
     */
    public void setInningsPitched(float ip){
        inningsPitched.set(ip);
    }
    
    /**
     *
     * @return
     */
    public int getBasesOnBalls(){
        return basesOnBalls.get();
    }
    
    public IntegerProperty basesOnBalls(){
        return basesOnBalls;
    }

    public void setBasesOnBalls(int bb){
        basesOnBalls.set(bb);
    }
    
   // public float getEarnedRunAverage(){
   //     return earnedRunAverage.get();
   // }
   // public FloatProperty earnedRunAverageProperty(){
   //     return earnedRunAverage;
   // }
   // public void setEearnedRunAverage(int ER, float IP){
  //      earnedRunAverage.set(ER*9/IP);
  //  }
    
    /**
     *
     * @return
     */
        
    public float getSB_ERA(){
        return SB_ERA.get();
    }
    public FloatProperty SB_ERAProperty(){
        return SB_ERA;
    }
    public void setSB_ERA(int ER,float IP, String positions,int sb){
        if(positions.equals("P"))
                SB_ERA.set((float) (Math.round((ER*9/IP)*100.0)/100.0));
        else
            SB_ERA.set(sb);
    }
    public void defaultSetSB_ERA(float value){
        SB_ERA.set(value);
    }
  //  public float getBattingAverage(){
    //    return battingAverage.get();
    //}
    //public FloatProperty battingAverageProperty(){
      //  return battingAverage;
    //}
    //public void setBattingAverage(int hits, int atBats){
      //  battingAverage.set((float)hits/atBats);
    //}
    
    //public float getWHIP(){
      //  return whip.get();                
    //}
    //public FloatProperty whipProperty(){
      //  return whip;
    //}
    //public void setWHIP(int walk, int hits, float IP){
      //  whip.set((walk+hits)/IP);
    //}

    /**
     *
     * @return
     */
        public float getBA_WHIP(){
        return BA_WHIP.get();
    }

    /**
     *
     * @return
     */
    public FloatProperty BA_WHIPProperty(){
        return BA_WHIP;
    }

    /**
     *
     * @param hits
     * @param atBats
     * @param walk
     * @param IP
     * @param positions
     */
    public void setBA_WHIP(int hits, int atBats, int walk,float IP, String positions){
        if(positions.equals("P"))
            BA_WHIP.set((float) (Math.round(((walk+hits)/IP)*100.0)/100.0));
        else
            BA_WHIP.set((float) (Math.round(((float)hits/atBats)*1000.0)/1000.0));
                    
    }
 //   public void defaultSetBA_WHIP(float value){
 //       BA_WHIP.set(value);
 //   }
    
     public float getEstimatedValue(){
        return estimatedValue.get();
    }
    
    public FloatProperty estimatedValueProperty(){
        return estimatedValue;
    }

    public void setEstimatedValue(float bb){     
       
        estimatedValue.set(bb);
    }
    
      public String getContract(){
        return contract.get();
    }

    public StringProperty contractProperty(){
        return contract;
    }
    public void setContract(String initContract){
        contract.set(initContract);
    }
     public int getSalary(){
        return salary.get();
    }
    
    public IntegerProperty salaryProperty(){
        return salary;
    }

    public void setSalary(int bb){
        salary.set(bb);
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
   public int compareTo(Object obj) {
        Player other = (Player)obj;
       return (this.getPriority()<other.getPriority())?-1:
               (this.getPriority()>other.getPriority())?1:0;
    }
         
   public int getPriority(){
       return priority;
   }
   
   public void setPriority(int value){
       priority=value;
   }
   
   public int getRank_R_W(){
       return rank_R_W;
   }
   public void setRank_R_W(int value){
       rank_R_W=value;
   }
   public int getRank_HR_SV(){
       return rank_HR_SV;
   }
   public void setRank_HR_SV(int value){
       rank_HR_SV=value;
   }
   public int getRank_RBI_W(){            
        return rank_RBI_W;
   }
   public void setRank_RBI_W(int value){
       rank_RBI_W=value;
   }
   public int getRank_SB_ERA(){
        return rank_SB_ERA;
   }
   public void setRank_SB_ERA(int value){
       rank_SB_ERA=value;
   }
   public int getRank_BA_WHIP(){
       return rank_BA_WHIP;
   }
   public void setRank_BA_WHIP(int value){
       rank_BA_WHIP=value;
   }        
   public float getAverageRank(){
       return averageRank;
   } 
   public void setAverageRank(float value){
       averageRank=value;
   }
}
