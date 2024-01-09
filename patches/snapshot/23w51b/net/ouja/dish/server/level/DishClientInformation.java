package net.ouja.dish.server.level;

import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.ouja.api.server.level.ClientInformation;
import org.jetbrains.annotations.NotNull;

public class DishClientInformation implements ClientInformation {
    private final String language;
    private final int viewDistance;
    private final ChatVisiblity chatVisibility;
    private final boolean chatColors;
    private final int modelCustomisation;
    private final HumanoidArm mainHand;
    private final boolean textFilteringEnabled;
    private final boolean allowsListing;

    public DishClientInformation(String language, int viewDistance, ChatVisiblity chatVisibility, boolean chatColors, int modelCustomisation, HumanoidArm mainHand, boolean textFilteringEnabled, boolean allowsListing) {
        this.language = language;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.chatColors = chatColors;
        this.modelCustomisation = modelCustomisation;
        this.mainHand = mainHand;
        this.textFilteringEnabled = textFilteringEnabled;
        this.allowsListing = allowsListing;
    }

    public DishClientInformation(@NotNull net.minecraft.server.level.ClientInformation mcInfo) {
        this(
           mcInfo.language(),
           mcInfo.viewDistance(),
           mcInfo.chatVisibility(),
           mcInfo.chatColors(),
           mcInfo.modelCustomisation(),
           mcInfo.mainHand(),
           mcInfo.textFilteringEnabled(),
           mcInfo.allowsListing()
        );
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public int getViewDistance() {
        return viewDistance;
    }

    @Override
    public int getChatVisibility() {
        return chatVisibility.getId();
    }

    @Override
    public boolean isChatColors() {
        return chatColors;
    }

    @Override
    public int getModelCustomisation() {
        return modelCustomisation;
    }

    @Override
    public String getMainHand() {
        return mainHand.getSerializedName();
    }

    @Override
    public boolean isTextFilteringEnabled() {
        return textFilteringEnabled;
    }

    @Override
    public boolean isAllowsListing() {
        return allowsListing;
    }
}
