package fr.universecorp.launcher.ui.panel.partials;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panel.Panel;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class TopBar extends Panel {
    private GridPane topBar;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getStylesheetPath() {
        return "css/top.css";
    }


    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);
        this.topBar = this.layout;
        this.layout.setStyle("-fx-background-color: rgb(35, 40, 40);");
        setCanTakeAllWidth(this.topBar);

        /*
         * TopBar separation
         */
        // TopBar: left side
        Label title = new Label("UniverseCorp Launcher (1.12.2)");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 18f));
        title.setStyle("-fx-text-fill: white;");
        setLeft(title);
        title.setTranslateX(15d);
        this.layout.getChildren().add(title);

        // TopBar: right side
        GridPane topBarButton = new GridPane();
        topBarButton.setMinWidth(100d);
        topBarButton.setMaxWidth(100d);
        setCanTakeAllSize(topBarButton);
        setRight(topBarButton);
        this.layout.getChildren().add(topBarButton);

        /*
         * TopBar buttons configuration
         */
        MaterialDesignIconView closeBtn = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
        MaterialDesignIconView fullscreenBtn = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_RESTORE);
        MaterialDesignIconView minimizeBtn = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MINIMIZE);
        setCanTakeAllWidth(closeBtn, fullscreenBtn, minimizeBtn);

        closeBtn.setFill(Color.WHITE);
        closeBtn.setOpacity(1.f);
        closeBtn.setSize("22px");
        closeBtn.setOnMouseEntered(e -> closeBtn.setOpacity(1.f));
        closeBtn.setOnMouseExited(e -> closeBtn.setOpacity(.7f));
        closeBtn.setOnMouseClicked(e -> System.exit(0));
        closeBtn.setTranslateX(65d);
        closeBtn.setTranslateY(3d);

        fullscreenBtn.setFill(Color.WHITE);
        fullscreenBtn.setOpacity(1.0f);
        fullscreenBtn.setSize("20px");
        fullscreenBtn.setOnMouseEntered(e -> fullscreenBtn.setOpacity(1.0f));
        fullscreenBtn.setOnMouseExited(e -> fullscreenBtn.setOpacity(0.7f));
        fullscreenBtn.setTranslateX(20d);
        fullscreenBtn.setTranslateY(2d);


        minimizeBtn.setFill(Color.WHITE);
        minimizeBtn.setOpacity(1.0f);
        minimizeBtn.setSize("22px");
        minimizeBtn.setOnMouseEntered(e -> minimizeBtn.setOpacity(1.0f));
        minimizeBtn.setOnMouseExited(e -> minimizeBtn.setOpacity(0.7f));
        minimizeBtn.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));
        minimizeBtn.setTranslateX(-30d);
        minimizeBtn.setTranslateY(4d);
        minimizeBtn.getStyleClass().add("mini-btn");

        topBarButton.getChildren().addAll(closeBtn, fullscreenBtn, minimizeBtn);
    }
}
