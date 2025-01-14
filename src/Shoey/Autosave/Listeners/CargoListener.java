package Shoey.Autosave.Listeners;

import Shoey.Autosave.MainPlugin;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.CargoGainedListener;
import com.fs.starfarer.api.campaign.listeners.CargoScreenListener;

public class CargoListener implements CargoGainedListener, CargoScreenListener {
    @Override
    public void reportSpecialCargoGainedFromRecoveredDerelict(CargoAPI loot, InteractionDialogAPI dialog) {

    }

    @Override
    public void reportCargoScreenOpened() {

    }

    @Override
    public void reportPlayerLeftCargoPods(SectorEntityToken entity) {
        if (MainPlugin.AfterCargo)
        {
            MainPlugin.pendSave();
        }
    }

    @Override
    public void reportPlayerNonMarketTransaction(PlayerMarketTransaction transaction, InteractionDialogAPI dialog) {
        if (MainPlugin.AfterCargo)
        {
            MainPlugin.pendSave();
        }
    }

    @Override
    public void reportSubmarketOpened(SubmarketAPI submarket) {

    }
}
