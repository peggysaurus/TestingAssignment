import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class DataLoader{

    public boolean loadFile(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            NodeList nList = doc.getElementsByTagName("row");

//            ArrayList<Player> playerList = new ArrayList<Player>();
//            ArrayList<Club> clubs = new ArrayList<Club>();

            DataBaseConnector dbSaver = new DataBaseConnector();

            for (int i = 0; i < nList.getLength(); i++) {

                Node n = nList.item(i);

                Element e = (Element) n;
                Player p = loadPlayerXML(e);
                if(!dbSaver.savePlayer(p)) {
                    System.out.println("A player was not saved");
                }
            }
            return true;
            } catch(Exception e) {
            System.out.println("error in loadFile: " + e);
            return false;
            }
        }

        public Player loadPlayerXML (Element e){
//            String name = e.getElementsByTagName("name").item(0).getTextContent();

            Player p = new Player();
            String club;
            int club_id;
            try {
                club = e.getElementsByTagName("club").item(0).getTextContent();
            }catch(NullPointerException ex){
                club = "Unknown";
            }
            try {
                club_id = Integer.parseInt(e.getElementsByTagName("club_id").item(0).getTextContent());
            }catch(NullPointerException ex){
                club_id = 0;
            }
            Club c = new Club(club,club_id);
            p.setClub(c);

            try{
                Double mv = Double.parseDouble(e.getElementsByTagName("market_value").item(0).getTextContent());
                p.setMarket_value(mv);
            } catch (NumberFormatException e1){
                p.setMarket_value(0.0);
            }

            for(String s : playerStringValues()){
                try {
                    String x = e.getElementsByTagName(s).item(0).getTextContent();
                    setPlayerValue(p,s,x);
                } catch (NumberFormatException | NullPointerException ex) {
                    setPlayerValue(p,s,"Unknown");
                }
            }
            for(String s : playerIntValues()){
                try {
                    int x = Integer.parseInt(e.getElementsByTagName(s).item(0).getTextContent());
                    setPlayerValue(p,s,x);
                } catch (NumberFormatException | NullPointerException ex) {
                    setPlayerValue(p,s,0);
                }
            }
//            Double.parseDouble(e.getElementsByTagName("fpl_value").item(0).getTextContent()),
//            e.getElementsByTagName("fpl_sel").item(0).getTextContent();
//            System.out.println("created player: " + p.getName());
            return p;
        }

        private ArrayList <String> playerIntValues(){
            ArrayList <String> intValues = new ArrayList<String>();
            intValues.add("age");
            intValues.add("position_cat");
            intValues.add("page_views");
            intValues.add("fpl_points");
            intValues.add("new_foreign");
            intValues.add("region");
            intValues.add("age_cat");
//            intValues.add("club_id");
            intValues.add("big_club");
            intValues.add("new_signing");
            return intValues;
        }

        private ArrayList<String> playerStringValues(){
            ArrayList <String> StringValues = new ArrayList<String>();
            StringValues.add("name");
//            StringValues.add("club");
            StringValues.add("position");
            StringValues.add("nationality");
            StringValues.add("fpl_sel");
            return StringValues;
        }

        private void setPlayerValue(Player p, String field, int val){
            if(field.equals("age")){
                p.setAge(val);
            }
            if(field.equals("position_cat")){
                p.setPosition_cat(val);
            }
            if(field.equals("page_views")){
                p.setPage_views(val);
            }
            if(field.equals("fpl_points")){
                p.setFpl_points(val);
            }
            if(field.equals("new_foreign")){
                p.setNew_foreign(val);
            }
            if(field.equals("region")){
                p.setRegion(val);
            }
            if(field.equals("age_cat")){
                p.setAge_cat(val);
            }
//            if(field.equals("club_id")){
//                p.setClub_id(val);
//            }
            if(field.equals("big_club")){
                p.setBig_club(val);
            }
            if(field.equals("new_signing")){
                p.setNew_signing(val);
            }
        }

        private void setPlayerValue(Player p, String field, String val) {
            if(field.equals("name")){
                p.setName(val);
            }
//            if(field.equals("club")){
//                p.setClub(val);
//            }
            if(field.equals("position")){
                p.setPosition(val);
            }
            if(field.equals("nationality")){
                p.setNationality(val);
            }
            if(field.equals("fpl_sel")){
                p.setFpl_sel(val);
            }
        }

}
