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
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class NearbyEntityActionEventJS extends EventJS {
    private ServerPlayer player;
    private LivingEntity target;
    private String callback;
    private long availablePoints;
    private EffectDefinition.BonusAction action;
    private long consumePoints;

    public NearbyEntityActionEventJS() {
    }

    public NearbyEntityActionEventJS(ServerPlayer player, LivingEntity target, String callback, long availablePoints, EffectDefinition.BonusAction action) {
        this.player = player;
        this.target = target;
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
