package net.darkhax.jmapstages.crt;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import net.darkhax.jmapstages.JMapEventListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ActionToggleStage implements IRuntimeAction {

    private final Type type;
    private final String stage;

    public ActionToggleStage(Type type, String stage) {
        this.type = type;
        this.stage = stage;
    }

    @Override
    public void apply () {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> applyToJMapEvent());
    }

    @Override
    public String describe () {
        return String.format("Restricting Journey Map %s stage to %s.", this.type.name().toLowerCase(), this.stage);
    }

    public void applyToJMapEvent() {
        switch (this.type) {
            case DEATHPOINT:
                JMapEventListener.stageDeathpoint = this.stage;
                break;
            case FULLSCREEN:
                JMapEventListener.stageFullscreen = this.stage;
                break;
            case MINIMAP:
                JMapEventListener.stageMinimap = this.stage;
                break;
            case WAYPOINT:
                JMapEventListener.stageWaypoint = this.stage;
                break;
            default:
                break;
        }
    }

    enum Type {

        FULLSCREEN,
        MINIMAP,
        WAYPOINT,
        DEATHPOINT;
    }
}