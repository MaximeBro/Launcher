package fr.universecorp.launcher.ui.panels.pages.content;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.universecorp.launcher.Launcher;
import fr.universecorp.launcher.game.MinecraftInfos;
import fr.universecorp.launcher.ui.PanelManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Home extends ContentPanel {
    private final Saver saver = Launcher.getInstance().getSaver();
    GridPane boxPane = new GridPane();
    ProgressBar progressBar = new ProgressBar();
    Label stepLabel = new Label();
    Label fileLabel = new Label();
    boolean isDownloading = false;

    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getStylesheetPath() {
        return "css/content/home.css";
    }

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setMinHeight(115);
        rowConstraints.setMaxHeight(115);
        this.layout.getRowConstraints().addAll(rowConstraints, new RowConstraints());
        boxPane.getStyleClass().add("box-pane");
        setCanTakeAllSize(boxPane);
        boxPane.setPadding(new Insets(20));
        this.layout.add(boxPane, 0, 0);
        this.layout.getStyleClass().add("home-layout");

        progressBar.getStyleClass().add("download-progress");
        stepLabel.getStyleClass().add("download-status");
        fileLabel.getStyleClass().add("download-status");

        progressBar.setTranslateY(-15);
        setCenterH(progressBar);
        setCanTakeAllSize(progressBar);

        stepLabel.setTranslateY(5);
        setCenterH(stepLabel);
        setCanTakeAllSize(stepLabel);

        fileLabel.setTranslateY(20);
        setCenterH(fileLabel);
        setCanTakeAllSize(fileLabel);

        this.showPlayButton();
    }

    private void showPlayButton() {
        boxPane.getChildren().clear();
        Button playButton = new Button("  Jouer");
        playButton.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 24f));
        setCanTakeAllSize(playButton);
        playButton.setMinWidth(170);
        playButton.setMaxWidth(170);
        playButton.setMinHeight(55);
        playButton.setMaxHeight(55);
        setCenterV(playButton);
        setCenterH(playButton);
        playButton.getStyleClass().add("play-btn");
        playButton.setOnMouseClicked(e -> this.play());


        ImageView playIcon = new ImageView();
        if(Launcher.selectedModpack != null)
            if(Launcher.selectedModpack.equals("pixelmon"))
                playIcon = new ImageView(new Image("/images/launch2.png"));
            else
                playIcon = new ImageView(new Image("/images/shield.png"));

        setCenterH(playIcon);
        setCenterV(playIcon);
        playIcon.setTranslateX(-50d);
        playIcon.getStyleClass().add("play-icon");
        playIcon.setFitHeight(30);
        playIcon.setFitWidth(30);

        boxPane.getChildren().addAll(playButton, playIcon);
    }

    private void play() {
        isDownloading = true;
        boxPane.getChildren().clear();
        setProgress(0, 0);
        boxPane.getChildren().addAll(progressBar, stepLabel, fileLabel);

        Platform.runLater(() -> new Thread(this::update).start());
    }

    public void update() {
        IProgressCallback callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            private String stepTxt = "";
            private String percentTxt = "0.0%";

            @Override
            public void step(Step step) {
                Platform.runLater(() -> {
                    stepTxt = StepInfo.valueOf(step.name()).getDetails();
                    setStatus(String.format("%s (%s)", stepTxt, percentTxt));
                });
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {
                Platform.runLater(() -> {
                    percentTxt = decimalFormat.format(info.getDownloadedBytes() * 100.d / info.getTotalToDownloadBytes()) + "%";
                    setStatus(String.format("%s (%s)", stepTxt, percentTxt));
                    setProgress(info.getDownloadedBytes(), info.getTotalToDownloadBytes());
                });
            }

            @Override
            public void onFileDownloaded(Path path) {
                Platform.runLater(() -> {
                    String p = path.toString();
                    fileLabel.setText("..." + p.replace(Launcher.getInstance().getLauncherDir().toFile().getAbsolutePath(), ""));
                });
            }
        };

        try {
            final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                    .withName(MinecraftInfos.GAME_VERSION)
                    .build();

            List<Mod> mods = Mod.getModsFromJson(MinecraftInfos.MODS_LIST_URL);
            final AbstractForgeVersion forgeVersion = new ForgeVersionBuilder(MinecraftInfos.VERSION_TYPE)
                    .withForgeVersion((Launcher.selectedModpack.equals("pixelmon") ? MinecraftInfos.POKE_FORGE_VERSION : MinecraftInfos.RAD_FORGE_VERSION))
                    .withMods(mods)
                    .build();

            final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(vanillaVersion)
                    .withModLoaderVersion(forgeVersion)
                    .withLogger(Launcher.getInstance().getLogger())
                    .withProgressCallback(callback)
                    .build();

            updater.update(Launcher.getInstance().getLauncherDir());

            this.startGame(updater.getVanillaVersion().getName());
        } catch(Exception exception) {
            Launcher.getInstance().getLogger().err(exception.toString());
            exception.printStackTrace();
            Platform.runLater(() -> panelManager.getStage().show());
        }
    }


    public void startGame(String gameVersion) {
        GameInfos infos = new GameInfos(
                (Launcher.selectedModpack.equals("pixelmon") ? "PokeUniverse" : "RadUniverse"),
                Launcher.getInstance().getLauncherDir(),
                new GameVersion(gameVersion, MinecraftInfos.GAME_TYPE),
                new GameTweak[]{GameTweak.FORGE}
        );

        try {
            ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(infos, GameFolder.FLOW_UPDATER, Launcher.getInstance().getAuthInfos());
            profile.getVmArgs().add(this.getRamArgsFromSaver());

            if(saver.get("autoCB") != null && saver.get("autoCB").equals("true"))
                if(Launcher.selectedModpack != null)
                    if(Launcher.selectedModpack.equals("pixelmon")) {
                        profile.getArgs().addAll(Arrays.asList("--server=mc.universecorp.fr", "--port=25566"));
                    }
                    else {
                        profile.getArgs().addAll(Arrays.asList("--server=mc.universecorp.fr", "--port=25565"));
                    }

            ExternalLauncher launcher = new ExternalLauncher(profile);
            Process p = launcher.launch();

            Platform.runLater(() -> {
                    panelManager.getStage().hide();
                    Platform.exit();
            });
            } catch(Exception e) {
                e.printStackTrace();
                Launcher.getInstance().getLogger().err(e.toString());
            }
    }

    public String getRamArgsFromSaver() {
        int val = 1024;
        try {
            if (saver.get("maxRam") != null) {
                val = Integer.parseInt(saver.get("maxRam"));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException error) {
            saver.set("maxRam", String.valueOf(val));
            saver.save();
        }

        return "-Xmx" + (val + 1) + "G";
    }

    public void setStatus(String status) {
        this.stepLabel.setText(status);
    }

    public void setProgress(double current, double max) {
        this.progressBar.setProgress(current / max);
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public enum StepInfo {
        READ("Lecture du fichier json ..."),
        DL_LIBS("Téléchargement des libraries..."),
        DL_ASSETS("Téléchargement des ressources..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        INTEGRATION("Préparation du modpack..."),
        MOD_LOADER("Téléchargement des mods..."),
        MODS("Téléchargement des mods..."),
        EXTERNAL_FILES("Téléchargement des fichiers externes..."),
        POST_EXECUTIONS("Exécution post-installation..."),
        END("Terminé !");
        String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }
}
