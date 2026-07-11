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

package cn.kuzuanpa.organkubejs.compat.execution;

import cn.kuzuanpa.organeffects.api.EffectDefinition;
import cn.kuzuanpa.organeffects.api.extension.PointExecutor;
import cn.kuzuanpa.organeffects.common.effect.RuntimePointExecutor;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public final class KubejsNearbyEntitiesExecutor implements PointExecutor {
    @Override
    public String type() {
        return "kubejs_nearby_entities";
    }

    @Override
    public void execute(PointExecutionContext context, EffectDefinition.BonusAction action) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        String callback = firstNonBlank(
                action.extraString("callback"),
                action.configString("callback"),
                action.extraString("id"),
                action.configString("id")
        );
        if (callback == null) {
            return;
        }

        PointUsage preview = context.resolveUsage(action);
        if (preview.usedPoints() <= 0L) {
            return;
        }

        double radius = action.configDouble("radius") != null ? action.configDouble("radius") : 2.2D;
        List<LivingEntity> targets = findNearbyLivingTargets(player, radius);
        if (targets.isEmpty()) {
            return;
        }

        long requestedUsage = 0L;
        for (LivingEntity target : targets) {
            requestedUsage = Math.max(requestedUsage, OrganKubejsApi.invokeNearbyEntityAction(player, target, callback, preview.usedPoints(), action));
        }
        long clampedUsage = Math.max(0L, Math.min(preview.usedPoints(), requestedUsage));

        if (clampedUsage <= 0L || !action.isPointsConsume()) {
            return;
        }

        EffectDefinition.BonusAction adjustedAction = withMaxConsume(action, clampedUsage);
        RuntimePointExecutor.consumePointUsage(player, context.holder(), adjustedAction);
    }

    private static List<LivingEntity> findNearbyLivingTargets(Player player, double radius) {
        AABB area = player.getBoundingBox().inflate(radius);
        double radiusSq = radius * radius;
        return player.level().getEntitiesOfClass(LivingEntity.class, area, entity -> entity != player && entity.isAlive() && !(entity instanceof Player) && entity.distanceToSqr(player) <= radiusSq);
    }

    private static EffectDefinition.BonusAction withMaxConsume(EffectDefinition.BonusAction action, long maxConsume) {
        return new EffectDefinition.BonusAction(
                action.type(),
                action.amount(),
                action.config(),
                action.pointType(),
                action.pointId(),
                action.source(),
                maxConsume,
                action.isPointsConsume(),
                action.hidden(),
                action.customDisplayKey(),
                action.effectId(),
                action.durationTicks(),
                action.amplifier(),
                action.target(),
                action.items(),
                action.rolls(),
                action.unique(),
                action.dropIfFull(),
                action.chance(),
                action.extra()
        );
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
