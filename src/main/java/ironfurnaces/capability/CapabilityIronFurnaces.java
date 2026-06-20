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

package ironfurnaces.capability;

import ironfurnaces.energy.FEnergyStorage;
import ironfurnaces.init.Registration;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.List;

public class CapabilityIronFurnaces {

    //public static final EntityCapability<IPlayerFurnacesList, Void> FURNACES_LIST = EntityCapability.createVoid(Identifier.fromNamespaceAndPath(IronFurnaces.MOD_ID, "furnace_list"), IPlayerFurnacesList.class);


    public static void register(RegisterCapabilitiesEvent event)
    {

                List<Block> furnaces = List.of(
                Registration.COPPER_FURNACE.get(),
                Registration.CRYSTAL_FURNACE.get(),
                Registration.DIAMOND_FURNACE.get(),
                Registration.EMERALD_FURNACE.get(),
                Registration.GOLD_FURNACE.get(),
                Registration.IRON_FURNACE.get(),
                Registration.MILLION_FURNACE.get(),
                Registration.NETHERITE_FURNACE.get(),
                Registration.OBSIDIAN_FURNACE.get(),
                Registration.SILVER_FURNACE.get(),
                Registration.ALLTHEMODIUM_FURNACE.get(),
                Registration.VIBRANIUM_FURNACE.get(),
                Registration.UNOBTAINIUM_FURNACE.get()
        );


        event.registerBlock(Capabilities.Item.BLOCK,
                                (level, pos, state, be, side) -> getItemHandler((Container) be, side),
                // blocks to register for
                Registration.HEATER.get());

        event.registerBlock(Capabilities.Energy.BLOCK,
                                (level, pos, state, be, side) -> wrapEnergy(((BlockWirelessEnergyHeaterTile) be).energyStorage),
                // blocks to register for
                Registration.HEATER.get());

        for (Block furnace : furnaces) {
            event.registerBlock(Capabilities.Item.BLOCK,
                                        (level, pos, state, be, side) -> getItemHandler((Container) be, side),
                    // blocks to register for
                    furnace
            );
            event.registerBlock(Capabilities.Energy.BLOCK,
                                        (level, pos, state, be, side) -> wrapEnergy(((BlockIronFurnaceTileBase) be).energyStorage),
                    // blocks to register for
                    furnace
            );
            event.registerBlock(Capabilities.Fluid.BLOCK,
                                        (level, pos, state, be, side) -> getFluidHandler((BlockIronFurnaceTileBase) be),
                    // blocks to register for
                    furnace
            );
        }

    }

        private static ResourceHandler<ItemResource> getItemHandler(Container container, Direction side) {
                if (side != null && container instanceof WorldlyContainer worldlyContainer) {
                        return new WorldlyContainerWrapper(worldlyContainer, side);
                }
                return VanillaContainerWrapper.of(container);
        }

        private static ResourceHandler<FluidResource> getFluidHandler(BlockIronFurnaceTileBase furnace) {
                return furnace.fluidStorage;
        }

        private static EnergyHandler wrapEnergy(FEnergyStorage energyStorage) {
                return new EnergyHandler() {
                        @Override
                        public long getAmountAsLong() {
                                return energyStorage.getEnergyStored();
                        }

                        @Override
                        public long getCapacityAsLong() {
                                return energyStorage.getMaxEnergyStored();
                        }

                        @Override
                        public int insert(int maxAmount, TransactionContext context) {
                                return energyStorage.receiveEnergy(maxAmount, false);
                        }

                        @Override
                        public int extract(int maxAmount, TransactionContext context) {
                                return energyStorage.extractEnergy(maxAmount, false);
                        }
                };
        }

}

