package net.darkhax.jmapstages.crt;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import net.darkhax.jmapstages.JMapStages;

public class ActionToggleStage implements IRuntimeAction {

    private final Type type;
    private final String stage;

    public ActionToggleStage(Type type, String stage) {
        this.type = type;
        this.stage = stage;
    }

    @Override
    public void apply () {

        switch (this.type) {
            case DEATHPOINT:
                JMapStages.stageDeathoint = this.stage;
                break;
            case FULLSCREEN:
                JMapStages.stageFullscreen = this.stage;
                break;
            case MINIMAP:
                JMapStages.stageMinimap = this.stage;
                break;
            case WAYPOINT:
                JMapStages.stageWaypoint = this.stage;
                break;
            default:
                break;
        }
    }

    @Override
    public String describe () {
        return String.format("Restricting Journey Map %s stage to %s.", this.type.name().toLowerCase(), this.stage);
    }

    enum Type {

        FULLSCREEN,
        MINIMAP,
        WAYPOINT,
        DEATHPOINT;
    }
}