import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
// import javafx.scene.layout.Border;
// import javafx.scene.layout.BorderStroke;
// import javafx.scene.layout.BorderStrokeStyle;
// import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.text.Font;

// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Text;


public class App extends Application {
    TableView<Buku> tableView = new TableView<Buku>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("DATA BUKU");
        TableColumn<Buku, String> columnId = new TableColumn<>("ID");
        columnId.setCellValueFactory(new PropertyValueFactory<>("Idbuku"));

        TableColumn<Buku, String> columnJudul = new TableColumn<>("Judul Buku");
        columnJudul.setCellValueFactory(new PropertyValueFactory<>("Judul"));

        TableColumn<Buku, String> columnJenis = new TableColumn<>("Jenis Buku");
        columnJenis.setCellValueFactory(new PropertyValueFactory<>("Jenis"));

        tableView.getColumns().add(columnId);
        tableView.getColumns().add(columnJudul);
        tableView.getColumns().add(columnJenis);

        ToolBar toolBar = new ToolBar();

        //Button Stage dan tampilan awal
        Button button1 = new Button("Tambah Buku");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());
        button1.setStyle("-fx-background-color : MediumSeaGreen");

        Button button2 = new Button("Hapus");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());
        
        button2.setStyle("-fx-background-color : RED");

        Button button3 = new Button("ubah");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());
        button3.setStyle("-fx-background-color : YELLOW");

        

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);
        vbox.setPadding(  new Insets(10 ,10,10,10));
      
        vbox.setBackground(new Background(
            new BackgroundFill(Color.MEDIUMSLATEBLUE,new CornerRadii(10),Insets.EMPTY)));
    
      
    //   scene.setStroke(Color.DARKRED);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from daftar ");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Buku(rs.getInt("Idbuku"), rs.getString("Judul"), rs.getString("Jenis")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");
        
        save.setStyle("-fx-background-color : MediumSeaGreen");

        addStage.setTitle("Tambah Data BUKU");

        TextField JudulField = new TextField("Masukan Judul Buku");
        TextField JenisField = new TextField("Masukan Jenis Buku");
        Label labelJudul = new Label("JUDUL BUKU");
        Label labelJenis = new Label("JENIS BUKU");

        VBox hbox1 = new VBox(5, labelJudul, JudulField);
        VBox hbox2 = new VBox(5, labelJenis, JenisField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);
        vbox.setPadding(  new Insets(10 ,10,10,10));
     
        vbox.setBackground(new Background(
            new BackgroundFill(Color.MEDIUMSLATEBLUE,new CornerRadii(10),Insets.EMPTY)));
        Scene scene = new Scene(vbox, 300, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into daftar SET Judul='%s', Jenis='%s'";
                sql = String.format(sql, JudulField.getText(), JenisField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("HAPUS");
           
        
           save.setStyle("-fx-background-color : MEDIUMVIOLETRED");

        addStage.setTitle("Hapus data buku");

        TextField noField = new TextField();
        Label labelNo = new Label("Masukan ID");

        VBox hbox1 = new VBox(5, labelNo, noField);
        VBox vbox = new VBox(20, hbox1, save);
        vbox.setPadding(  new Insets(10 ,10,10,10));
        vbox.setBackground(new Background(
            new BackgroundFill(Color.MEDIUMSLATEBLUE,new CornerRadii(10),Insets.EMPTY)));
        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from daftar WHERE Idbuku='%s'";
                sql = String.format(sql, noField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");
        //MEDIUMVIOLETRED
        
        save.setStyle("-fx-background-color : MediumSeaGreen");

        addStage.setTitle("Edit data Buku");

        TextField IdField = new TextField();
        TextField JudulField = new TextField();
        Label labelId = new Label("ID buku");
        Label labelJudul = new Label("Judul");
        

        VBox hbox1 = new VBox(5, labelId, IdField);
        VBox hbox2 = new VBox(5, labelJudul, JudulField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);
        vbox.setPadding(  new Insets(10 ,10,10,10));
        vbox.setBackground(new Background(
            new BackgroundFill(Color.MEDIUMSLATEBLUE,new CornerRadii(10),Insets.EMPTY)));
        

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE daftar SET Judul ='%s' WHERE Idbuku='%s'";
                sql = String.format(sql, JudulField.getText(), IdField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from daftar");
            while (rs.next()) {
                tableView.getItems().addAll(new Buku(rs.getInt("Idbuku"), rs.getString("Judul"), rs.getString("Jenis")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE daftar DROP Idbuku";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE buku ADD Idbuku INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}