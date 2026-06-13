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

package ironfurnaces;

import com.mojang.logging.LogUtils;
import ironfurnaces.capability.CapabilityIronFurnaces;
import ironfurnaces.init.ClientSetup;
import ironfurnaces.init.ModSetup;
import ironfurnaces.init.Registration;
import ironfurnaces.network.Messages;
import ironfurnaces.util.EventHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IronFurnaces.MOD_ID)
public class IronFurnaces
{

    public static final String MOD_ID = "ironfurnaces";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static CreativeModeTab tabIronFurnaces;




    public IronFurnaces(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);


        modEventBus.addListener(ModSetup::init);
        modEventBus.addListener(CapabilityIronFurnaces::register);
        modEventBus.addListener(ClientSetup::init);
        modEventBus.register(Messages.class);

        Registration.init(modEventBus);

        NeoForge.EVENT_BUS.addListener(EventHandler::explosionEvent);




    }

}
