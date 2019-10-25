import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;


public class UserInterface extends Application {
    protected Scene scene;
    private Stage stage;
    private TableView playerView;
    private ScrollPane sp;
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
        sp = new ScrollPane(getVBox());
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setFitToWidth(true);
        root.getChildren().add(sp);
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
        sp.setPrefViewportHeight(stage.getHeight()-55);
        playerView.setPrefWidth(stage.getWidth()-30);
        for(int i = 0; i < playerView.getColumns().size(); i++){
            TableColumn tc = (TableColumn)playerView.getColumns().get(i);
            tc.setPrefWidth((stage.getWidth()-30)/playerView.getColumns().size());
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
        //TODO method to add club from UI
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
    }

    private EventHandler<ActionEvent> addPlayer() {
        //TODO method to add player from UI
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
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
