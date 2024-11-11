package kaptainwutax.seedcrackerX.util;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

import java.util.HashMap;
import java.util.Map;

public class BiomeFixer {

    private static final Map<String, Biome> COMPATREGISTRY = new HashMap<>();

    static {
        for (Biome biome : Biomes.REGISTRY.values()) {
            COMPATREGISTRY.put(biome.getName(), biome);
        }
        COMPATREGISTRY.put("snowy_plains", Biomes.SNOWY_TUNDRA);
        COMPATREGISTRY.put("old_growth_birch_forest", Biomes.TALL_BIRCH_FOREST);
        COMPATREGISTRY.put("old_growth_pine_taiga", Biomes.GIANT_TREE_TAIGA);
        COMPATREGISTRY.put("old_growth_spruce_taiga", Biomes.GIANT_TREE_TAIGA);
        COMPATREGISTRY.put("windswept_hills", Biomes.EXTREME_HILLS);
        COMPATREGISTRY.put("windswept_forest", Biomes.WOODED_MOUNTAINS);
        COMPATREGISTRY.put("windswept_gravelly_hills", Biomes.GRAVELLY_MOUNTAINS);
        COMPATREGISTRY.put("windswept_savanna", Biomes.SHATTERED_SAVANNA);
        COMPATREGISTRY.put("sparse_jungle", Biomes.JUNGLE_EDGE);
        COMPATREGISTRY.put("stony_shore", Biomes.STONE_SHORE);
        // 新增的生態群系對應
        COMPATREGISTRY.put("meadow", Biomes.PLAINS);
        COMPATREGISTRY.put("grove", Biomes.TAIGA);
        COMPATREGISTRY.put("snowy_slopes", Biomes.SNOWY_TUNDRA);
        COMPATREGISTRY.put("frozen_peaks", Biomes.TAIGA);
        COMPATREGISTRY.put("jagged_peaks", Biomes.TAIGA);
        COMPATREGISTRY.put("stony_peaks", Biomes.TAIGA);
        COMPATREGISTRY.put("mangrove_swamp", Biomes.SWAMP);
    }

    public static Biome swap(net.minecraft.world.biome.Biome biome) {
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler == null) return Biomes.VOID;

        Identifier biomeID = networkHandler.getRegistryManager().getOrThrow(RegistryKeys.BIOME).getId(biome);

        if (biomeID == null) return Biomes.THE_VOID;

        return COMPATREGISTRY.getOrDefault(biomeID.getPath(), Biomes.VOID);
    }

    public static net.minecraft.world.biome.Biome swap(Biome biome) {
        var biomeRegistries = BuiltinRegistries.createWrapperLookup().getOptional(RegistryKeys.BIOME);

        return biomeRegistries.get().getOptional(RegistryKey.of(RegistryKeys.BIOME, Identifier.ofVanilla(biome.getName()))).orElse(
                biomeRegistries.get().getOrThrow(BiomeKeys.THE_VOID)
        ).value();
    }
}
