package net.ouja.dish.world.block.entity;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.ouja.api.network.chat.Component;
import net.ouja.api.world.level.block.entity.SignText;
import net.ouja.dish.network.chat.DishComponent;

public class DishSignText implements SignText {
    private final SignBlockEntity signBlockEntity;
    private final boolean isFront;

    public DishSignText(SignBlockEntity signBlockEntity, boolean front) {
        this.signBlockEntity = signBlockEntity;
        this.isFront = front;
    }

    @Override
    public Component[] getText(boolean filtered) {
        if (signBlockEntity == null) return new Component[0];
        return isFront ? DishComponent.fromComponent(signBlockEntity.getFrontText().getMessages(filtered)) : DishComponent.fromComponent(signBlockEntity.getBackText().getMessages(filtered));
    }

    @Override
    public boolean isGlowing() {
        return isFront ? signBlockEntity.getFrontText().hasGlowingText() : signBlockEntity.getBackText().hasGlowingText();
    }

    @Override
    public void setGlowing(boolean b) {
        net.minecraft.world.level.block.entity.SignText text = isFront ?
                signBlockEntity.getFrontText().setHasGlowingText(b) :
                signBlockEntity.getBackText().setHasGlowingText(b);

        setText(text);
    }

    @Override
    public void setMessage(int i, Component component) {
        this.setMessage(i, component, component);
    }

    @Override
    public void setMessage(int i, Component message, Component filtered) {
        net.minecraft.world.level.block.entity.SignText text = isFront ?
                signBlockEntity.getFrontText().setMessage(i, DishComponent.toComponent(message), DishComponent.toComponent(filtered)) :
                signBlockEntity.getBackText().setMessage(i, DishComponent.toComponent(message), DishComponent.toComponent(filtered));

        setText(text);
    }

    private void setText(net.minecraft.world.level.block.entity.SignText text) {
        signBlockEntity.updateText((signText) -> text, isFront);
    }
}
