package fr.universecorp.launcher;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowlogger.Logger;
import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.model.response.RefreshResponse;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.universecorp.launcher.ui.PanelManager;
import fr.universecorp.launcher.ui.panels.pages.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Launcher extends Application {
    private PanelManager panelManager;
    private static Launcher instance;

    public static String selectedModpack;
    private ILogger logger;
    private Path launcherDir = GameDirGenerator.createGameDir("UniverseCorp", true);
    private Saver saver;

    private AuthInfos authInfos = null;

    public Launcher() {
        instance = this;
        this.logger = new Logger("[Log]", this.launcherDir.resolve("launcher.log"));
        if(Files.notExists(this.launcherDir)) {
            try {
                Files.createDirectories(this.launcherDir);
            } catch (IOException e) {
                throw new RuntimeException("Unable to create launcher folder", e);
            }
        }

        saver = new Saver(this.launcherDir.resolve("config.properties"));
        saver.load();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.logger.info("Starting launcher");
        this.panelManager = new PanelManager(this, stage);
        this.panelManager.init();

        this.panelManager.showPanel(new ServerPanel());
    }


    public boolean isUserAlreadyLoggedIn() {
        if(this.saver.get("accessToken") != null && this.saver.get("clientToken") != null) {
            Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);

            try {
                RefreshResponse response = authenticator.refresh(saver.get("accessToken"), saver.get("clientToken"));
                this.saver.set("accessToken", response.getAccessToken());
                this.saver.set("clientToken", response.getClientToken());
                this.saver.save();

                this.setAuthInfos(new AuthInfos(
                    response.getSelectedProfile().getName(),
                    response.getAccessToken(),
                    response.getClientToken(),
                    response.getSelectedProfile().getId()
                ));

                return true;
            } catch (AuthenticationException ignored) {
                this.saver.remove("accessToken");
                this.saver.remove("clientToken");
                this.saver.save();
            }
        } else if(this.saver.get("msAccessToken") != null && this.saver.get("msRefreshToken") != null) {
            try {
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                MicrosoftAuthResult response = authenticator.loginWithRefreshToken(this.saver.get("msRefreshToken"));

                this.saver.set("msAccessToken", response.getAccessToken());
                this.saver.set("msRefreshToken", response.getRefreshToken());
                this.saver.save();

                this.setAuthInfos(new AuthInfos(
                        response.getProfile().getName(),
                        response.getAccessToken(),
                        response.getProfile().getId()
                ));
                return true;
            } catch(MicrosoftAuthenticationException e) {
                this.saver.remove("msAccessToken");
                this.saver.remove("msRefreshToken");
                this.saver.save();
            }
        }

        return false;
    }

    public ILogger getLogger() {
        return logger;
    }

    public static Launcher getInstance() {
        return instance;
    }

    public Saver getSaver() {
        return this.saver;
    }

    public void setAuthInfos(AuthInfos infos) {
        this.authInfos = infos;
    }

    public AuthInfos getAuthInfos() {
        return this.authInfos;
    }

    public Path getLauncherDir() {
        return launcherDir;
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public void setModpackPath(String path, String modpack) {
        Launcher.selectedModpack = modpack;

        Path modpackPath = GameDirGenerator.createGameDir("UniverseCorp" + path, true);
        this.launcherDir = modpackPath;

        if(Files.notExists(modpackPath)) {
            try {
                Files.createDirectories(modpackPath);
            } catch (IOException e) {
                throw new RuntimeException("Unable to create launcher folder", e);
            }
        }
    }

    public static String getSelectedModpack() {
        return Launcher.selectedModpack;
    }

    public void HideWindow() {
        this.panelManager.getStage().hide();
    }
}
