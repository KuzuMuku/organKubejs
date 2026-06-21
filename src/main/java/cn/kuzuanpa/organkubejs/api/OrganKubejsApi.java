package cn.kuzuanpa.organkubejs.api;

import cn.kuzuanpa.organapi.api.query.OrganPosition;
import cn.kuzuanpa.organeffectprocessor.api.EffectDefinition;
import cn.kuzuanpa.organeffectprocessor.api.OepPointApi;
import cn.kuzuanpa.organeffectprocessor.api.extension.SkillExecutor;
import cn.kuzuanpa.organeffectprocessor.common.skill.SkillDefinition;
import cn.kuzuanpa.organeffectprocessor.common.skill.SkillManager;
import cn.kuzuanpa.organkubejs.kubejs.OrganKubejsEvents;
import cn.kuzuanpa.organkubejs.kubejs.event.PointActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PredicateEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.SkillCastEventJS;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class OrganKubejsApi {
    private OrganKubejsApi() {
    }

    public static String pointKey(String pointType, String pointId) {
        if (pointType == null || pointType.isBlank() || pointId == null || pointId.isBlank()) {
            return "";
        }
        return pointType + ":" + pointId;
    }

    public static long getPoint(LivingEntity entity, String pointKey) {
        return OepPointApi.getPoint(entity, pointKey);
    }

    public static long getPoint(LivingEntity entity, String pointType, String pointId) {
        return getPoint(entity, pointKey(pointType, pointId));
    }

    public static long addSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OepPointApi.addSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public static long addSourcePoint(LivingEntity entity, String sourceTag, String pointType, String pointId, long amount) {
        return addSourcePoint(entity, sourceTag, pointKey(pointType, pointId), amount);
    }

    public static long addRuntimePoint(LivingEntity entity, String pointKey, long amount, long durationTicks) {
        return OepPointApi.addRuntimePoint(entity, pointKey, amount, durationTicks);
    }

    public static long addRuntimePoint(LivingEntity entity, String pointType, String pointId, long amount, long durationTicks) {
        return addRuntimePoint(entity, pointKey(pointType, pointId), amount, durationTicks);
    }

    public static long setSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long value) {
        return OepPointApi.setSourcePoint(entity, sourceTag, pointKey, value);
    }

    public static long consumeSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OepPointApi.consumeSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public static long clearSourcePoint(LivingEntity entity, String sourceTag, String pointKey) {
        return OepPointApi.clearSourcePoint(entity, sourceTag, pointKey);
    }

    public static long setRuntimePoint(LivingEntity entity, String pointKey, long value, long durationTicks) {
        return OepPointApi.setRuntimePoint(entity, pointKey, value, durationTicks);
    }

    public static long consumeRuntimePoint(LivingEntity entity, String pointKey, long amount) {
        return OepPointApi.consumeRuntimePoint(entity, pointKey, amount);
    }

    public static long clearRuntimePoint(LivingEntity entity, String pointKey) {
        return OepPointApi.clearRuntimePoint(entity, pointKey);
    }

    public static void recompute(LivingEntity entity) {
        OepPointApi.recompute(entity);
    }

    public static void refresh(LivingEntity entity) {
        OepPointApi.refresh(entity);
    }

    public static void registerSkill(String skillId, String nameKey, String descriptionKey, int maxLevel, String callback) {
        String normalizedSkillId = normalizeSkillId(skillId);
        if (normalizedSkillId.isBlank()) {
            return;
        }
        String resolvedNameKey = nameKey == null || nameKey.isBlank() ? normalizedSkillId : nameKey;
        String resolvedDescriptionKey = descriptionKey == null || descriptionKey.isBlank() ? normalizedSkillId + ".desc" : descriptionKey;
        String resolvedCallback = callback == null || callback.isBlank() ? normalizedSkillId : callback;
        SkillManager.registerSkill(new SkillDefinition(
                normalizedSkillId,
                resolvedNameKey,
                resolvedDescriptionKey,
                List.of(),
                Math.max(1, maxLevel)
        ));
        SkillManager.registerSkillExecutor(normalizedSkillId, new SkillExecutor() {
            @Override
            public boolean cast(Player player, int level) {
                return player instanceof ServerPlayer serverPlayer
                        && OrganKubejsEvents.postSkillCast(resolvedCallback, new SkillCastEventJS(serverPlayer, normalizedSkillId, resolvedCallback, level));
            }
        });
    }

    public static boolean evaluatePredicate(ServerPlayer player, String callback, ResourceLocation organId, OrganPosition position, EffectDefinition.Condition condition) {
        return OrganKubejsEvents.postPredicate(callback, new PredicateEventJS(player, callback, organId, position, condition));
    }

    public static long invokePointAction(ServerPlayer player, String callback, long availablePoints, EffectDefinition.BonusAction action) {
        return OrganKubejsEvents.postPointAction(callback, new PointActionEventJS(player, callback, availablePoints, action));
    }

    private static String normalizeSkillId(String skillId) {
        ResourceLocation parsed = ResourceLocation.tryParse(skillId);
        return parsed != null ? parsed.toString() : (skillId == null ? "" : skillId);
    }
}
