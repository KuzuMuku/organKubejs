package cn.kuzuanpa.organkubejs.kubejs.event;

import cn.kuzuanpa.organeffectprocessor.api.EffectDefinition;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class PointActionEventJS extends EventJS {
    private ServerPlayer player;
    private String callback;
    private long availablePoints;
    private EffectDefinition.BonusAction action;
    private long consumePoints;

    public PointActionEventJS() {
    }

    public PointActionEventJS(ServerPlayer player, String callback, long availablePoints, EffectDefinition.BonusAction action) {
        this.player = player;
        this.callback = callback;
        this.availablePoints = availablePoints;
        this.action = action;
        this.consumePoints = 0L;
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

    public long getAvailablePoints() {
        return availablePoints;
    }

    public String getPointType() {
        return action != null ? action.pointType() : null;
    }

    public String getPointId() {
        return action != null ? action.pointId() : null;
    }

    public String getPointKey() {
        return action != null ? OrganKubejsApi.pointKey(action.pointType(), action.pointId()) : "";
    }

    public long getMaxConsume() {
        return action != null ? action.maxConsume() : 0L;
    }

    public boolean isPointsConsume() {
        return action != null && action.isPointsConsume();
    }

    public long getConsumePoints() {
        return consumePoints;
    }

    public void setConsumePoints(long consume) {
        this.consumePoints = Math.max(0L, consume);
    }

    public String configString(String key) {
        if (action == null) {
            return null;
        }
        String extra = action.extraString(key);
        return extra != null ? extra : action.configString(key);
    }

    public Long configLong(String key) {
        if (action == null) {
            return null;
        }
        Long extra = action.extraLong(key);
        return extra != null ? extra : action.configLong(key);
    }

    public Double configDouble(String key) {
        if (action == null) {
            return null;
        }
        Double extra = action.extraDouble(key);
        return extra != null ? extra : action.configDouble(key);
    }

    public Boolean configBoolean(String key) {
        if (action == null) {
            return null;
        }
        Boolean extra = action.extraBoolean(key);
        return extra != null ? extra : action.configBoolean(key);
    }
}
