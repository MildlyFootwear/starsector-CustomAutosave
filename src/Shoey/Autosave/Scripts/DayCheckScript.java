package Shoey.Autosave.Scripts;

import Shoey.Autosave.MainPlugin;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;

public class DayCheckScript implements EveryFrameScript {

    int day = 0;

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        if (MainPlugin.EveryMonth && day != Global.getSector().getClock().getDay())
        {
            day = Global.getSector().getClock().getDay();
            if (day == 2)
            {
                MainPlugin.pendSave();
            }
        }
    }
}
