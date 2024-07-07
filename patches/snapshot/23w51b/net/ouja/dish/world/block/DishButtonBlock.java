package net.ouja.dish.world.block;

import net.minecraft.world.level.block.Block;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.ButtonBlock;

public class DishButtonBlock extends DishBlock implements ButtonBlock {
    private final net.minecraft.world.level.block.ButtonBlock buttonBlock;

    public DishButtonBlock(BlockPos pos, BlockTypes blockType, Block block) {
        super(pos, blockType, block);
        this.buttonBlock = (net.minecraft.world.level.block.ButtonBlock) block;
    }

    @Override
    public BlockPos getBlockPos() {
        return super.getBlockPos();
    }

    @Override
    public BlockTypes getType() {
        return super.getType();
    }

    @Override
    public int getSignal() {
        return this.buttonBlock.defaultBlockState().getValue(net.minecraft.world.level.block.ButtonBlock.POWERED) ? 15 : 0;
    }
}
