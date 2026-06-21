package cn.kuzuanpa.organkubejs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class SkillCastEventJS extends EventJS {
    private ServerPlayer player;
    private String skillId;
    private String callback;
    private int level;

    public SkillCastEventJS() {
    }

    public SkillCastEventJS(ServerPlayer player, String skillId, String callback, int level) {
        this.player = player;
        this.skillId = skillId;
        this.callback = callback;
        this.level = level;
    }

    public MinecraftServer getServer() {
        return player != null ? player.server : null;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public String getSkillId() {
        return skillId;
    }

    public String getCallback() {
        return callback;
    }

    public int getLevel() {
        return level;
    }
}
