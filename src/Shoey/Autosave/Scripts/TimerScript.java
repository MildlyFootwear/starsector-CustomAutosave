package Shoey.Autosave.Scripts;

import Shoey.Autosave.MainPlugin;
import com.fs.graphics.G;
import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;

public class TimerScript implements EveryFrameScript {

    public static float pendingTimer = 0;
    public static boolean pendingSave = false;

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
        if (pendingSave)
        {
            if (Global.getSector().getCampaignUI().isShowingMenu() || Global.getSector().getCampaignUI().isShowingDialog())
            {
                pendingTimer = 5;
                return;
            }
            
            pendingTimer -= amount;
            if (pendingTimer < 0)
            {
                MainPlugin.attemptSave();
                pendingTimer = 5;
            }
        } else {
            MainPlugin.timer = (float) (System.currentTimeMillis() - MainPlugin.lastSaveMillis) / 1000;
            if (MainPlugin.timer > MainPlugin.MinutesBetweenSaves * 60) {
                MainPlugin.attemptSave();
            }
        }
    }
}
