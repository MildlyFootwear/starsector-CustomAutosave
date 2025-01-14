package Shoey.Autosave.Scripts;

import Shoey.Autosave.MainPlugin;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;

import static Shoey.Autosave.MainPlugin.*;

public class TransitionTriggerScript implements EveryFrameScript {

    boolean lastHyperspace = false, init = false;

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return true;
    }

    @Override
    public void advance(float amount) {

        boolean isHyperspace = Global.getSector().getPlayerFleet().isInHyperspace();
        if (!init)
        {
            lastHyperspace = isHyperspace;
            init = true;
        }
        if (lastHyperspace != isHyperspace)
        {
            lastHyperspace = isHyperspace;

            if (isHyperspace)
                log.debug("Player transitioned to hyperspace");
            else
                log.debug("Player transitioned to normal space");

            if (timer < MinimumMinutesBetweenTriggers * 60) {
                log.debug("Minimum trigger time not elapsed, timer at " + timer);
                return;
            }
            if (isHyperspace && MainPlugin.NormalToHyperspace)
            {
                MainPlugin.pendSave();
            } else if (!isHyperspace && HyperspaceToNormal) {
                MainPlugin.pendSave();
            }
        }
    }
}
