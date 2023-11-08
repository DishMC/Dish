package net.ouja.dish.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.ouja.api.world.level.block.BlockPos;
import net.ouja.api.world.level.block.BlockTypes;
import net.ouja.api.world.level.block.WaterLoggableBlock;
import net.ouja.api.world.level.block.entity.SignText;
import net.ouja.dish.world.block.entity.DishSignText;

public class DishSignBlock extends DishBlock implements WaterLoggableBlock, net.ouja.api.world.level.block.SignBlock {
    private final SignBlock block;
    private final SignBlockEntity signBlockEntity;
    private final boolean isWallSign;
    private final boolean isHangingSign;

    public DishSignBlock(BlockPos pos, BlockTypes blockType, Block block, SignBlockEntity signBlockEntity) {
        super(pos, blockType, block);
        this.block = (SignBlock) block;
        this.signBlockEntity = signBlockEntity;
        this.isWallSign = blockType.getBlockName().contains("_wall_");
        this.isHangingSign = blockType.getBlockName().endsWith("_hanging_sign");
    }

    @Override
    public boolean isWaterLogged() {
        return !this.block.getFluidState(this.block.defaultBlockState()).isEmpty();
    }

    @Override
    public boolean isWallSign() {
        return isWallSign;
    }

    @Override
    public boolean isHangingSign() {
        return isHangingSign;
    }

    @Override
    public boolean isWaxed() {
        return signBlockEntity.isWaxed();
    }

    @Override
    public void setWaxed(boolean waxed) {
        signBlockEntity.setWaxed(waxed);
    }

    @Override
    public SignText getFrontText() {
        return new DishSignText(signBlockEntity,true);
    }

    @Override
    public SignText getBackText() {
        return new DishSignText(signBlockEntity,false);
    }
}
