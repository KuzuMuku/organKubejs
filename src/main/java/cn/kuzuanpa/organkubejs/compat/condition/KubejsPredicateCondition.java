package cn.kuzuanpa.organkubejs.compat.condition;

import cn.kuzuanpa.organapi.api.query.OrganPosition;
import cn.kuzuanpa.organeffectprocessor.api.EffectDefinition;
import cn.kuzuanpa.organeffectprocessor.api.extension.ConditionHandler;
import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class KubejsPredicateCondition implements ConditionHandler {
    @Override
    public boolean test(ConditionContext context, EffectDefinition.Condition condition) {
        if (!(context.evaluationContext().entity() instanceof ServerPlayer player)) {
            return false;
        }

        String callback = firstNonBlank(
                condition.extraString("callback"),
                condition.configString("callback"),
                condition.extraString("id"),
                condition.configString("id")
        );
        if (callback == null) {
            return false;
        }

        OrganPosition position = context.position();
        ResourceLocation organId = context.evaluationContext().organId(position);
        return OrganKubejsApi.evaluatePredicate(player, callback, organId, position, condition);
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
