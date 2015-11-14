package wdk.data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *The class draft holds all the information about 
 * plyaers database and fantasy teams 
 * @author jingyu
 */
public class Draft {
    
    //I WILL PUT THEM INTO TABLES
    ObservableList<Player> dataBase;
    ObservableList<Team> fantasyTeams;
    ObservableList<Player> draftedPlayers;
    String currentTeam;
    String draftName;
    /**
     *This is the default constructor
     */
    public Draft(){
     dataBase= FXCollections.observableArrayList(); 
     draftName ="NONE";
     currentTeam="";
     fantasyTeams = FXCollections.observableArrayList();
     draftedPlayers = FXCollections.observableArrayList();
    }
    
    /**
     *This method clear the database, make it to the original mode
     */
    public void clearDatabase(){
        dataBase.clear();
    }
    
    public void clearDraftedPlayers(){
        draftedPlayers.clear();        
    }
    /**
     *This method tells us how to add players
     * @param player
     */
    public void addPlayer(Player player){
        dataBase.add(player);        
    }
    
    /**
     *This methods tells us how to add a list of players
     * @param players
     */
    public void addPlayers(ArrayList<Player> players){
        for(int i=0; i<players.size();i++){
        dataBase.add(players.get(i));
        }
    }

    /**
     *This method return database
     * @return ObservableList<Player>
     */
    public ObservableList<Player> getDataBase(){
        return dataBase;
    }
    
    /**
     *this method is used to remove a player
     * @param player
     */
    public void removePlayer(Player player){
        dataBase.remove(player);
    }
    
      /**
     *This method return draftedPlayers
     * @return ObservableList<Player>
     */
    public ObservableList<Player> getDraftedPlayers(){
        return draftedPlayers;
    }
      /**
     *This method tells us how to add a player to a draftedPlayers List
     * @param player
     */
    public void addPlayerToDraftedPlayers(Player player){
        draftedPlayers.add(player);
        player.setDraftPickNumber(draftedPlayers.size());
    }
    /**
     *this method is used to remove a player from a draftedPlayers list
     * @param player
     */
    public void removePlayerFromDrafedPlayers(Player player){
        //first I find out where is this player
        if(draftedPlayers.contains(player)){
         int position=draftedPlayers.indexOf(player);
         for(int i=position+1;i<draftedPlayers.size();i++){
             draftedPlayers.get(i).setDraftPickNumber(draftedPlayers.get(i).getDraftPickNumber()-1);
         }
        draftedPlayers.remove(player);
        }       
    }
    
    public String getDraftName(){
        return draftName;
    }
    public void setDraftName(String name){
        draftName=name;
    }
    
    public void addTeam(Team team){
        fantasyTeams.add(team);
    }
    
    public String getCurrentTeam(){
        return currentTeam;
    }
    
    public void setCurrentTeam(String team){
        currentTeam=team;
    }
    
    public void removeTeam(Team team){
        fantasyTeams.remove(team);
    }
    
    public ObservableList<Team> getTeams(){
        return fantasyTeams;
    }
   
    public int checkRemainTeams(){
        return 12-fantasyTeams.size();
    }
    
    public Boolean teamsFullInDraft(){
        if(checkRemainTeams()>0)
            return false;
        else
            return true;
    }
    
