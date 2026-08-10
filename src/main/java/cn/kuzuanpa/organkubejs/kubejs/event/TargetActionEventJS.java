/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the organKubejs Mod. Get the Source Code in github:
 * https://github.com/KuzuMuku/organKubejs
 *
 * organKubejs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * organKubejs is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.organkubejs.kubejs.event;

import cn.kuzuanpa.organeffects.api.EffectDefinition;
import cn.kuzuanpa.organeffects.api.extension.OrganEffectsRuntimeEvent;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TargetActionEventJS extends EventJS {
    private ServerPlayer player;
    private LivingEntity target;
    private String callback;
    private long availablePoints;
    private EffectDefinition.BonusAction action;
    private OrganEffectsRuntimeEvent runtimeEvent;
    private long consumePoints;

    public TargetActionEventJS() {
    }

    public TargetActionEventJS(ServerPlayer player, LivingEntity target, String callback, long availablePoints,
                               EffectDefinition.BonusAction action, OrganEffectsRuntimeEvent runtimeEvent) {
        this.player = player;
        this.target = target;
        this.callback = callback;
        this.availablePoints = availablePoints;
        this.action = action;
        this.runtimeEvent = runtimeEvent;
        this.consumePoints = 0L;
    }

    public MinecraftServer getServer() {
        return player != null ? player.server : null;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public LivingEntity getTarget() {
        return target;
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

    public String getEffectId() {
        return action != null ? action.effectId() : null;
    }

    public int getDurationTicks() {
        return action != null && action.durationTicks() != null ? action.durationTicks() : 40;
    }

    public int getAmplifier() {
        return action != null && action.amplifier() != null ? action.amplifier() : 0;
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

    public String getEventType() {
        return runtimeEvent != null ? runtimeEvent.type() : "";
    }

    public double getEventAmount() {
        return runtimeEvent != null ? runtimeEvent.amount() : 0.0D;
    }

    public boolean isProjectileAttack() {
        return runtimeEvent != null && runtimeEvent.isProjectileAttack();
    }

    public Entity getDirectEntity() {
        return runtimeEvent != null ? runtimeEvent.directEntity() : null;
    }

    public ItemStack getItemStack() {
        return runtimeEvent != null ? runtimeEvent.itemStack() : ItemStack.EMPTY;
    }

    public BlockState getBlockState() {
        return runtimeEvent != null ? runtimeEvent.blockState() : null;
    }

    public String eventExtraString(String key) {
        return runtimeEvent != null ? runtimeEvent.extraString(key) : null;
    }

    public Long eventExtraLong(String key) {
        return runtimeEvent != null ? runtimeEvent.extraLong(key) : null;
    }

    public Double eventExtraDouble(String key) {
        return runtimeEvent != null ? runtimeEvent.extraDouble(key) : null;
    }

    public Boolean eventExtraBoolean(String key) {
        return runtimeEvent != null ? runtimeEvent.extraBoolean(key) : null;
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
