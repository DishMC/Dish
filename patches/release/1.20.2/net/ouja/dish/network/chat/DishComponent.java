package net.ouja.dish.network.chat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DishComponent implements Component {
    private final Component component;

    public DishComponent(net.ouja.api.network.chat.Component fakeComponent) {
        this.component = Component.literal(fakeComponent.getString())
           .setStyle(
              Style.EMPTY
                 .withColor(TextColor.parseColor(fakeComponent.getColor()).getValue())
                 .withBold(fakeComponent.isBold())
                 .withUnderlined(fakeComponent.isUnderlined())
                 .withStrikethrough(fakeComponent.isStrikeThrough())
                 .withItalic(fakeComponent.isItalic())
           );
    }

    @Override
    public Style getStyle() {
        return this.component.getStyle();
    }

    @Override
    public ComponentContents getContents() {
        return this.component.getContents();
    }

    @Override
    public @NotNull String getString() {
        return this.component.getString();
    }

    @Override
    public List<Component> getSiblings() {
        return this.component.getSiblings();
    }

    @Override
    public FormattedCharSequence getVisualOrderText() {
        return this.component.getVisualOrderText();
    }

    /**
     * Converts a vanilla component into Dish's version of component
     * @param component vanilla component class
     * @return DishComponent
     */
    public static net.ouja.api.network.chat.Component fromComponent(Component component) {
        return net.ouja.api.network.chat.Component.create(component.getString(), component.getStyle().isBold(), component.getStyle().isUnderlined(), component.getStyle().isStrikethrough(), component.getStyle().isItalic());
    }

    public static net.ouja.api.network.chat.Component[] fromComponent(Component[] componentsIn) {
        net.ouja.api.network.chat.Component[] components = new net.ouja.api.network.chat.Component[componentsIn.length];

        for (int i = 0; i < components.length; i++) {
            Component component = componentsIn[i];
            components[i] = fromComponent(component);
        }

        return components;
    }

    public static MutableComponent toComponent(net.ouja.api.network.chat.Component component) {
        Style style = Style.EMPTY
                .withColor(TextColor.parseColor(component.getColor()).getValue())
                .withBold(component.isBold())
                .withUnderlined(component.isUnderlined())
                .withStrikethrough(component.isStrikeThrough())
                .withItalic(component.isItalic());
        return Component.literal(component.getString()).withStyle(style);
    }
}
