package net.ouja.dish.world.level.chunk;

import net.ouja.api.world.level.chunk.Structure;
import net.ouja.api.world.level.chunk.StructureTypes;

public class DishStructure implements Structure {
    private final net.minecraft.world.level.levelgen.structure.Structure structure;

    public DishStructure(net.minecraft.world.level.levelgen.structure.Structure structure) {
        this.structure = structure;
    }

    @Override
    public StructureTypes getType() {
        if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.BURIED_TREASURE) {
            return StructureTypes.BURIED_TREASURE;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.DESERT_PYRAMID) {
            return StructureTypes.DESERT_PYRAMID;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.END_CITY) {
            return StructureTypes.END_CITY;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.FORTRESS) {
            return StructureTypes.FORTRESS;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.IGLOO) {
            return StructureTypes.IGLOO;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.JIGSAW) {
            return StructureTypes.JIGSAW;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.JUNGLE_TEMPLE) {
            return StructureTypes.JUNGLE_TEMPLE;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.MINESHAFT) {
            return StructureTypes.MINESHAFT;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.NETHER_FOSSIL) {
            return StructureTypes.NETHER_FOSSIL;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.OCEAN_MONUMENT) {
            return StructureTypes.OCEAN_MONUMENT;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.OCEAN_RUIN) {
            return StructureTypes.OCEAN_RUIN;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.RUINED_PORTAL) {
            return StructureTypes.RUINED_PORTAL;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.SHIPWRECK) {
            return StructureTypes.SHIPWRECK;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.STRONGHOLD) {
            return StructureTypes.STRONGHOLD;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.SWAMP_HUT) {
            return StructureTypes.SWAMP_HUT;
        } else if (structure.type() == net.minecraft.world.level.levelgen.structure.StructureType.WOODLAND_MANSION) {
            return StructureTypes.WOODLAND_MANSION;
        } else {
            return null;
        }

    }
}
