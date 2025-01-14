package Shoey.Autosave.Listeners;

import Shoey.Autosave.MainPlugin;
import Shoey.Autosave.Scripts.TimerScript;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.listeners.GateTransitListener;

import static Shoey.Autosave.MainPlugin.*;
import static Shoey.Autosave.MainPlugin.timer;

public class GateListener implements GateTransitListener {
    @Override
    public void reportFleetTransitingGate(CampaignFleetAPI fleet, SectorEntityToken gateFrom, SectorEntityToken gateTo) {
        log.debug("Gate event " + timer);
        if (timer < MinimumMinutesBetweenTriggers * 60) {
            log.debug("Minimum trigger time not elapsed, timer at " + timer);
            return;
        }

        TimerScript.pendingSave = true;
        TimerScript.pendingTimer = 10;
    }
}
