import java.io.IOException;
import java.sql.*;

public class DataBaseConnector {

    private Connection con;

    public DataBaseConnector(){
        try {
            String dbUser = "myuser";
            String usrPass = "mypass";
            Class.forName("org.mariadb.jdbc.Driver");

            String url = "jdbc:mysql://localhost/mydbTesting";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Player.setNext_id(countPlayers()+1);
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            System.out.println("Error in DBSaver constructor");
        }
    }

    public boolean savePlayer(Player p){
//        System.out.println("started savePlayer");
        try {
            Club c = p.getClub();
//            if(c!=null){
//                System.out.println("club exists");
//            }
            saveClub(c);
            String name = p.getName();
            int id = p.getPlayer_id();
            int club = c.getId();
            String position = p.getPosition();
            int age = p.getAge();
            Double mv = p.getMarket_value();
            String nat = p.getNationality();

//            System.out.println("name: " + name +
//                    ", id: " + id +
//                    ", club: " + club +
//                    ", pos: " + position +
//                    ", age: " + age +
//                    ", mv: " + mv +
//                    ", nat: " + nat);

            String check = "Select * from players where id = ?";
            PreparedStatement checkStmt = con.prepareStatement(check);
            checkStmt.setInt(1,id);
            ResultSet rs = checkStmt.executeQuery();
            if(rs.next()){
//                System.out.println("found matching id");
                    return false;
            }
            else {
                String checkN = "Select * from players where name = ?";
                PreparedStatement checkName = con.prepareStatement(checkN);
                checkName.setString(1,name);
                ResultSet rs2 = checkName.executeQuery();
                while(rs2.next()){
                    System.out.println("Found existing player named " + name);
                    //if player id already there, skip
                    //if name, club and age match then assume same person and skip to avoid duplicates
                    if((rs2.getInt("club")==club && rs2.getInt("age")==age)){
                        return false;
                    }
                }
                String prep = "INSERT INTO players (id, name, club, position, age, market_value, nationality) VALUES (?,?, ?, ?, ?,?,?)";

                PreparedStatement ps = null;
                ps = con.prepareStatement(prep);

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setInt(3, club);
                ps.setString(4, position);
                ps.setInt(5, age);
                ps.setDouble(6, mv);
                ps.setString(7, nat);

                ps.executeUpdate();
//                System.out.println("finished savePlayer");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in savePlayer to DB");
            return false;
        }

    }

    public boolean saveClub(Club c){
//        System.out.println("started saveClub");
        try{

            String name = c.getName();
            int id = c.getId();

            String check = "Select * from clubs where id = ?";
            PreparedStatement checkStmt = con.prepareStatement(check);
            checkStmt.setInt(1,id);
            ResultSet rs = checkStmt.executeQuery();
            if(rs.next()){
                return false;
            }
            else {
                String prep = "INSERT INTO clubs (id, name) VALUES (?,?)";

                PreparedStatement ps = null;
                ps = con.prepareStatement(prep);

                ps.setInt(1, id);
                ps.setString(2, name);

                ps.executeUpdate();
//                System.out.println("finished save Club");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in saveClub to DB");
            return false;
        }

    }

    public String printPlayerByName(String name){
        try {
            String prep = "Select * FROM players WHERE name = ?";

            PreparedStatement ps = null;
            ps = con.prepareStatement(prep);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return ("Player: " + rs.getString("name")
                        + ", club_id: " + rs.getInt("club")
                        + ", position: " + rs.getString("position")
                        + ", age: " + rs.getInt("age")
                        + ", market value: " + rs.getDouble("market_value"));
            }
            return "Unable to find player";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in getPlayerByName from db");
            return "error in method";
        }
    }

    public Player getPlayer(String name){
        try {
            String prep = "Select * FROM players WHERE name = ?";

            PreparedStatement ps = null;
            ps = con.prepareStatement(prep);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int club_id = rs.getInt("club");
                String position = rs.getString("position");
                int age = rs.getInt("age");
                Double mv = rs.getDouble("market_value");
                String nationality = rs.getString("nationality");
                //search for club name
                String clubSql = "Select * FROM clubs WHERE id = ?";

                PreparedStatement pstmt = con.prepareStatement(clubSql);

                pstmt.setInt(1, club_id);
                ResultSet rsClub = pstmt.executeQuery();
                String clubName = "Unknown";
                if(rsClub.next()){
                    clubName = rs.getString("name");
                }
                Club c = new Club(clubName, club_id);
                Player p = new Player();
                p.setName(name);
                p.setClub(c);
                p.setPlayer_id(id);
                p.setMarket_value(mv);
//                p.setClub_id(club_id);
                p.setAge(age);
                p.setNationality(nationality);
                p.setPosition(position);
                return p;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in getPlayerByName from db");
            return null;
        }
    }

    public int countPlayers(){
        try{
            int count = 0;
            Statement stmt = con.createStatement();

            String sql = "select name from players";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                count++;
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in countPlayers");
            return -1;
        }
    }

    public String removePlayer(Player p){
        try{
            String sql = "DELETE FROM players WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getPlayer_id());
            int result = ps.executeUpdate();
            if(result==1){
                return("Player: " + p.getName() + " removed");
            }
            else if(result>1){
                return("Somehow multiple lines were changed");
            }
            else{
                return("seems nothing was removed");
            }

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return "Unable to locate player";
        }
    }

    public void clearTables(){
        try {
            Statement playerStmt = con.createStatement();
            String playerSql = "delete from players";
            playerStmt.executeUpdate(playerSql);
            Statement clubStmt = con.createStatement();
            String clubsql = "delete from clubs";
            clubStmt.executeUpdate(clubsql);
        } catch (SQLException e) {
            System.out.println("error in clear tables");
            e.printStackTrace();
        }
    }
}
