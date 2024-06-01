package dev.stashy.soundcategories;

import dev.stashy.soundcategories.shared.SoundCategories;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public final class SoundCategoriesBootstrap implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        SoundCategories.setup();
    }
}
