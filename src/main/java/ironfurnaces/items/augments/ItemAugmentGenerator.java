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

package ironfurnaces.items.augments;

import ironfurnaces.IronFurnaces;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.api.distmarker.Dist;

import java.util.List;
import java.util.function.Consumer;

public class ItemAugmentGenerator extends ItemAugmentBlue {

    public ItemAugmentGenerator(Properties properties) {
        super(properties);
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext pContext, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag pTooltipFlag) {

        super.appendHoverText(stack, pContext, tooltipDisplay, tooltipAdder, pTooltipFlag);

        tooltipAdder.accept(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".augment_generator_pro").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GREEN))));
        tooltipAdder.accept(Component.translatable("tooltip." + IronFurnaces.MOD_ID + ".augment_generator_con").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)));


    }




}
