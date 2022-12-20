package fr.universecorp.launcher.ui.panels.pages;

import com.sun.javafx.application.HostServicesDelegate;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panel.Panel;
import fr.universecorp.launcher.ui.panels.pages.content.ContentPanel;
import fr.universecorp.launcher.ui.panels.pages.content.Home;
import fr.universecorp.launcher.ui.panels.pages.content.RadSettings;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class RadApp extends Panel {
    GridPane sidemenu = new GridPane();
    GridPane navContent = new GridPane();

    Node activeLink = null;
    Button homeBtn, settingsBtn, discordBtn;

    Saver saver = Launcher.getInstance().getSaver();

    ContentPanel currentPage = null;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getStylesheetPath() {
        return "css/radapp.css";
    }


    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        // Background
        this.layout.getStyleClass().add("app-layout");
        setCanTakeAllSize(this.layout);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setMinWidth(350);
        columnConstraints.setMaxWidth(350);
        this.layout.getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());

        // Sidemenu
        this.layout.add(sidemenu, 0, 0);
        sidemenu.getStyleClass().add("sidemenu");
        setLeft(sidemenu);
        setCenterH(sidemenu);
        setCenterV(sidemenu);

        // Background image
        GridPane bImage = new GridPane();
        setCanTakeAllSize(bImage);
        bImage.getStyleClass().add("bg-image");
        this.layout.add(bImage, 1, 0);
        ImageView imageText = new ImageView(new Image("/images/radtext.png"));
        setCenterH(imageText);
        setBottom(imageText);
        imageText.setTranslateY(-15d);
        this.layout.add(imageText, 1, 0);

        // Nav content
        this.layout.add(navContent, 1, 0);
        navContent.getStyleClass().add("nav-content");
        setLeft(navContent);
        setCenterV(navContent);
        setCenterH(navContent);


        /*
         *  Side menu
         */
        // Title
        Label titleBg = new Label("");
        setCenterH(titleBg);
        setTop(titleBg);
        setCanTakeAllWidth(titleBg);
        setCanTakeAllSize(titleBg);
        titleBg.getStyleClass().add("app-title-bg");

        Label title = new Label("R.A.D");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 30f));
        title.getStyleClass().add("app-title");
        setCenterH(title);
        setCanTakeAllSize(title);
        setTop(title);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTranslateY(15d);
        sidemenu.getChildren().addAll(titleBg, title);

        // Navigation
        homeBtn = new Button(" Accueil");
        homeBtn.getStyleClass().add("sidemenu-nav-btn");
        homeBtn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.HOME));
        setCanTakeAllSize(homeBtn);
        setTop(homeBtn);
        homeBtn.setTranslateY(70d);
        homeBtn.setGraphic(new ImageView(new Image("/images/castle2.png")));
        homeBtn.setOnMouseClicked(e -> setPage(new Home(), homeBtn));

        // Settings
        settingsBtn = new Button(" Paramètres");
        settingsBtn.getStyleClass().add("sidemenu-nav-btn");
        settingsBtn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.GEARS));
        setCanTakeAllSize(settingsBtn);
        setTop(settingsBtn);
        settingsBtn.setTranslateY(130d);
        settingsBtn.setGraphic(new ImageView(new Image("/images/settings.png")));
        settingsBtn.setOnMouseClicked(e -> setPage(new RadSettings(), settingsBtn));

        sidemenu.getChildren().addAll(homeBtn, settingsBtn);


        // Discord button
        discordBtn = new Button(" Discord");
        setTop(discordBtn);
        setCanTakeAllSize(discordBtn);
        discordBtn.setTranslateY(190);
        discordBtn.getStyleClass().add("sidemenu-nav-btn");
        discordBtn.setGraphic(new ImageView(new Image("/images/discord.png")));
        discordBtn.setOnMouseClicked(e -> {
            HostServicesDelegate hostServices = HostServicesDelegate.getInstance(Launcher.getInstance());
            hostServices.showDocument("https://discord.gg/ewBc7KaEq5");
        });

        sidemenu.getChildren().add(discordBtn);


        // Avatar + Pseudo
        GridPane userPane = new GridPane();
        setCanTakeAllSize(userPane);
        userPane.setMaxHeight(80);
        userPane.setMinWidth(80);
        userPane.getStyleClass().add("user-pane");
        setBottom(userPane);

        String headUrl = "https://crafatar.com/avatars/" + Launcher.getInstance().getAuthInfos().getUuid() + ".png";
        String avatarUrl = "https://crafatar.com/renders/body/" + Launcher.getInstance().getAuthInfos().getUuid() + "?size=64&scale=6&";

        ImageView headView = new ImageView();
        Image headImg = new Image(headUrl);
        headView.setImage(headImg);
        headView.setPreserveRatio(true);
        headView.setFitHeight(50);
        setCenterV(headView);
        setLeft(headView);
        setCanTakeAllSize(headView);
        headView.setTranslateX(15d);
        userPane.getChildren().add(headView);

        Label avatarLabel = new Label("");
        setCenterH(avatarLabel);
        setCanTakeAllSize(avatarLabel);
        avatarLabel.setTranslateY(85d);
        avatarLabel.setTranslateX(-75d);
        avatarLabel.getStyleClass().add("avatar-profile-bg");
        sidemenu.getChildren().add(avatarLabel);

        ImageView avatarView = new ImageView();
        Image avatarImg = new Image(avatarUrl);
        avatarView.setImage(avatarImg);
        avatarView.setPreserveRatio(true);
        setCenterH(avatarView);
        setCanTakeAllSize(avatarView);
        avatarView.setTranslateY(85d);
        avatarView.setTranslateX(-75d);
        avatarView.getStyleClass().add("avatar-profile");
        sidemenu.getChildren().add(avatarView);


        Button mapBtn = new Button("");
        setCenterH(mapBtn);
        mapBtn.setTranslateY(85d);
        mapBtn.setTranslateX(95d);
        mapBtn.getStyleClass().add("map-btn");
        ImageView map = new ImageView(new Image("/images/rad-map.png"));
        map.setFitWidth(110);
        map.setFitHeight(110);
        mapBtn.setGraphic(map);
        mapBtn.setOnMouseClicked(e -> {
            HostServicesDelegate hostServices = HostServicesDelegate.getInstance(Launcher.getInstance());
            hostServices.showDocument("http://map.universecorp.fr/");
        });

        Label mapLabel = new Label("Dynmap");
        mapLabel.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 26f));
        setCenterH(mapLabel);
        setCanTakeAllSize(mapLabel);
        mapLabel.setTranslateY(161d);
        mapLabel.setTranslateX(95d);
        mapLabel.getStyleClass().add("map-label");
        mapLabel.setOnMouseClicked(e -> {
            HostServicesDelegate hostServices = HostServicesDelegate.getInstance(Launcher.getInstance());
            hostServices.showDocument("http://map.universecorp.fr/");
        });

        sidemenu.getChildren().addAll(mapBtn, mapLabel);



        Label usernameLabel = new Label(Launcher.getInstance().getAuthInfos().getUsername());
        usernameLabel.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 25f));
        setCanTakeAllSize(usernameLabel);
        setCenterV(usernameLabel);
        setLeft(usernameLabel);
        usernameLabel.getStyleClass().add("username-label");
        usernameLabel.setTranslateX(85d);
        setCanTakeAllWidth(usernameLabel);
        userPane.getChildren().add(usernameLabel);


        Button logoutBtn = new Button("");
        ImageView logoutIcon = new ImageView();
        Image logoutPng = new Image("images/logout.png");
        logoutIcon.setImage(logoutPng);
        logoutIcon.getStyleClass().add("logout-icon");
        setCanTakeAllSize(logoutBtn);
        setCenterV(logoutBtn);
        setRight(logoutBtn);
        logoutBtn.getStyleClass().add("logout-btn");
        logoutBtn.setGraphic(logoutIcon);
        logoutBtn.setTranslateX(-20d);
        logoutBtn.setOnMouseClicked(e -> {
            if(currentPage instanceof Home && ((Home) currentPage).isDownloading()) {
                return;
            }
            saver.remove("accessToken");
            saver.remove("accessClient");
            saver.remove("msAccessToken");
            saver.remove("msRefreshToken");
            saver.remove("offline-username");
            saver.save();
            Launcher.getInstance().setAuthInfos(null);

            this.panelManager.showPanel(new RadLogin());
        });

        userPane.getChildren().add(logoutBtn);

        sidemenu.getChildren().add(userPane);
    }


    @Override
    public void onShow() {
        super.onShow();
        setPage(new Home(), homeBtn);
    }

    public void setPage(ContentPanel panel, Node navButton) {
        if(currentPage instanceof Home && ((Home) currentPage).isDownloading()) {
            return;
        }
        if(activeLink != null)
            activeLink.getStyleClass().remove("active");
        activeLink = navButton;
        activeLink.getStyleClass().add("active");

        this.navContent.getChildren().clear();
        if(panel != null) {
            this.navContent.getChildren().add(panel.getLayout());
            if(panel.getStylesheetPath() != null) {
                this.panelManager.getStage().getScene().getStylesheets().clear();
                this.panelManager.getStage().getScene().getStylesheets().addAll(
                        this.getStylesheetPath(),
                        panel.getStylesheetPath()
                );
            }
            panel.init(this.panelManager);
            panel.onShow();
        }
    }
}
