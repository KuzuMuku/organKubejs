package cn.kuzuanpa.organkubejs.compat;

import cn.kuzuanpa.organeffectprocessor.api.extension.OepExtensionApi;
import cn.kuzuanpa.organkubejs.compat.condition.KubejsPredicateCondition;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsCallbackExecutor;

public final class OrganKubejsCompat {
    private OrganKubejsCompat() {
    }

    public static void register() {
        OepExtensionApi.registerConditionHandler("kubejs_predicate", new KubejsPredicateCondition());
        OepExtensionApi.registerPointExecutor(new KubejsCallbackExecutor());
    }
}
