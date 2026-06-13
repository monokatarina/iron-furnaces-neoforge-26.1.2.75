/*
 * Copyright 2025 pizzaatime and XenoMustache
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.util;

import ironfurnaces.Config;
import ironfurnaces.IronFurnaces;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FurnaceSettings {
    public int[] settings;
    public int[] autoIO;
    public int[] redstoneSettings;
    public int augmentGUI;
    public int autoSplit;

    public FurnaceSettings() {
        settings = new int[]{0, 0, 0, 0, 0, 0};
        autoIO = new int[]{0, 0};
        // (mode 1, 2, 3, 4, subtract) ignored, low/high, comparator, comparator sub, subtract
        redstoneSettings = new int[]{0, 0};
        augmentGUI = 0;
        autoSplit = 0;
    }

    public int get(int index) {
        try {
            switch (index) {
                case 0:
                    return settings[0];
                case 1:
                    return settings[1];
                case 2:
                    return settings[2];
                case 3:
                    return settings[3];
                case 4:
                    return settings[4];
                case 5:
                    return settings[5];
                case 6:
                    return autoIO[0];
                case 7:
                    return autoIO[1];
                case 8:
                    return redstoneSettings[0];
                case 9:
                    return redstoneSettings[1];
                case 10:
                    return augmentGUI;
                case 11:
                    return autoSplit;
                default:
                    return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (Config.showErrors.get()) {
                IronFurnaces.LOGGER.error("Something went wrong.");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
                }
            }
        }
        return 0;
    }

    public void set(int index, int value) {
        try {
            switch (index) {
                case 0:
                    settings[0] = value;
                    break;
                case 1:
                    settings[1] = value;
                    break;
                case 2:
                    settings[2] = value;
                    break;
                case 3:
                    settings[3] = value;
                    break;
                case 4:
                    settings[4] = value;
                    break;
                case 5:
                    settings[5] = value;
                    break;
                case 6:
                    autoIO[0] = value;
                    break;
                case 7:
                    autoIO[1] = value;
                    break;
                case 8:
                    redstoneSettings[0] = value;
                    break;
                case 9:
                    redstoneSettings[1] = value;
                    break;
                case 10:
                    augmentGUI = value;
                    break;
                case 11:
                    autoSplit = value;
                    break;
                default:
                    break;
            }
            onChanged();
        } catch (ArrayIndexOutOfBoundsException e) {
            if (Config.showErrors.get()) {
                IronFurnaces.LOGGER.error("Something went wrong.");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    IronFurnaces.LOGGER.error(e.getStackTrace()[i].toString());
                }
            }
        }
    }

    public int size() {
        return settings.length + autoIO.length + redstoneSettings.length + 2;
    }

    public void read(CompoundTag tag) {
        this.settings = tag.getIntArray("Settings").orElse(new int[]{0, 0, 0, 0, 0, 0});
        this.autoIO = tag.getIntArray("AutoIO").orElse(new int[]{0, 0});
        this.redstoneSettings = tag.getIntArray("Redstone").orElse(new int[]{0, 0});
        this.augmentGUI = tag.getInt("AugmentGUI").orElse(0);
        this.autoSplit = tag.getInt("AutoSplit").orElse(0);
        onChanged();
    }

    public void read(ValueInput input) {
        this.settings = input.getIntArray("Settings").orElse(new int[]{0, 0, 0, 0, 0, 0});
        this.autoIO = input.getIntArray("AutoIO").orElse(new int[]{0, 0});
        this.redstoneSettings = input.getIntArray("Redstone").orElse(new int[]{0, 0});
        this.augmentGUI = input.getIntOr("AugmentGUI", 0);
        this.autoSplit = input.getIntOr("AutoSplit", 0);
        onChanged();
    }

    public void write(CompoundTag tag) {
        tag.putIntArray("Settings", settings);
        tag.putIntArray("AutoIO", autoIO);
        tag.putIntArray("Redstone", redstoneSettings);
        tag.putInt("AugmentGUI", augmentGUI);
        tag.putInt("AutoSplit", autoSplit);
    }

    public void write(ValueOutput output) {
        output.putIntArray("Settings", settings);
        output.putIntArray("AutoIO", autoIO);
        output.putIntArray("Redstone", redstoneSettings);
        output.putInt("AugmentGUI", augmentGUI);
        output.putInt("AutoSplit", autoSplit);
    }

    public void onChanged() {

    }
}