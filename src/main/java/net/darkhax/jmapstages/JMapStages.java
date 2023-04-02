package net.darkhax.jmapstages;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(JMapStages.MOD_ID)
public class JMapStages {
    public static final String MOD_ID = "jmapstages";
    public static final Logger LOGGER = LoggerFactory.getLogger("JMapStages");

    public JMapStages () {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new JMapEventListener());
    }
}