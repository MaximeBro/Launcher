package fr.universecorp.launcher.ui.panels.pages;

import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panel.Panel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ServerPanel extends Panel {

    private GridPane page;
    private Button radBtn, radPlayBtn, pokeBtn, pokePlayBtn;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getStylesheetPath() {
        return "css/serverpanel.css";
    }

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        this.page = this.layout;
        this.layout.getStyleClass().add("background");


        // Title -> choosing a modpack
        Label titleLabel = new Label("Choisissez votre modpack");
        titleLabel.setFont(Font.loadFont("file:C:\\Users\\Maxime\\Desktop\\launcher\\Launcher\\src\\main\\resources\\fonts\\Qmodular.ttf", 40f));
        titleLabel.getStyleClass().add("title-label");
        setCenterH(titleLabel);
        setTop(titleLabel);
        setCanTakeAllSize(titleLabel);
        titleLabel.setTranslateY(50d);

        this.layout.getChildren().add(titleLabel);


        /*
         * Buttons, for every modpack
         */

        // RAD Button
        this.radBtn = new Button();
        this.radBtn.getStyleClass().add("rad-btn");
        setCenterH(this.radBtn);
        setCenterV(this.radBtn);
        setCanTakeAllSize(this.radBtn);
        this.radBtn.setTranslateX(-250d);

        // Button ImageView
        Label radView = new Label();
        radView.getStyleClass().add("rad-icon");
        setCenterH(radView);
        setCenterV(radView);
        setCanTakeAllSize(radView);
        radView.setTranslateX(-250d);
        radView.setTranslateY(-25d);

        // Button label
        Label radLabel = new Label("RAD - 1.49 Revamp");
        setCenterV(radLabel);
        setCenterH(radLabel);
        setCanTakeAllSize(radLabel);
        radLabel.setTranslateX(-250d);
        radLabel.setTranslateY(135d);
        radLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 20f));
        radLabel.setStyle("-fx-text-fill: white;");

        // Button Play
        this.radPlayBtn = new Button("R.A.D");
        this.radPlayBtn.setFont(Font.loadFont("file:C:\\Users\\Maxime\\Desktop\\launcher\\Launcher\\src\\main\\resources\\fonts\\Qmodular.ttf", 32f));
        setCenterH(this.radPlayBtn);
        setBottom(this.radPlayBtn);
        setCanTakeAllSize(this.radPlayBtn);
        this.radPlayBtn.setTranslateY(-70d);
        this.radPlayBtn.setTranslateX(-250d);
        this.radPlayBtn.getStyleClass().add("rad-play-btn");
        this.radPlayBtn.setOnMouseClicked(e -> {
            Launcher.getInstance().setModpackPath("\\.RadUniverse\\", "rad");
            if(Launcher.getInstance().isUserAlreadyLoggedIn()) {
                logger.info("Bonjour " + Launcher.getInstance().getAuthInfos().getUsername());
                this.panelManager.showPanel(new RadApp());
            } else {
                this.panelManager.showPanel(new RadLogin());
            }
        });

        this.layout.getChildren().addAll(this.radBtn, this.radPlayBtn, radView, radLabel);



        // Poke Button
        this.pokeBtn = new Button();
        this.pokeBtn.getStyleClass().add("poke-btn");
        setCenterH(this.pokeBtn);
        setCenterV(this.pokeBtn);
        setCanTakeAllSize(this.pokeBtn);
        this.pokeBtn.setTranslateX(250d);

        // Button ImageView
        Label pokeView = new Label();
        pokeView.getStyleClass().add("poke-icon");
        setCenterH(pokeView);
        setCenterV(pokeView);
        setCanTakeAllSize(pokeView);
        pokeView.setTranslateX(250d);
        pokeView.setTranslateY(-25d);

        // Button label
        Label pokeLabel = new Label("Pixelmon Reforged\n      8.4.0");
        setCenterV(pokeLabel);
        setCenterH(pokeLabel);
        setCanTakeAllSize(pokeLabel);
        pokeLabel.setTranslateX(250d);
        pokeLabel.setTranslateY(135d);
        pokeLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 20f));
        pokeLabel.setStyle("-fx-text-fill: white;");

        // Button Play
        this.pokePlayBtn = new Button("PIXELMON");
        this.pokePlayBtn.setFont(Font.loadFont("file:C:\\Users\\Maxime\\Desktop\\launcher\\Launcher\\src\\main\\resources\\fonts\\Qmodular.ttf", 32f));
        setCenterH(this.pokePlayBtn);
        setBottom(this.pokePlayBtn);
        setCanTakeAllSize(this.pokePlayBtn);
        this.pokePlayBtn.setTranslateY(-70d);
        this.pokePlayBtn.setTranslateX(250d);
        this.pokePlayBtn.getStyleClass().add("poke-play-btn");
        this.pokePlayBtn.setOnMouseClicked(e -> {
            Launcher.getInstance().setModpackPath("\\.PokeUniverse\\", "pixelmon");
            if(Launcher.getInstance().isUserAlreadyLoggedIn()) {
                logger.info("Bonjour " + Launcher.getInstance().getAuthInfos().getUsername());
                this.panelManager.showPanel(new PokeApp());
            } else {
                this.panelManager.showPanel(new PokeLogin());
            }
        });

        this.layout.getChildren().addAll(this.pokeBtn, this.pokePlayBtn, pokeView, pokeLabel);
    }
}
