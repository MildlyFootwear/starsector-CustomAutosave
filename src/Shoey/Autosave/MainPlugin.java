package Shoey.Autosave;
import Shoey.Autosave.Listeners.CampaignEvents;
import Shoey.Autosave.Listeners.GateListener;
import Shoey.Autosave.Scripts.DayCheckScript;
import Shoey.Autosave.Scripts.TimerScript;
import Shoey.Autosave.Scripts.TransitionTriggerScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import lunalib.lunaSettings.LunaSettings;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class MainPlugin extends BaseModPlugin {

    public static Logger log = Global.getLogger(MainPlugin.class);

    public static float timer = 0;

    public static long lastSaveMillis = 0;

    public static String ID = "ShoeyAutosave";

    public static int MinutesBetweenSaves, MinimumMinutesBetweenTriggers;
    public static boolean SaveAsCopy, HyperspaceToNormal, NormalToHyperspace, MarketTransaction, EveryMonth, AfterCombat, AfterCargo, AfterMarket;

    public static void updateLuna()
    {
        MinutesBetweenSaves = LunaSettings.getInt(ID, "MinutesBetweenSaves");
        MinimumMinutesBetweenTriggers = LunaSettings.getInt(ID, "MinimumMinutesBetweenTriggers");

        SaveAsCopy = LunaSettings.getBoolean(ID, "SaveAsCopy");
        HyperspaceToNormal = LunaSettings.getBoolean(ID, "HyperspaceToNormal");
        NormalToHyperspace = LunaSettings.getBoolean(ID, "NormalToHyperspace");
        MarketTransaction = LunaSettings.getBoolean(ID, "MarketTransaction");
        EveryMonth = LunaSettings.getBoolean(ID, "EveryMonth");
        AfterCombat = LunaSettings.getBoolean(ID, "AfterCombat");
        AfterCargo = LunaSettings.getBoolean(ID, "AfterCargo");
        AfterMarket = LunaSettings.getBoolean(ID, "AfterMarket");

        if (LunaSettings.getBoolean(ID, "Debugging"))
        {
            log.setLevel(Level.DEBUG);
        } else {
            log.setLevel(Level.INFO);
        }
    }

    public static void pendSave()
    {
        TimerScript.pendingSave = true;
        TimerScript.pendingTimer = 5;
    }

    public static void attemptSave()
    {
        CampaignUIAPI cUI = Global.getSector().getCampaignUI();

        if (cUI.isShowingDialog() || cUI.isShowingMenu())
            return;

        log.debug("Saving. "+timer+" seconds since last.");
        cUI.addMessage("Autosaving...");
        if (SaveAsCopy)
        {
            Global.getSector().getCampaignUI().cmdSaveCopy();
        } else {
            Global.getSector().getCampaignUI().cmdSave();
        }
    }

    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();
        log.setLevel(Level.INFO);
        updateLuna();
        LunaSettings.addSettingsListener(new LunaListener());
    }

    @Override
    public void onGameLoad(boolean b) {
        super.onGameLoad(b);
        timer = 0;
        lastSaveMillis = System.currentTimeMillis();

        Global.getSector().addTransientScript(new TimerScript());
        Global.getSector().addTransientScript(new TransitionTriggerScript());
        Global.getSector().addTransientScript(new DayCheckScript());
        Global.getSector().getListenerManager().addListener(new GateListener(), true);
        Global.getSector().addTransientListener(new CampaignEvents());

    }

    @Override
    public void beforeGameSave()
    {
        super.beforeGameSave();
        lastSaveMillis = System.currentTimeMillis();
        TimerScript.pendingSave = false;
    }

    @Override
    public void afterGameSave()
    {
        super.afterGameSave();

    }
}
