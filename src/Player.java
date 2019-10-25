public class Player {
    private static int next_id = 1;
    private int player_id;
    private String name;
    private Club club;
    private String position;
    private String nationality;
    private Double market_value;
    private int age;
    private int position_cat;
    private int page_views;
    private Double fpl_value;
    private int fpl_points;
    private int new_foreign;
    private int region;
    private int age_cat;
//    private int club_id;
    private int big_club;
    private int new_signing;
    private String fpl_sel;

    public Player (){
//        this.setName(name);
//        this.setClub(club);
        this.setPlayer_id(this.next_id++);
    }

    public String toString(){
        return "Player: " + name + ", " + club.getName() + ", "+ age + ", "+ nationality + ", "+ position;
    }

    public static int getNext_id() {
        return next_id;
    }

    public static void setNext_id(int next_id) {
        Player.next_id = next_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public String getClubName(){
        return club.getName();
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Double getMarket_value() {
        return market_value;
    }

    public void setMarket_value(Double market_value) {
        this.market_value = market_value;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPosition_cat() {
        return position_cat;
    }

    public void setPosition_cat(int position_cat) {
        this.position_cat = position_cat;
    }

    public int getPage_views() {
        return page_views;
    }

    public void setPage_views(int page_views) {
        this.page_views = page_views;
    }

    public Double getFpl_value() {
        return fpl_value;
    }

    public void setFpl_value(Double fpl_value) {
        this.fpl_value = fpl_value;
    }

    public int getFpl_points() {
        return fpl_points;
    }

    public void setFpl_points(int fpl_points) {
        this.fpl_points = fpl_points;
    }

    public int getNew_foreign() {
        return new_foreign;
    }

    public void setNew_foreign(int new_foreign) {
        this.new_foreign = new_foreign;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getAge_cat() {
        return age_cat;
    }

    public void setAge_cat(int age_cat) {
        this.age_cat = age_cat;
    }

//    public int getClub_id() {
//        return club_id;
//    }
//
//    public void setClub_id(int club_id) {
//        this.club_id = club_id;
//    }

    public int getBig_club() {
        return big_club;
    }

    public void setBig_club(int big_club) {
        this.big_club = big_club;
    }

    public int getNew_signing() {
        return new_signing;
    }

    public void setNew_signing(int new_signing) {
        this.new_signing = new_signing;
    }

    public String getFpl_sel() {
        return fpl_sel;
    }

    public void setFpl_sel(String fpl_sel) {
        this.fpl_sel = fpl_sel;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(Player.class)){
            Player o = (Player)obj;
            if (this.getPlayer_id() == o.getPlayer_id()){
                if(this.getName().equals(o.getName())){
                    if(this.getNationality().equals(o.getNationality())){
                        if(this.getClub().equals(o.getClub())){
                            if(this.getAge()==o.getAge()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
