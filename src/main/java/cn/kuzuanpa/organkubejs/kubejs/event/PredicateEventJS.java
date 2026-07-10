package cn.kuzuanpa.organkubejs.kubejs.event;

import cn.kuzuanpa.organapi.api.query.OrganPosition;
import cn.kuzuanpa.organeffects.api.EffectDefinition;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class PredicateEventJS extends EventJS {
    private ServerPlayer player;
    private String callback;
    private String organId;
    private String bodyPartId;
    private int slotIndex;
    private EffectDefinition.Condition condition;

    public PredicateEventJS() {
        this.slotIndex = -1;
    }

    public PredicateEventJS(ServerPlayer player, String callback, ResourceLocation organId, OrganPosition position, EffectDefinition.Condition condition) {
        this.player = player;
        this.callback = callback;
        this.organId = organId != null ? organId.toString() : "";
        this.bodyPartId = position != null ? position.bodyPartId().toString() : "";
        this.slotIndex = position != null ? position.slotIndex() : -1;
        this.condition = condition;
    }

    public MinecraftServer getServer() {
        return player != null ? player.server : null;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public String getCallback() {
        return callback;
    }

    public String getOrganId() {
        return organId;
    }

    public String getBodyPartId() {
        return bodyPartId;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public String configString(String key) {
        if (condition == null) {
            return null;
        }
        String extra = condition.extraString(key);
        return extra != null ? extra : condition.configString(key);
    }

    public Long configLong(String key) {
        if (condition == null) {
            return null;
        }
        Long extra = condition.extraLong(key);
        return extra != null ? extra : condition.configLong(key);
    }

    public Double configDouble(String key) {
        if (condition == null) {
            return null;
        }
        Double extra = condition.extraDouble(key);
        return extra != null ? extra : condition.configDouble(key);
    }

    public Boolean configBoolean(String key) {
        if (condition == null) {
            return null;
        }
        Boolean extra = condition.extraBoolean(key);
        return extra != null ? extra : condition.configBoolean(key);
    }
}
