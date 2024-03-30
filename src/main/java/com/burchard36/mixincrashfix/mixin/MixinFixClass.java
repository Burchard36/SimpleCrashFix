package com.burchard36.mixincrashfix.mixin;

import elucent.eidolon.api.spells.Spell;
import elucent.eidolon.registries.Spells;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that prevent eid configs from being loaded to prevent a server crash with the latest 2.55 version of ATM9
 *
 * @author kristi71111
 */
@Mixin(Spells.class)
public abstract class MixinFixClass {

    @Final
    @Shadow(remap = false)
    static final Map<ResourceLocation, Spell> spellMap = new HashMap();

    /**
     * @author kristi1111
     * @reason Revert this back to the previous versions way of doing it, literally what was wrong with this?
     */
    @Overwrite(remap = false)
    public static Spell register(Spell spell) {
        spellMap.put(spell.getRegistryName(), spell);
        ForgeConfigSpec spec;
        ForgeConfigSpec.Builder spellBuilder = new ForgeConfigSpec.Builder();
        spell.buildConfig(spellBuilder);
        spec = spellBuilder.build();
        spell.CONFIG = spec;
        return spell;
    }
}
