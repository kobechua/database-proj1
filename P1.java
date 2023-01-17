import java.util.ArrayList;
import java.io.*;
public class P1 {
	
	/* Define data structures for holding the data here */
    public class coach{
        String CoachID = "";
        String Team = "";
        String FirstName = "";
        String LastName = "";
        int Season = 0;
        int seasonWin = 0;
        int seasonLoss = 0;
        int playoffWin = 0;
        int playoffLost = 0;

        public coach(String coachID, int szn, String firstName, String lastName, int sznWin, int sznLoss, int pfWin, int pfLost, String team){
            this.CoachID = coachID;
            this.Team = team;
            this.FirstName = firstName;
            this.LastName = lastName;
            this.Season = szn;
            this.seasonWin = sznWin;
            this.seasonLoss = sznLoss;
            this.playoffWin = pfWin;
            this.playoffLost = pfLost;
        }

    }

    public class team{
        String TeamID = "";
        String Location = "";
        String Name = "";
        Character League = '\n';

        public team(String teamID, String location, String name, Character league){
            this.TeamID = teamID;
            this.Location = location;
            this.Name = name;
            this.League = league;
        }
    }

    ArrayList<coach> CoachList = new ArrayList<coach>();
    ArrayList<team> TeamsList = new ArrayList<team>();

    public P1() {
        CoachList.clear();
        TeamsList.clear();
    }
    
    public void run(){
        CommandParser parser = new CommandParser();

        System.out.println("The mini-DB of NBA coaches and teams");
        System.out.println("Please enter a command.  Enter 'help' for a list of commands.");
        System.out.println();
        System.out.print("> "); 
        
        Command cmd = null;
        while ((cmd = parser.fetchCommand()) != null) {
            //System.out.println(cmd);
            
            boolean result=false;
            
            if (cmd.getCommand().equals("help")) {
                result = doHelp();
            } 

            else if (cmd.getCommand().equals("add_coach")) {
                coach newCoach = new coach(cmd.getParameters()[0], Integer.parseInt(cmd.getParameters()[1]), cmd.getParameters()[2], cmd.getParameters()[3], Integer.parseInt(cmd.getParameters()[4]), Integer.parseInt(cmd.getParameters()[5]), Integer.parseInt(cmd.getParameters()[6]), Integer.parseInt(cmd.getParameters()[7]), cmd.getParameters()[8]);
                CoachList.add(newCoach);           
            }

            else if (cmd.getCommand().equals("add_team")) {
                if (cmd.getParameters().length > 4){
                    cmd.getParameters()[1] += " " + cmd.getParameters()[2]; 
                    team newTeam = new team(cmd.getParameters()[0], cmd.getParameters()[1], cmd.getParameters()[3], cmd.getParameters()[4].charAt(0));
                    TeamsList.add(newTeam);
                }
                else{
                    team newTeam = new team(cmd.getParameters()[0], cmd.getParameters()[1], cmd.getParameters()[2], cmd.getParameters()[4].charAt(0));
                    TeamsList.add(newTeam);
                }
            }

            else if (cmd.getCommand().equals("print_coaches")) {
                for(coach i : CoachList){
                    System.out.format("%.9S, %4s, %s, %s, %d, %d, %d, %d, %S\n", i.CoachID, i.Season, i.FirstName, i.LastName, i.seasonWin, i.seasonLoss, i.playoffWin, i.playoffLost, i.Team);
                }
            }
            else if (cmd.getCommand().equals("print_teams")) {
                for(team i : TeamsList){
                    System.out.format("%S, %s, %s, %s\n", i.TeamID, i.Location, i.Name, i.League);
                }
            }
            else if (cmd.getCommand().equals("coaches_by_name")) {
                for (coach i : CoachList){
                    if (i.LastName == cmd.getParameters()[0]){
                        System.out.format("%.9S, %4s, %s, %s, %d, %d, %d, %d, %S\n", i.CoachID, i.Season, i.FirstName, i.LastName, i.seasonWin, i.seasonLoss, i.playoffWin, i.playoffLost, i.Team);
                        for (team j : TeamsList){
                            if (i.Team == j.Name){
                                System.out.format("%S", j.TeamID);
                                
                            }
                        }
                    }
                }
            }

            else if (cmd.getCommand().equals("teams_by_city")) {
                for(team i : TeamsList){
                    if (i.Location == cmd.getParameters()[0]){
                        System.out.format("%S, %s, %s, %s\n", i.TeamID, i.Location, i.Name, i.League);
                        for (coach j : CoachList){
                            if(j.Team == i.TeamID){
                                System.out.format("%s %s\n", j.FirstName, j.LastName);
                            }
                        }
                    }
                }
            }

            else if (cmd.getCommand().equals("load_coaches")) {

                try{
                    File inputFile = new File(cmd.getParameters()[0]);
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));

                    String line;
                    
                    while ((line = reader.readLine()) != null){
                        String[] info = line.split(",");
                        System.out.printf(info[1], "\n");
                    }
                    reader.close();
                }
                catch(Exception e){
                    System.out.printf("File not found");
                    continue;
                }
                

            }
            else if (cmd.getCommand().equals("load_teams")) {
            
            }
            else if (cmd.getCommand().equals("best_coach")) {

            }
            else if (cmd.getCommand().equals("search_coaches")) {

            }
            else if (cmd.getCommand().equals("exit")) {
                System.out.println("Leaving the database, goodbye!");
                break;
            }
            else if (cmd.getCommand().equals("")) {
            }
            else {
                System.out.println("Invalid Command, try again!");
            } 
                
            if (result) {
                    // ...
            }

            System.out.print("> "); 
        }        
    }
    
    private boolean doHelp() {
        System.out.println("add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN "); 
	    System.out.println("          EASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
        System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
        System.out.println("print_coaches - print a listing of all coaches");
        System.out.println("print_teams - print a listing of all teams");
        System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
        System.out.println("teams_by_city CITY - list the teams in the specified city");
	    System.out.println("load_coach FILENAME - bulk load of coach info from a file");
        System.out.println("load_team FILENAME - bulk load of team info from a file");
        System.out.println("best_coach SEASON - print the name of the coach with the most netwins in a specified season");
        System.out.println("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
		System.out.println("exit - quit the program");        
        return true;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new P1().run();
    }
    
    

}
