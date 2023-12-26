package net.ouja.dish.world.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.ouja.api.Player;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.TrapdoorBlock;

import javax.annotation.Nullable;

public class DishTrapdoorBlock extends DishBlock implements TrapdoorBlock {
    private final TrapDoorBlock trapdoor;
    private final Level level;

    public DishTrapdoorBlock(BlockPos pos, BlockTypes blockType, Block block, Level level) {
        super(pos, blockType, block);
        this.trapdoor = (TrapDoorBlock) block;
        this.level = level;
    }

    @Override
    public boolean isOpen() {
        return this.trapdoor.defaultBlockState().getValue(TrapDoorBlock.OPEN);
    }

    @Override
    public void setOpen(@Nullable Player player, boolean open) {
        net.minecraft.core.BlockPos blockPos = new net.minecraft.core.BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ());
        this.trapdoor.setOpen(player != null ? this.level.getPlayerByUUID(player.getUUID()) : null, this.level, this.trapdoor.defaultBlockState(), blockPos, open);
    }

    @Override
    public boolean isWaterLogged() {
        return this.trapdoor.defaultBlockState().getValue(TrapDoorBlock.WATERLOGGED);
    }
}
