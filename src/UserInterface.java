import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserInterface extends Application {
    protected Scene scene;
    private Stage stage;
    private TableView playerView;
    private ScrollPane sp;

    public static void main(String[] args) {
        launch(args);}

    @Override
    public void start(Stage primaryStage) {
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
        Text txt = new Text("Put buttons here");
        hbox.getChildren().add(txt);
        return hbox;
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
        TableColumn col1 = new TableColumn("Name");
        TableColumn col2 = new TableColumn("Club");
        TableColumn col3 = new TableColumn("Age");
        TableColumn col4 = new TableColumn("Position");
        TableColumn col5 = new TableColumn("Nationality");
        TableColumn col6 = new TableColumn("Market Value");

        tbView.getColumns().addAll(col1,col2,col3,col4,col5,col6);

        for(int i = 0; i<tbView.getColumns().size(); i++){
            TableColumn tc = (TableColumn)tbView.getColumns().get(i);
//            System.out.println();
            Double wd = 60.0;
            tc.setMinWidth(wd);
        }

        tbView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        return tbView;
    }
}
