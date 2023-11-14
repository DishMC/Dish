package net.ouja.dish.world.block;

import net.ouja.api.world.level.block.Block;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;

public class DishBlock implements Block {
    private final BlockPos pos;
    private final BlockTypes blockType;
    private final net.minecraft.world.level.block.Block block;

    public DishBlock(BlockPos pos, BlockTypes blockType, net.minecraft.world.level.block.Block block) {
        this.pos = pos;
        this.blockType = blockType;
        this.block = block;
    }

    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }

    @Override
    public BlockTypes getType() {
        return blockType;
    }
}
