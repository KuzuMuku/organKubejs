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
import cn.kuzuanpa.organeffects.api.extension.TargetPointExecutor;
import cn.kuzuanpa.organeffects.common.effect.RuntimePointExecutor;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import net.minecraft.server.level.ServerPlayer;

public final class KubejsTargetCallbackExecutor implements TargetPointExecutor {
    @Override
    public String type() {
        return "kubejs_target_call";
    }

    @Override
    public void execute(TargetExecutionContext context, EffectDefinition.BonusAction action) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        String callback = firstNonBlank(
                action.extraString("callback"),
                action.configString("callback"),
                action.extraString("id"),
                action.configString("id")
        );
        if (callback == null || context.target() == null || !context.target().isAlive()) {
            return;
        }

        cn.kuzuanpa.organeffects.api.extension.PointExecutor.PointUsage preview = context.resolveUsage(action);
        if (preview.usedPoints() <= 0L) {
            return;
        }

        long requestedUsage = OrganKubejsApi.invokeTargetAction(player, context.target(), callback, preview.usedPoints(), action, context.event());
        long clampedUsage = Math.max(0L, Math.min(preview.usedPoints(), requestedUsage));

        if (clampedUsage <= 0L || !action.isPointsConsume()) {
            return;
        }

        EffectDefinition.BonusAction adjustedAction = withMaxConsume(action, clampedUsage);
        RuntimePointExecutor.consumePointUsage(player, context.holder(), adjustedAction);
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