    public Team findTeam(String teamName){
        Team team=new Team();        
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName)){
        //     team.setOwnerName(fantasyTeams.get(i).getOwnerName());
        //     team.setTeamName(fantasyTeams.get(i).getTeamName());
        //     team.setTeamPlayers(fantasyTeams.get(i).getTeamPlayers());
        //     team.set1BFull(fantasyTeams.get(i).get1BFull());  
        //     team.setCIFull(fantasyTeams.get(i).getCIFull());
        //     team.set3BFull(fantasyTeams.get(i).get3BFull());
        //     team.set2BFull(fantasyTeams.get(i).get2BFull());
        //     team.setMIFull(fantasyTeams.get(i).getMIFull());
        //     team.setSSFull(fantasyTeams.get(i).getSSFull());
        //     team.setUFull(fantasyTeams.get(i).getUFull());             
             team=fantasyTeams.get(i);
            }                            
        }        
        return team;
    }
    
    public void assignedCToTeam(String teamName, int value){
          for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setCounterC(value);
        }  
    }
    
    public void assigned1BToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).set1BFull(value);                                        
        }        
    }
    
     public void assignedCIToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setCIFull(value);                                        
        }        
    }
     
      public void assigned3BToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).set3BFull(value);                                        
        }        
    }
      
       public void assigned2BToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).set2BFull(value);                                        
        }        
                
    }
       
        public void assignedMIToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setMIFull(value);                                        
        }        
    }
        
         public void assignedSSToTeam(String teamName,Boolean value){
        for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setSSFull(value);                                        
        }        
    }
      
          public void assignedUToTeam(String teamName,Boolean value){
             for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setUFull(value);                                        
        }        
    }
          
           public void assignedOFToTeam(String teamName, int value){
          for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setCounterOF(value);
        }  
    }
    
            public void assignedPToTeam(String teamName, int value){
          for(int i =0;i<fantasyTeams.size();i++){
            if(fantasyTeams.get(i).getTeamName().equals(teamName))
            fantasyTeams.get(i).setCounterP(value);
        }          
    }
            public void assignedTaxiNumberToTeam(String teamName, int value){
                for(int i=0;i<fantasyTeams.size();i++){
                    if(fantasyTeams.get(i).getTeamName().equals(teamName))
                        fantasyTeams.get(i).setTaxiPlayersNumber(value);
                }
            }
    
     public void clearFantasyTeams(){
         fantasyTeams.clear();
     }    
     
     public void clearPlayersDatabase(){
         dataBase.clear();
     }
     //In the fantasy Screen each team needs total points
     //This method is used to achieve that.
     public void assignPointsToTeams(){
         //CLEAR ALL THE POINTS IN THE TEAMS
         for(Team t: fantasyTeams){
                t.setTotalPoints(0);     
                    int totalHits=0; int totalAtBats=0; int totalEarnedRuns=0;
                    int totalIP=0; int totalWalk=0;
                       for(int i=0; i<t.getTeamPlayers().size();i++){
                         totalHits+=t.getTeamPlayers().get(i).getHits();
                         totalAtBats+=t.getTeamPlayers().get(i).getAtBats();
                         totalEarnedRuns+=t.getTeamPlayers().get(i).getEarnedRuns();
                         totalIP+=t.getTeamPlayers().get(i).getInningsPitched();
                         if(t.getTeamPlayers().get(i).getQualifyPositions().equals("P"))
                         totalWalk+=t.getTeamPlayers().get(i).getRunsWalk();
                          }
                       //I also calcalate BA,ERA, AND WHIP here.
                       //other stats are calculated in Team.java
                       if(totalAtBats!=0){
                  t.setBA((float)(totalHits)/totalAtBats);
                  t.setBA((float)(Math.round(t.getBA()*1000.0)/1000.0));
                       }
                       else{
                           t.setBA(0);
                       }
                  if(totalIP!=0){
                  t.setERA((float)(totalEarnedRuns)*9/totalIP);                  
                  t.setERA((float)(Math.round(t.getERA()*100.0)/100.0));
                   
                  t.setWHIP((float)(totalWalk+totalHits)/totalIP);
                  t.setWHIP((float)(Math.round(t.getWHIP()*100.0)/100.0));
                  }
                  else{
                      t.setERA(Float.POSITIVE_INFINITY);
                      t.setWHIP(Float.POSITIVE_INFINITY);
                  }
                 
            }
            
         //assign points for R
            Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getRun()<t2.getRun())?-1:
               (t1.getRun()>t2.getRun())?1:0;
           }
       });
             int value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            
           //assign points to HR 
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getHomeRuns()<t2.getHomeRuns())?-1:
               (t1.getHomeRuns()>t2.getHomeRuns())?1:0;
           }
       });
               value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            
            //ASSIGN POINT TO RBI
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getRBI()<t2.getRBI())?-1:
               (t1.getRBI()>t2.getRBI())?1:0;
           }
       });
               value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            
            //ASSIGN POINTS TO SB
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getSB()<t2.getSB())?-1:
               (t1.getSB()>t2.getSB())?1:0;
           }
       });
             value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value); value++;}
            
            //ASSIGN POINTS TO BA
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getBA()<t2.getBA())?-1:
               (t1.getBA()>t2.getBA())?1:0;
           }
       });
             value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            //ASSIGN POINTS TO W
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getWalk()<t2.getWalk())?-1:
               (t1.getWalk()>t2.getWalk())?1:0;
           }
       });
             value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            //ASSIGN POINTS TO K
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getStrikeouts()<t2.getStrikeouts())?-1:
               (t1.getStrikeouts()>t2.getStrikeouts())?1:0;
           }
       });
            value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
            
            //ASSIGN POINT TO SV
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getSave()<t2.getSave())?-1:
               (t1.getSave()>t2.getSave())?1:0;
           }
       });
             value=1;
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value++;}
           //ASSIGN POINT TO ERA 
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getERA()<t2.getERA())?-1:
               (t1.getERA()>t2.getERA())?1:0;
           }
       });
             value=fantasyTeams.size();
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value--;}
           //ASSIGN POINT TO WHIP 
              Collections.sort(fantasyTeams, new Comparator<Team>(){           
           public int compare(Team t1, Team t2){                              
               return (t1.getWHIP()<t2.getWHIP())?-1:
               (t1.getWHIP()>t2.getWHIP())?1:0;
           }
       });
             value=fantasyTeams.size();
            for(Team t: fantasyTeams){t.setTotalPoints(t.getTotalPoints()+value);value--;}
     }   
     
     
     
     
     
     //This method is used to calculate estimated value for each 
     //player in the free agent pool
     public void calculateEstimatedValue(){
         if(fantasyTeams.size()>0){
         int totalRemainedMoney=0;
         
         //(1)calculate the toal money remaining in the draft
         //only count the money for the teams with spots
         for(Team t: fantasyTeams){
             if(t.getPlayersNeeded()>0)
         totalRemainedMoney+=t.getMoneyLeft();
                 }
         //(2)calculate the rank among players in all 5 of their
         //categories, and determine the average rank of each player
         
         //depend on the player positions, put them into different lists
         ObservableList<Player> hitters=FXCollections.observableArrayList();
         ObservableList<Player> pitchers=FXCollections.observableArrayList();
         for(Player p : dataBase){
             if(p.getQualifyPositions().equals("P"))
                 pitchers.add(p);
             else
                 hitters.add(p);
         }
         //get R ranks
        Collections.sort(hitters, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getRunsWalk()<p2.getRunsWalk())?-1:
               (p1.getRunsWalk()>p2.getRunsWalk())?1:0;
           }
       });
              int rank=hitters.size();
              for(Player p: hitters){
                  p.setRank_R_W(rank);
                  rank--;
              }
         //get HR ranks
        Collections.sort(hitters, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getHomeRunsSaves()<p2.getHomeRunsSaves())?-1:
               (p1.getHomeRunsSaves()>p2.getHomeRunsSaves())?1:0;
           }
       });
                 rank=hitters.size();
              for(Player p: hitters){
                  p.setRank_HR_SV(rank);
                  rank--;
              }
         //get RBI ranks
        Collections.sort(hitters, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getRunsBattedInStrikeouts()<p2.getRunsBattedInStrikeouts())?-1:
               (p1.getRunsBattedInStrikeouts()>p2.getRunsBattedInStrikeouts())?1:0;
           }
       });
                rank=hitters.size();
              for(Player p: hitters){
                  p.setRank_RBI_W(rank);
                  rank--;
              }
         //get SB ranks
        Collections.sort(hitters, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getSB_ERA()<p2.getSB_ERA())?-1:
               (p1.getSB_ERA()>p2.getSB_ERA())?1:0;
           }
       });
                 rank=hitters.size();
              for(Player p: hitters){
                  p.setRank_SB_ERA(rank);
                  rank--;
              }
         //get BA ranks
        Collections.sort(hitters, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getBA_WHIP()<p2.getBA_WHIP())?-1:
               (p1.getBA_WHIP()>p2.getBA_WHIP())?1:0;
           }
       });
               rank=hitters.size();
              for(Player p: hitters){
                  p.setRank_BA_WHIP(rank);
                  rank--;
              }
         //calculate the average the rank for each hitters
              for(Player p: hitters){
               p.setAverageRank((float)(p.getRank_R_W()+p.getRank_BA_WHIP()+p.getRank_HR_SV()+p.getRank_RBI_W()
                       +p.getRank_SB_ERA())/5);
              }      
        //get W ranks
        Collections.sort(pitchers, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getRunsWalk()<p2.getRunsWalk())?-1:
               (p1.getRunsWalk()>p2.getRunsWalk())?1:0;
           }
       });
                 rank=pitchers.size();
              for(Player p: pitchers){
                  p.setRank_R_W(rank);
                  rank--;
              }
         //get save ranks
        Collections.sort(pitchers, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getHomeRunsSaves()<p2.getHomeRunsSaves())?-1:
               (p1.getHomeRunsSaves()>p2.getHomeRunsSaves())?1:0;
           }
       });
                 rank=pitchers.size();
              for(Player p: pitchers){
                  p.setRank_HR_SV(rank);
                  rank--;
              }
         //get strikeouts ranks
        Collections.sort(pitchers, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getRunsBattedInStrikeouts()<p2.getRunsBattedInStrikeouts())?-1:
               (p1.getRunsBattedInStrikeouts()>p2.getRunsBattedInStrikeouts())?1:0;
           }
       });
                rank=pitchers.size();
              for(Player p: pitchers){
                  p.setRank_RBI_W(rank);
                  rank--;
              }
         //get ERA ranks
        Collections.sort(pitchers, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getSB_ERA()<p2.getSB_ERA())?-1:
               (p1.getSB_ERA()>p2.getSB_ERA())?1:0;
           }
       });
                 rank=1;
              for(Player p: pitchers){
                  p.setRank_SB_ERA(rank);
                  rank++;
              }
         //get WHIP ranks
        Collections.sort(pitchers, new Comparator<Player>(){           
             public int compare(Player p1, Player p2){                              
               return (p1.getBA_WHIP()<p2.getBA_WHIP())?-1:
               (p1.getBA_WHIP()>p2.getBA_WHIP())?1:0;
           }
       });
               rank=1;
              for(Player p: pitchers){
                  p.setRank_BA_WHIP(rank);
                  rank++;
              }
             //calculate the average the rank for each pitchers
              for(Player p: pitchers){
               p.setAverageRank((float)(p.getRank_R_W()+p.getRank_BA_WHIP()+p.getRank_HR_SV()+p.getRank_RBI_W()
                       +p.getRank_SB_ERA())/5);
              }
             
         //(3)Now we know the top X hitters and Y pitchers.
         //X is the number of hitters needed to be drafted
         //Y is the number of pitchers needed to be drafted
              int top_X_Hitters=0;
              int top_Y_Pitchers=0;
              int pitchers_already_have=0;
          for(Team t: fantasyTeams){
             if(t.getPlayersNeeded()>0)
                  for(int i=0;i<t.getTeamPlayers().size();i++){
                      if(t.getTeamPlayers().get(i).getQualifyPositions().equals("P"))
                          pitchers_already_have++;
                  }
             top_Y_Pitchers+=9-pitchers_already_have;
             top_X_Hitters+=t.getPlayersNeeded()-(9-pitchers_already_have);
                 }
         //(4)calculate median salary: total $/2X for hitters
         //                         total $/2Y for pitchers
               float medianSalaryForHitters=0;
               float medianSalaryForPitchers=0;
               if(top_X_Hitters!=0){
               medianSalaryForHitters=totalRemainedMoney/(2*top_X_Hitters);
               }
               if(top_Y_Pitchers!=0){
               medianSalaryForPitchers=totalRemainedMoney/(2*top_Y_Pitchers);
               }
               if(top_X_Hitters==0){
               medianSalaryForHitters=0;
               }
               if(top_Y_Pitchers==0){
               medianSalaryForPitchers=0;
               }
         
         //(5)estimated value= median salary*(2X/rank) for hitters
         //                 median salary*(2Y/rank) for pithcers
               if(medianSalaryForHitters!=0){
               for(Player p: hitters){
                   p.setEstimatedValue(medianSalaryForHitters*(2*top_X_Hitters/p.getAverageRank()));
                   p.setEstimatedValue((float)(Math.round(p.getEstimatedValue()*1000.0)/1000.0));
               }
               }
               else{
                   for(Player p: hitters){
                       p.setEstimatedValue(0);
                   }
               }
                 if(medianSalaryForPitchers!=0){   
                for(Player p: pitchers){
                   p.setEstimatedValue(medianSalaryForHitters*(2*top_X_Hitters/p.getAverageRank()));
                    p.setEstimatedValue((float)(Math.round(p.getEstimatedValue()*1000.0)/1000.0));
                }
                 }
                 else{
                     for(Player p:pitchers)
                         p.setEstimatedValue(0);
                 }
               
            dataBase.clear();
            for(Player p: hitters)
                 dataBase.add(p);
            for(Player p: pitchers)
                 dataBase.add(p);
                
    }
         else{
             for(Player p: dataBase)
                 p.setEstimatedValue(0);
         }
   }
     
     
     
}
