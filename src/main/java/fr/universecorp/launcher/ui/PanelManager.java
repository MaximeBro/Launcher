package fr.universecorp.launcher.ui;

import fr.flowarg.flowcompat.Platform;
import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.ui.panel.IPanel;
import fr.universecorp.launcher.ui.panel.partials.TopBar;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PanelManager {
    private final Launcher launcher;
    private final Stage stage;
    private GridPane layout;
    private final GridPane contentPane = new GridPane();



    public PanelManager(Launcher launcher, Stage stage) {
        this.launcher = launcher;
        this.stage = stage;
    }

    public void init() {
        this.stage.setTitle("UniverseCorp");
        this.stage.setMinWidth(854);
        this.stage.setMinWidth(480);
        this.stage.setWidth(1280);
        this.stage.setHeight(720);

        this.stage.centerOnScreen();
        this.stage.getIcons().add(new Image("images/icon.png"));

        this.layout = new GridPane();
        Scene scene = new Scene(this.layout);
        if(Platform.isOnLinux()) {
            this.stage.setScene(scene);
        } else {
            this.stage.initStyle(StageStyle.UNDECORATED);
            TopBar topBar = new TopBar();
            this.stage.setScene(scene);
            this.stage.show();

            RowConstraints topPanelConstraints = new RowConstraints();
            topPanelConstraints.setValignment(VPos.TOP);
            topPanelConstraints.setMinHeight(30);
            topPanelConstraints.setMaxHeight(30);
            this.layout.getRowConstraints().addAll(topPanelConstraints, new RowConstraints());
            this.layout.add(topBar.getLayout(), 0, 0);
            topBar.init(this);
        }

        this.layout.add(this.contentPane, 0, 1);
        GridPane.setVgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setHgrow(this.contentPane, Priority.ALWAYS);

        this.layout.setOnMousePressed(pressEvent -> {
            this.layout.setOnMouseDragged(dragEvent -> {
                this.stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                this.stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }


    public void showPanel(IPanel panel) {
        this.contentPane.getChildren().clear();
        this.contentPane.getChildren().add(panel.getLayout());

        if(panel.getStylesheetPath() != null) {
            this.stage.getScene().getStylesheets().clear();
            this.stage.getScene().getStylesheets().add(panel.getStylesheetPath());
        }

        panel.init(this);
        panel.onShow();
    }

    public Stage getStage() {
        return stage;
    }

    public Launcher getLauncher() {
        return launcher;
    }
}
