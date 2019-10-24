import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UserInterface extends Application {

    public static void main(String[] args) {
        launch(args);}

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Database Viewer");

        Group root = new Group();
        root.getChildren().add(getVBox());

        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();
    }

    private VBox getVBox() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        vbox.getChildren().add(getHBox());
        vbox.getChildren().add(BuildPlayerTable());
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
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    @Override
                    public TableCell call(TableColumn param) {
                        return new TableCell<Player, Double>();
                    }
                };
        TableColumn columnName = new TableColumn("Name");
        columnName.setCellValueFactory(
                new PropertyValueFactory<Player,String>("name"));
        columnName.setMinWidth(60);

        TableColumn columnValue1 = new TableColumn("Club");
        columnValue1.setCellValueFactory(
                new PropertyValueFactory<Player,Double>("club"));
        columnValue1.setMinWidth(60);

        TableColumn columnValue2 = new TableColumn("Age");
        columnValue2.setCellValueFactory(
                new PropertyValueFactory<Player,Double>("age"));
        columnValue2.setMinWidth(60);
        return tbView;
    }
}
