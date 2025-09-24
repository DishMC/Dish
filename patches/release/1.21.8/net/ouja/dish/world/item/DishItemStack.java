package net.ouja.dish.world.item;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.ouja.api.network.chat.Component;
import net.ouja.api.world.item.Item;
import net.ouja.api.world.item.ItemStack;
import net.ouja.dish.network.chat.DishComponent;

public class DishItemStack implements ItemStack {
    private final net.minecraft.world.item.ItemStack itemStack;

    public DishItemStack(net.minecraft.world.item.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Converts vanilla's ObjectArrayList<ItemStack> to Dishes version of it
     * @param items vanilla items
     * @return Dishes variant of the ItemStacks.
     */
    public static ObjectArrayList<ItemStack> convertFromVanilla(ObjectArrayList<net.minecraft.world.item.ItemStack> items) {
        ObjectArrayList<ItemStack> dishItems = new ObjectArrayList<>();

        for (net.minecraft.world.item.ItemStack vanillaItem : items) {
            dishItems.add(new DishItemStack(vanillaItem));
        }

        return dishItems;
    }

    // TODO: 11/15/2023 Make Item classes
    @Override
    public Item getItem() {
        return null;
    }

    @Override
    public boolean is(Item item) {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return this.itemStack.getMaxStackSize();
    }

    @Override
    public boolean isDamageableItem() {
        return this.itemStack.isDamageableItem();
    }

    @Override
    public boolean isDamaged() {
        return this.itemStack.isDamageableItem();
    }

    @Override
    public int getDamageValue() {
        return this.itemStack.getDamageValue();
    }

    @Override
    public void setDamageValue(int i) {
        this.itemStack.setDamageValue(i);
    }

    @Override
    public int getMaxDamage() {
        return this.itemStack.getMaxDamage();
    }

    @Override
    public ItemStack copy() {
        if (this.itemStack.isEmpty()) {
            return new DishItemStack(net.minecraft.world.item.ItemStack.EMPTY);
        }

        net.minecraft.world.item.ItemStack itemstack = new net.minecraft.world.item.ItemStack((Holder<net.minecraft.world.item.Item>)this.itemStack.getItem(), this.itemStack.getCount(), this.itemStack.getComponentsPatch());
        itemstack.setPopTime(this.itemStack.getPopTime());

        return new DishItemStack(itemstack);
    }

    @Override
    public ItemStack copyWithCount(int i) {
        if (this.itemStack.isEmpty()) {
            return new DishItemStack(net.minecraft.world.item.ItemStack.EMPTY);
        }

        net.minecraft.world.item.ItemStack itemstack = new net.minecraft.world.item.ItemStack((Holder<net.minecraft.world.item.Item>)this.itemStack.getItem(), this.itemStack.getCount(), this.itemStack.getComponentsPatch());
        itemstack.setPopTime(this.itemStack.getPopTime());

        itemstack.setCount(i);
        return new DishItemStack(itemstack);
    }

    @Override
    public Component getHoverName() {
        return DishComponent.fromComponent(this.itemStack.getHoverName());
    }

    @Override
    public void setHoverName(Component component) {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(this.itemStack.getComponents());
        builder.set(DataComponents.CUSTOM_NAME, DishComponent.toComponent(component));
        this.itemStack.applyComponents(builder.build());
    }

    @Override
    public void resetHoverName() {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(this.itemStack.getComponents());
        builder.set(DataComponents.CUSTOM_NAME, this.itemStack.getItem().getName(this.itemStack));
        this.itemStack.applyComponents(builder.build());
    }

    @Override
    public boolean hasCustomHoverName() {
        return this.itemStack.getComponents().has(DataComponents.CUSTOM_NAME);
    }

    @Override
    public Component getDisplayName() {
        return DishComponent.fromComponent(this.itemStack.getDisplayName());
    }

    @Override
    public int getCount() {
        return this.itemStack.getCount();
    }

    @Override
    public void setCount(int i) {
        this.itemStack.setCount(i);
    }
}
