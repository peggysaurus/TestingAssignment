import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Optional;


public class UserInterface extends Application {
    protected Scene scene;
    private Stage stage;
    private TableView playerView;
//    private ScrollPane sp;
    private DataLoader dl;
    private DataBaseConnector db;

    public static void main(String[] args) {
        launch(args);}

    @Override
    public void start(Stage primaryStage) {
        dl = new DataLoader();

        db = new DataBaseConnector();
        this.stage = primaryStage;
        primaryStage.setTitle("Database Viewer");
//        System.out.println("before set root");
        Group root = new Group();
//        sp = new ScrollPane(getVBox());
//        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        sp.setFitToWidth(true);
        root.getChildren().add(getVBox());
//        System.out.println("before set scene");

        scene = new Scene(root, 800, 350);
        primaryStage.setScene(scene);
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            autoSizeCols();
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            autoSizeCols();
        });
        primaryStage.show();
        autoSizeCols();
        loadTable();
    }

    private void autoSizeCols() {
        playerView.setPrefHeight(stage.getHeight()-70);
        playerView.setPrefWidth(stage.getWidth()-15);
        for(int i = 0; i < playerView.getColumns().size(); i++){
            TableColumn tc = (TableColumn)playerView.getColumns().get(i);
            tc.setPrefWidth((stage.getWidth()-15)/playerView.getColumns().size());
        }
    }

    private VBox getVBox() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.getChildren().add(getHBox());
        playerView = BuildPlayerTable();

        vbox.getChildren().add(playerView);
        return vbox;
    }

    private HBox getHBox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10,10,0,0));
        hbox.setSpacing(10);
        Button loadBtn = new Button();
        loadBtn.setText("Load XML file");
        loadBtn.setOnAction(loadFile());
        Button addPBtn = new Button();
        addPBtn.setText("Add Player");
        addPBtn.setOnAction(addPlayer());
        Button addCBtn = new Button();
        addCBtn.setText("Add Club");
        addCBtn.setOnAction(addClub());
        TextField search = new TextField();
        search.setOnAction(search());
        Text searchLabel = new Text("Search: ");
//        Text txt = new Text("Put buttons here");
        //TODO make a delete selected button
        Button clearBtn = new Button();
        clearBtn.setText("Clear Database");
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                db.clearTables();
                clearTable();
            }
        });
        hbox.getChildren().addAll(loadBtn,addPBtn,addCBtn,searchLabel,search, clearBtn);
        return hbox;
    }

    private EventHandler<ActionEvent> search() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO method to search by text
            }
        };
    }

    private EventHandler<ActionEvent> addClub() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Club c = null;
                Dialog <Club>dialog = getClubDialog();
                Optional<Club> result = dialog.showAndWait();
                if (result.isPresent()) {
                    c=result.get();
                }
                if(c!=null)
                    db.saveClub(c);
            }
        };
    }

    private EventHandler<ActionEvent> addPlayer() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player p = null;
                Dialog <Player>dialog = getPlayerDialog();
//                dialog.show();
                Optional<Player> result = dialog.showAndWait();
                if (result.isPresent()) {
                    p=result.get();
                }
                if(p!=null)
