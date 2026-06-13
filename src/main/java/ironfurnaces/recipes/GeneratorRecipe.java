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

package ironfurnaces.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ironfurnaces.init.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class GeneratorRecipe implements Recipe<SingleRecipeInput> {

    public static final MapCodec<GeneratorRecipe> CODEC = RecordCodecBuilder.mapCodec(
            h -> h.group(
                            Codec.INT.optionalFieldOf("energy", 0).forGetter((GeneratorRecipe recipe) -> recipe.energy),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter((recipe) -> recipe.ingredient)
                    )
                    .apply(h, GeneratorRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GeneratorRecipe> STREAM_CODEC = StreamCodec.of(GeneratorRecipe::toNetwork, GeneratorRecipe::fromNetwork);


    public int energy;
    public Ingredient ingredient;

    public GeneratorRecipe(int energy, Ingredient ingredient)
    {
        this.energy = energy;
        this.ingredient = ingredient;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    public boolean isIncomplete() {
        return ingredient.items().findAny().isEmpty();
    }


    public int getEnergy()
    {
        return energy;
    }


    @Override
    public boolean matches(SingleRecipeInput inv, Level p_44003_) {
        return this.ingredient.test(inv.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput p_343633_) {
        return ItemStack.EMPTY;
    }

    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<GeneratorRecipe> getSerializer() {
        return Registration.GENERATOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<GeneratorRecipe> getType() {
        return Registration.GENERATOR_RECIPE_TYPE.get();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return new RecipeBookCategory();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.isIncomplete()) {
            return PlacementInfo.NOT_PLACEABLE;
        }

        return PlacementInfo.create(this.ingredient);
    }

    private static GeneratorRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        int energy = buf.readVarInt();
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        return new GeneratorRecipe(energy, ingredient);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buf, GeneratorRecipe recipe) {
        buf.writeVarInt(recipe.energy);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
    }
}
