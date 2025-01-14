package Shoey.Autosave;

import lunalib.lunaSettings.LunaSettingsListener;
import org.jetbrains.annotations.NotNull;

public class LunaListener implements LunaSettingsListener {
    @Override
    public void settingsChanged(@NotNull String s) {
        if (s.equals(MainPlugin.ID))
        {
            MainPlugin.updateLuna();
        }
    }
}
