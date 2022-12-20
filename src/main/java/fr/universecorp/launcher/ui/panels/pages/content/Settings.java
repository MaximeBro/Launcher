package fr.universecorp.launcher.ui.panels.pages.content;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panels.pages.PokeApp;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class Settings extends ContentPanel {
    private final Saver saver = Launcher.getInstance().getSaver();
    GridPane contentPane = new GridPane();

    @Override
    public String getName() { return "settings"; }


    @Override
    public String getStylesheetPath() {
        return "css/content/settings.css";
    }


    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        // Background
        this.layout.getStyleClass().add("settings-layout");
        this.layout.setPadding(new Insets(40));
        setCanTakeAllSize(this.layout);


        // Content
        this.contentPane.getStyleClass().add("content-pane");
        setCanTakeAllSize(this.contentPane);
        this.layout.getChildren().add(this.contentPane);


        // Title
        Label title = new Label("Paramètres");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 25f));
        title.getStyleClass().add("settings-title");
        setLeft(title);
        setCanTakeAllSize(title);
        setTop(title);
        title.setTextAlignment(TextAlignment.LEFT);
        title.setTranslateX(25d);
        title.setTranslateY(40d);
        this.contentPane.getChildren().add(title);


        // RAM
        Label ramLabel = new Label("Mémoire max");
        ramLabel.getStyleClass().add("setting-label");
        setLeft(ramLabel);
        setCanTakeAllSize(ramLabel);
        setTop(ramLabel);
        ramLabel.setTextAlignment(TextAlignment.LEFT);
        ramLabel.setTranslateX(25d);
        ramLabel.setTranslateY(100d);
        this.contentPane.getChildren().add(ramLabel);


        // RAM Selector
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getStyleClass().add("ram-selector");

        for(int i = 1024; i <= Math.ceil(memory.getTotal() /  Math.pow(1024, 2)) + 1024; i += 1024) {
            comboBox.getItems().add(i / 1024 + " Go");
        }

        int val = 1024;
        try {
            if(saver.get("maxRam") != null) {
                val = Integer.parseInt(saver.get("maxRam")) * 1024;
            } else {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException error) {
            saver.set("maxRam", String.valueOf(val));
            saver.save();
        }

        if(comboBox.getItems().contains(val / 1024 + " Go")) {
            comboBox.setValue(val / 1024 + " Go");
        } else {
            comboBox.setValue("1 Go");
        }

        setLeft(comboBox);
        setCanTakeAllSize(comboBox);
        setTop(comboBox);
        comboBox.setTranslateX(35d);
        comboBox.setTranslateY(130d);
        this.contentPane.getChildren().add(comboBox);


        /*
         * Server autoconnection
         */
        Label autoLabel = new Label("Connexion automatique au serveur");
        autoLabel.getStyleClass().add("auto-label");
        setLeft(autoLabel);
        setCanTakeAllSize(autoLabel);
        setTop(autoLabel);
        autoLabel.setTextAlignment(TextAlignment.LEFT);
        autoLabel.setTranslateX(250d);
        autoLabel.setTranslateY(100d);
        this.contentPane.getChildren().add(autoLabel);

        // Checkbox
        CheckBox autoCB = new CheckBox();

        if(saver.get("autoCB") == null || !saver.get("autoCB").equals("true") && !saver.get("autoCB").equals("false")) {
            autoCB.setSelected(true); // Set to true by default ... (so as the label)
            saver.set("autoCB", "true");
            saver.save();
        } else {
            if(saver.get("autoCB").equals("true"))
                autoCB.setSelected(true);
            else if(saver.get("autoCB").equals("false"))
                autoCB.setSelected(false);
        }


        setLeft(autoCB);
        setCanTakeAllSize(autoCB);
        setTop(autoCB);
        autoCB.setTranslateX(275d);
        autoCB.setTranslateY(120d);
        autoCB.getStyleClass().add("check-box");
        this.contentPane.getChildren().add(autoCB);


        Label stateLbl = new Label();

        if(autoCB.isSelected())
            stateLbl.setText("(Activée)");
        else
            stateLbl.setText("(Désactivée)");

        stateLbl.getStyleClass().add("check-label");
        setLeft(stateLbl);
        setCanTakeAllSize(stateLbl);
        setTop(stateLbl);
        stateLbl.setTextAlignment(TextAlignment.LEFT);
        stateLbl.setTranslateX(370d);
        stateLbl.setTranslateY(135d);
        this.contentPane.getChildren().add(stateLbl);


        // When CB is fired (here only the label is modified, the cb's graphic is all css made)
        autoCB.setOnAction(event -> {
            if(event.getSource() == autoCB) {
                if(autoCB.isSelected()) {
                    stateLbl.setText("(Activée)");
                }
                else {
                    stateLbl.setText("(Désactivée)");
                }
            }
        });


        /*
         * Save Button
         */
        Button saveBtn = new Button(" Enregistrer".toUpperCase());
        saveBtn.setFont(Font.font("Consolas", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20f));
        saveBtn.getStyleClass().add("save-btn");
        saveBtn.setMinWidth(220);
        saveBtn.setMaxWidth(220);
        saveBtn.setMinHeight(65);
        saveBtn.setMaxHeight(65);

        ImageView iconView = new ImageView(new Image("/images/save.png"));
        iconView.setFitHeight(35);
        iconView.setFitWidth(35);
        iconView.getStyleClass().add("save-icon");
        saveBtn.setGraphic(iconView);
        setCanTakeAllSize(saveBtn);
        setBottom(saveBtn);
        setCenterH(saveBtn);
        saveBtn.setTranslateY(15d);
        saveBtn.setOnMouseClicked(e -> {
            double _val = Double.parseDouble(comboBox.getValue().replace(" Go", ""));
            saver.set("maxRam", String.valueOf((int) _val));

            if(autoCB.isSelected()) {
                saver.set("autoCB", "true");
                saver.save();
            } else {
                saver.set("autoCB", "false");
                saver.save();
            }

            this.panelManager.showPanel(new PokeApp());
        });
        contentPane.getChildren().add(saveBtn);


        ImageView cancelIcon = new ImageView(new Image("/images/back.png"));
        cancelIcon.setFitWidth(32);
        cancelIcon.setFitHeight(32);
        Button backBtn = new Button();
        backBtn.setGraphic(cancelIcon);
        setTop(backBtn);
        setLeft(backBtn);
        setCanTakeAllSize(backBtn);
        backBtn.setTranslateY(-10d);
        backBtn.getStyleClass().add("back-btn");

        backBtn.setOnMouseClicked(e -> this.panelManager.showPanel(new PokeApp()));

        contentPane.getChildren().add(backBtn);
    }
}
