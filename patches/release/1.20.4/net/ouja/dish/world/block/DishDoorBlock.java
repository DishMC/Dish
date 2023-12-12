package net.ouja.dish.world.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.ouja.api.Entity;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.DoorBlock;

import javax.annotation.Nullable;

public class DishDoorBlock extends DishBlock implements DoorBlock {
    private final net.minecraft.world.level.block.DoorBlock door;
    private final Level level;

    public DishDoorBlock(BlockPos pos, BlockTypes blockType, Block block, Level level) {
        super(pos, blockType, block);
        this.door = (net.minecraft.world.level.block.DoorBlock) block;
        this.level = level;
    }

    @Override
    public boolean isOpen() {
        return this.door.isOpen(this.door.defaultBlockState());
    }

    @Override
    public void setOpen(@Nullable Entity entity, boolean open) {
        net.minecraft.core.BlockPos blockPos = new net.minecraft.core.BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ());
        this.door.setOpen(entity != null ? this.level.getEntity(entity.getId()) : null, this.level, this.door.defaultBlockState(), blockPos, open);
    }
}
