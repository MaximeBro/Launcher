package fr.universecorp.launcher.ui.panels.pages;

import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panel.Panel;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class RadLogin extends Panel {
    GridPane loginCard = new GridPane();

    Saver saver = Launcher.getInstance().getSaver();

    TextField userField = new TextField();
    PasswordField passwordField = new PasswordField();
    Label userErrorLabel = new Label();
    Label passwordErrorLabel = new Label();
    Button btnLogin = new Button("Connexion");
    Button msLoginBtn = new Button();

    Button selecBtn = new Button();


    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getStylesheetPath() {
        return "css/radlogin.css";
    }

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        // Background
        this.layout.getStyleClass().add("login-layout");

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setMinWidth(350);
        columnConstraints.setMaxWidth(350);
        this.layout.getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.layout.add(loginCard, 0, 0);

        // Background image
        GridPane bImage = new GridPane();
        setCanTakeAllSize(bImage);
        bImage.getStyleClass().add("bg-image");
        this.layout.add(bImage, 1, 0);

        ImageView imageText = new ImageView(new Image("/images/radtext.png"));
        setCenterH(imageText);
        setTop(imageText);
        imageText.setTranslateY(15d);
        this.layout.add(imageText, 1, 0);


        Label selecLabel = new Label(">> Modpacks");
        setBottom(selecLabel);
        setRight(selecLabel);
        selecLabel.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 25f));
        selecLabel.setTranslateX(-20d);
        selecLabel.setTranslateY(-20d);
        selecLabel.getStyleClass().add("modpack-label");
        selecLabel.setOnMouseClicked(e -> this.panelManager.showPanel(new ServerPanel()));
        this.layout.add(selecLabel, 1, 0);

        ImageView helmet = new ImageView(new Image("/images/helmet.png"));
        setTop(this.selecBtn);
        setRight(this.selecBtn);
        this.selecBtn.setTranslateX(-35d);
        this.selecBtn.setTranslateY(35d);
        this.selecBtn.setGraphic(helmet);
        this.selecBtn.setStyle("-fx-background-color: transparent;");
        this.selecBtn.setOnMouseClicked(e -> this.panelManager.showPanel(new ServerPanel()));
        this.layout.add(this.selecBtn, 1, 0);


        // Login card
        setCanTakeAllSize(this.layout);
        loginCard.getStyleClass().add("login-card");
        setLeft(loginCard);
        setCenterH(loginCard);
        setCenterV(loginCard);


        /*
         * Login sidebar
         */
        Label title = new Label("Connexion".toUpperCase());
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 30f));
        title.getStyleClass().add("login-title");
        setCenterH(title);
        setCanTakeAllSize(title);
        setTop(title);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTranslateX(-10d);
        title.setTranslateY(30d);
        loginCard.getChildren().add(title);

        // Login with label
        Label mojangLabel = new Label("Compte Mojang".toUpperCase());
        setCanTakeAllSize(mojangLabel);
        setCenterV(mojangLabel);
        setCenterH(mojangLabel);
        mojangLabel.setFont(Font.font(mojangLabel.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 16f));
        mojangLabel.getStyleClass().add("login-with-label");
        mojangLabel.setTranslateY(-105d);
        mojangLabel.setTranslateX(-15d);
        mojangLabel.setMaxWidth(280);
        loginCard.getChildren().add(mojangLabel);

        // Username/E-Mail
        setCanTakeAllSize(userField);
        setCenterH(userField);
        setCenterV(userField);
        userField.setPromptText("Adresse E-mail");
        userField.setMaxWidth(300);
        userField.setTranslateY(-70d);
        userField.getStyleClass().add("login-input");
        userField.textProperty().addListener((_a, oldValue, newValue) -> {
            this.updateLoginBtnState(userField, userErrorLabel);
        });

        // User error
        setCanTakeAllSize(userErrorLabel);
        setCenterV(userErrorLabel);
        setCenterH(userErrorLabel);
        userErrorLabel.getStyleClass().add("login-error");
        userErrorLabel.setTranslateY(-45d);
        userErrorLabel.setMaxWidth(280);
        userErrorLabel.setTextAlignment(TextAlignment.LEFT);



        // Password
        setCanTakeAllSize(passwordField);
        setCenterH(passwordField);
        setCenterV(passwordField);
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setTranslateY(-15d);
        passwordField.getStyleClass().add("login-input");
        passwordField.textProperty().addListener((_a, oldValue, newValue) -> {
            this.updateLoginBtnState(passwordField, passwordErrorLabel);
        });

        // User error
        setCanTakeAllSize(passwordErrorLabel);
        setCenterV(passwordErrorLabel);
        setCenterH(passwordErrorLabel);
        passwordErrorLabel.getStyleClass().add("login-error");
        passwordErrorLabel.setTranslateY(10d);
        passwordErrorLabel.setMaxWidth(280);
        passwordErrorLabel.setTextAlignment(TextAlignment.LEFT);



        // Login button
        setCanTakeAllSize(btnLogin);
        setCenterV(btnLogin);
        setCenterH(btnLogin);
        btnLogin.setDisable(true);
        btnLogin.setMaxWidth(300);
        btnLogin.setTranslateY(40d);
        btnLogin.getStyleClass().add("login-log-btn");
        btnLogin.setOnMouseClicked(e -> {
            this.authenticate(userField.getText(), passwordField.getText());
        });

        Separator separator = new Separator();
        setCanTakeAllSize(separator);
        setCenterV(separator);
        setCenterH(separator);
        separator.getStyleClass().add("login-separator");
        separator.setMaxWidth(300);
        separator.setTranslateY(110d);

        // Login with label
        Label loginWithLabel = new Label("Ou se connecter avec".toUpperCase());
        setCanTakeAllSize(loginWithLabel);
        setCenterV(loginWithLabel);
        setCenterH(loginWithLabel);
        loginWithLabel.setFont(Font.font(loginWithLabel.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 14f));
        loginWithLabel.getStyleClass().add("login-with-label");
        loginWithLabel.setTranslateY(130d);
        loginWithLabel.setMaxWidth(280);



        // Microsoft login button
        ImageView view = new ImageView(new Image("images/microsoft.png"));
        view.setPreserveRatio(true);
        view.setFitHeight(30d);
        setCanTakeAllSize(msLoginBtn);
        setCenterH(msLoginBtn);
        setCenterV(msLoginBtn);
        msLoginBtn.getStyleClass().add("ms-login-btn");
        msLoginBtn.setMaxWidth(300);
        msLoginBtn.setTranslateY(165d);
        msLoginBtn.setGraphic(view);
        msLoginBtn.setOnMouseClicked(e -> {
            try {
                this.authenticateMS();
            } catch (MicrosoftAuthenticationException ex) {
                ex.printStackTrace();
            }
        });



        loginCard.getChildren().addAll(userField, userErrorLabel, passwordField, passwordErrorLabel, btnLogin, separator, loginWithLabel, msLoginBtn);
    }


    public void updateLoginBtnState(TextField textField, Label errorLabel) {
        if(textField.getText().length() == 0) {
            errorLabel.setText("Veuillez renseigner vos identifiants !");
        } else {
            errorLabel.setText("");
        }

        btnLogin.setDisable(!(userField.getText().length() > 0 && passwordField.getText().length() > 0));
    }


    public void authenticate(String user, String password) {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);

        try {
            AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, user, password, null);

            saver.set("accessToken", response.getAccessToken());
            saver.set("clientToken", response.getClientToken());
            saver.save();

            AuthInfos infos = new AuthInfos(
                    response.getSelectedProfile().getName(),
                    response.getAccessToken(),
                    response.getClientToken(),
                    response.getSelectedProfile().getId()
            );

            Launcher.getInstance().setAuthInfos(infos);
            this.logger.info("Bonjour " + infos.getUsername());

            panelManager.showPanel(new PokeApp());

        } catch (AuthenticationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur est survenue lors de la connexion !");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void authenticateMS() throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        authenticator.loginWithAsyncWebview().whenComplete((response, error) -> {
            if (error != null) {
                Launcher.getInstance().getLogger().err(error.toString());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(error.getMessage());
                alert.show();
                return;
            } else {
                saver.set("msAccessToken", response.getAccessToken());
                saver.set("msRefreshToken", response.getRefreshToken());
                saver.save();

                Launcher.getInstance().setAuthInfos(new AuthInfos(
                        response.getProfile().getName(),
                        response.getAccessToken(),
                        response.getRefreshToken(),
                        response.getProfile().getId()
                ));

                Launcher.getInstance().getLogger().info("Bonjour " + response.getProfile().getName());

                Platform.runLater(() -> {
                    panelManager.showPanel(new RadApp());
                });
            }
        });
    }
}
