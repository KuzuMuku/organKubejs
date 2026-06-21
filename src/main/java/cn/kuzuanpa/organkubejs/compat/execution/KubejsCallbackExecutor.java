package cn.kuzuanpa.organkubejs.compat.execution;

import cn.kuzuanpa.organeffectprocessor.api.EffectDefinition;
import cn.kuzuanpa.organeffectprocessor.api.extension.PointExecutor;
import cn.kuzuanpa.organeffectprocessor.common.effect.RuntimePointExecutor;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public final class KubejsCallbackExecutor implements PointExecutor {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public String type() {
        return "kubejs_call";
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

        long requestedUsage = OrganKubejsApi.invokePointAction(player, callback, preview.usedPoints(), action);
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