//                System.out.println(p.toString());
                db.savePlayer(p);
                playerView.getItems().add(p);
            }
        };
    }

    private Dialog<Club> getClubDialog(){
        Dialog <Club> dialog = new Dialog <>();
        ButtonType okbtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okbtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        Text header = new Text("Add new club");
        TextField name = new TextField();
        Text nameLabel = new Text("Name: ");

        grid.setAlignment(Pos.CENTER);
        grid.add(header,1,0);
        grid.setColumnSpan(header,2);
        grid.add(nameLabel,0,1);
        grid.add(name,2,1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okbtn) {
                String clubName = name.getText();
                int clubID = db.countClubs();
                while(db.findClub(clubID)!=null){
                    clubID++;
                }
                return new Club(clubName,clubID);
            }
            return null;
        });

        return dialog;
    }

    private Dialog<Player> getPlayerDialog(){
        Dialog <Player> dialog = new Dialog <>();
        ButtonType okbtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okbtn, ButtonType.CANCEL);


        GridPane grid = new GridPane();

        Text header = new Text("Add new player");
        TextField name = new TextField();
        Text nameLabel = new Text("Name: ");

        ArrayList <Club> clubs = db.getClubsArray();
        ObservableList<String> options = FXCollections.observableArrayList();
        for (Club c : clubs) {
            options.add(c.getName());
        }
        ComboBox comboBox = new ComboBox(options);
        Text clubLabel = new Text("Club: ");

        TextField age = new TextField();
        Text ageLabel = new Text("Age: ");

        TextField position = new TextField();
        Text posLabel = new Text("Position: ");

        TextField nationality = new TextField();
        Text natLabel = new Text("Nationality: ");

        TextField mv = new TextField();
        Text mvLabel = new Text("Market Value: ");


        grid.setAlignment(Pos.CENTER);
        grid.add(header,1,0);
        grid.setColumnSpan(header,2);
        grid.add(nameLabel,0,1);
        grid.add(name,2,1);
        grid.add(clubLabel,0,2);
        grid.add(comboBox,2,2);
        grid.add(ageLabel,0,3);
        grid.add(age,2,3);
        grid.add(posLabel,0,4);
        grid.add(position,2,4);
        grid.add(natLabel,0,5);
        grid.add(nationality,2,5);
        grid.add(mvLabel,0,6);
        grid.add(mv,2,6);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okbtn) {
                Player p = new Player();
                p.setName(name.getText());

                try {
                p.setAge(Integer.parseInt(age.getText()));
                } catch (NumberFormatException e) {
                    p.setAge(0);
                }
                p.setPosition(position.getText());
                p.setNationality(nationality.getText());
                try{
                p.setMarket_value(Double.parseDouble(mv.getText()));} catch (NumberFormatException e){
                    p.setMarket_value(0.0);
                }

                String clubName = comboBox.getValue().toString();
                Club c = null;
                for (Club i : clubs){
                    if(i.getName().equals(clubName)){
                        c = i;
                    }
                }
                if(c == null){
                    c = new Club("Unknown",clubs.size());
                }

                p.setClub(c);
                return p;
            }
            return null;
        });

        return dialog;
    }

    private EventHandler<ActionEvent> loadFile() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerView.setPlaceholder(new Text("loading..."));
                FileChooser chooser = new FileChooser();
                chooser.setTitle("File chooser");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
                File file = chooser.showOpenDialog(stage);
                dl.loadFile(file);
                loadTable();
            }
        };
    }

    private TableView BuildPlayerTable(){
        TableView tbView = new TableView ();
        tbView.setEditable(true);
//        System.out.println(scene.);
//        tbView.prefWidthProperty().bind(stage.widthProperty());
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        return new TableCell<Player, Double>();
                    }
                };
        TableColumn <String,Player> col1 = new TableColumn("Name");
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn <String,Player> col2 = new TableColumn("Club");
        col2.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        TableColumn <Integer,Player> col3 = new TableColumn("Age");
        col3.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn <String,Player> col4 = new TableColumn("Position");
        col4.setCellValueFactory(new PropertyValueFactory<>("position"));
        TableColumn <String,Player> col5 = new TableColumn("Nationality");
        col5.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        TableColumn <Double,Player> col6 = new TableColumn("Market Value");
        col6.setCellValueFactory(new PropertyValueFactory<>("market_value"));

        tbView.getColumns().addAll(col1,col2,col3,col4,col5,col6);
//tbView.getColumns().add(col1);
        for(int i = 0; i<tbView.getColumns().size(); i++){
            TableColumn tc = (TableColumn)tbView.getColumns().get(i);
//            System.out.println();
            Double wd = 60.0;
            tc.setMinWidth(wd);
        }

        tbView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tbView;
    }

    public void loadTable(){

        db.getAllPlayers(playerView);
//        System.out.println("Got player array of " + players.size());
//        for (Player p : players){
//            playerView.getItems().add(p);
//        }
        playerView.setPlaceholder(new Text("No content in table"));
    }

    public void clearTable(){
        playerView.getItems().clear();
    }
}
