package net.ouja.dish.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.WaterLoggableBlock;

public class DishStairBlock extends DishBlock implements WaterLoggableBlock, net.ouja.api.world.level.block.StairBlock {
    private final StairBlock block;

    public DishStairBlock(BlockPos pos, BlockTypes blockType, Block block) {
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
