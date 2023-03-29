package net.darkhax.jmapstages;

import journeymap.client.ui.fullscreen.Fullscreen;
import journeymap.client.ui.waypoint.WaypointEditor;
import journeymap.client.ui.waypoint.WaypointManager;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.event.StagesSyncedEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(JMapStages.MOD_ID)
public class JMapStages {
    public static final String MOD_ID = "jmapstages";
    public static final Logger LOGGER = LoggerFactory.getLogger("JMapStages");

    public static String stageFullscreen = "";
    public static String stageMinimap = "";
    public static String stageWaypoint = "";
    public static String stageDeathoint = "";

    private JMapPermissionHandler perms;

    public JMapStages () {
        MinecraftForge.EVENT_BUS.addListener(this::onScreenOpen);
        MinecraftForge.EVENT_BUS.addListener(this::onStageSynced);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::post);
    }

    public void post (FMLLoadCompleteEvent event) {
        this.perms = new JMapPermissionHandler();
    }

    private void onScreenOpen (ScreenEvent.Opening event) {
        final Player player = Minecraft.getInstance().player;
        if (!stageFullscreen.isEmpty() && event.getNewScreen() instanceof Fullscreen &&
                !GameStageHelper.hasStage(player, stageFullscreen)) {
            player.sendSystemMessage(
                    Component.translatable("message.jmapstages.restrict.fullscreen", stageFullscreen).withStyle(ChatFormatting.RED)
            );
            event.setCanceled(true);
        }
        else if (!stageWaypoint.isEmpty() &&
                (event.getNewScreen() instanceof WaypointManager || event.getNewScreen() instanceof WaypointEditor) &&
                !GameStageHelper.hasStage(player, stageWaypoint)) {
            player.sendSystemMessage(
                    Component.translatable("message.jmapstages.restrict.waypoint", stageWaypoint).withStyle(ChatFormatting.RED)
            );
            event.setCanceled(true);
        }
    }

    private void onStageSynced (StagesSyncedEvent event) {
        if (event.getData().hasStage(stageMinimap)) {
            perms.toggleMinimap(true);
        }
    }

    private void onPlayerTick (TickEvent.PlayerTickEvent event) {
        if (event.player.level.isClientSide && event.player.level.getGameTime() % 5 == 0) {
            if (!stageMinimap.isEmpty() && !GameStageHelper.hasStage(event.player, stageMinimap)) {
                this.perms.toggleMinimap(false);
            }

            if (!stageWaypoint.isEmpty() && !GameStageHelper.hasStage(event.player, stageWaypoint)) {
                this.perms.clearWaypoints();
            }

            if (!stageDeathoint.isEmpty() && !GameStageHelper.hasStage(event.player, stageDeathoint)) {
                this.perms.clearDeathPoints();
            }
        }
    }
}