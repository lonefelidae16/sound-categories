package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public abstract class VersionedSoundGroupOptionsScreen extends AbstractSoundListedScreen {
    protected final SoundCategory parentCategory;

    public VersionedSoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable(SoundCategories.OPTION_PREFIX_SOUND_CAT + category.getName()));
        this.parentCategory = category;
    }

    protected abstract void addParentCategoryWidget();

    public static VersionedSoundGroupOptionsScreen newInstance(Screen parent, GameOptions settings, SoundCategory category) {
        try {
            Class<VersionedSoundGroupOptionsScreen> screen = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.SoundGroupOptionsScreen");
            Constructor<VersionedSoundGroupOptionsScreen> constructor = screen.getConstructor(Screen.class, GameOptions.class, SoundCategory.class);
            return constructor.newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

    @Override
    protected void init() {
        super.init();

        try {
            Objects.requireNonNull(this.list);
        } catch (NullPointerException ex) {
            SoundCategories.LOGGER.error("Error during screen initialization", ex);
            return;
        }

        this.addParentCategoryWidget();

        final SoundCategory[] categories = Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == this.parentCategory;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(categories);

        this.addDrawableChild((Element & Drawable & Selectable) this.list);
    }
}
