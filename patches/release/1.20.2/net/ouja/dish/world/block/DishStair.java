package net.ouja.dish.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.Stair;
import net.ouja.api.world.level.block.WaterLoggableBlock;

public class DishStair extends DishBlock implements WaterLoggableBlock, Stair {
    private final StairBlock block;

    public DishStair(BlockPos pos, BlockTypes blockType, Block block) {
        super(pos, blockType, block);
        this.block = (StairBlock) block;
    }

    @Override
    public boolean isWaterLogged() {
        return !this.block.getFluidState(this.block.defaultBlockState()).isEmpty();
    }

    @Override
    public String direction() {
        return this.block.defaultBlockState().getValue(StairBlock.FACING).getName();
    }
}
