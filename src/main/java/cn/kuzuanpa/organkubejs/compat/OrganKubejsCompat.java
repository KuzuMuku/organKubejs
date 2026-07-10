package cn.kuzuanpa.organkubejs.compat;

import cn.kuzuanpa.organeffects.api.extension.OrganEffectsExtensionApi;
import cn.kuzuanpa.organkubejs.compat.condition.KubejsPredicateCondition;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsCallbackExecutor;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsNearbyEntitiesExecutor;

public final class OrganKubejsCompat {
    private OrganKubejsCompat() {
    }

    public static void register() {
        OrganEffectsExtensionApi.registerConditionHandler("kubejs_predicate", new KubejsPredicateCondition());
        OrganEffectsExtensionApi.registerPointExecutor(new KubejsCallbackExecutor());
        OrganEffectsExtensionApi.registerPointExecutor(new KubejsNearbyEntitiesExecutor());
    }
}
