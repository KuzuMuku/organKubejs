package cn.kuzuanpa.organkubejs.kubejs;

import cn.kuzuanpa.organkubejs.api.OrganKubejsApi;
import net.minecraft.world.entity.LivingEntity;

public final class OrganKubejsBindings {
    public String pointKey(String pointType, String pointId) {
        return OrganKubejsApi.pointKey(pointType, pointId);
    }

    public long getPoint(LivingEntity entity, String pointKey) {
        return OrganKubejsApi.getPoint(entity, pointKey);
    }

    public long getTypedPoint(LivingEntity entity, String pointType, String pointId) {
        return OrganKubejsApi.getPoint(entity, pointType, pointId);
    }

    public long addSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OrganKubejsApi.addSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public long addTypedSourcePoint(LivingEntity entity, String sourceTag, String pointType, String pointId, long amount) {
        return OrganKubejsApi.addSourcePoint(entity, sourceTag, pointType, pointId, amount);
    }

    public long setSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long value) {
        return OrganKubejsApi.setSourcePoint(entity, sourceTag, pointKey, value);
    }

    public long consumeSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OrganKubejsApi.consumeSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public long clearSourcePoint(LivingEntity entity, String sourceTag, String pointKey) {
        return OrganKubejsApi.clearSourcePoint(entity, sourceTag, pointKey);
    }

    public long addRuntimePoint(LivingEntity entity, String pointKey, long amount, long durationTicks) {
        return OrganKubejsApi.addRuntimePoint(entity, pointKey, amount, durationTicks);
    }

    public long addTypedRuntimePoint(LivingEntity entity, String pointType, String pointId, long amount, long durationTicks) {
        return OrganKubejsApi.addRuntimePoint(entity, pointType, pointId, amount, durationTicks);
    }

    public long setRuntimePoint(LivingEntity entity, String pointKey, long value, long durationTicks) {
        return OrganKubejsApi.setRuntimePoint(entity, pointKey, value, durationTicks);
    }

    public long consumeRuntimePoint(LivingEntity entity, String pointKey, long amount) {
        return OrganKubejsApi.consumeRuntimePoint(entity, pointKey, amount);
    }

    public long clearRuntimePoint(LivingEntity entity, String pointKey) {
        return OrganKubejsApi.clearRuntimePoint(entity, pointKey);
    }

    public void recompute(LivingEntity entity) {
        OrganKubejsApi.recompute(entity);
    }

    public void refresh(LivingEntity entity) {
        OrganKubejsApi.refresh(entity);
    }
}
