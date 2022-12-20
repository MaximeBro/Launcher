package fr.universecorp.launcher.game;

import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.universecorp.launcher.Launcher;

public class MinecraftInfos {
    public static final String GAME_VERSION = "1.12.2";
    public static final ForgeVersionBuilder.ForgeVersionType VERSION_TYPE = ForgeVersionBuilder.ForgeVersionType.NEW;
    public static final String POKE_FORGE_VERSION = "1.12.2-14.23.5.2860";
    public static final String RAD_FORGE_VERSION = "1.12.2-14.23.5.2859";
    public static final GameType GAME_TYPE = GameType.V1_8_HIGHER;

    public static final String MODS_LIST_URL = Launcher.getInstance().getSelectedModpack().equals("pixelmon") ?
                                                "http://176.189.72.231/PokeFiles/mods/mods.json" :
                                                "http://176.189.72.231/RadFiles/mods/mods.json";
}
