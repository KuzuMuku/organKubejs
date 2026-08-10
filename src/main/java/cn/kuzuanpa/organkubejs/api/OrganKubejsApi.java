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

package cn.kuzuanpa.organkubejs.api;

import cn.kuzuanpa.organapi.api.query.OrganPosition;
import cn.kuzuanpa.organeffects.api.EffectDefinition;
import cn.kuzuanpa.organeffects.api.OrganEffectsPointApi;
import cn.kuzuanpa.organeffects.api.extension.SkillExecutor;
import cn.kuzuanpa.organeffects.common.skill.SkillDefinition;
import cn.kuzuanpa.organeffects.common.skill.SkillManager;
import cn.kuzuanpa.organkubejs.kubejs.OrganKubejsEvents;
import cn.kuzuanpa.organkubejs.kubejs.event.NearbyEntityActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PointActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PredicateEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.SkillCastEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.TargetActionEventJS;
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
        return OrganEffectsPointApi.getPoint(entity, pointKey);
    }

    public static long getPoint(LivingEntity entity, String pointType, String pointId) {
        return getPoint(entity, pointKey(pointType, pointId));
    }

    public static java.util.Map<String, Long> getPoints(LivingEntity entity) {
        return OrganEffectsPointApi.getPoints(entity);
    }

    public static long addSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OrganEffectsPointApi.addSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public static long addSourcePoint(LivingEntity entity, String sourceTag, String pointType, String pointId, long amount) {
        return addSourcePoint(entity, sourceTag, pointKey(pointType, pointId), amount);
    }

    public static long addRuntimePoint(LivingEntity entity, String pointKey, long amount, long durationTicks) {
        return OrganEffectsPointApi.addRuntimePoint(entity, pointKey, amount, durationTicks);
    }

    public static long addRuntimePoint(LivingEntity entity, String pointType, String pointId, long amount, long durationTicks) {
        return addRuntimePoint(entity, pointKey(pointType, pointId), amount, durationTicks);
    }

    public static long setSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long value) {
        return OrganEffectsPointApi.setSourcePoint(entity, sourceTag, pointKey, value);
    }

    public static long consumeSourcePoint(LivingEntity entity, String sourceTag, String pointKey, long amount) {
        return OrganEffectsPointApi.consumeSourcePoint(entity, sourceTag, pointKey, amount);
    }

    public static long clearSourcePoint(LivingEntity entity, String sourceTag, String pointKey) {
        return OrganEffectsPointApi.clearSourcePoint(entity, sourceTag, pointKey);
    }

    public static long setRuntimePoint(LivingEntity entity, String pointKey, long value, long durationTicks) {
        return OrganEffectsPointApi.setRuntimePoint(entity, pointKey, value, durationTicks);
    }

    public static long consumeRuntimePoint(LivingEntity entity, String pointKey, long amount) {
        return OrganEffectsPointApi.consumeRuntimePoint(entity, pointKey, amount);
    }

    public static long clearRuntimePoint(LivingEntity entity, String pointKey) {
        return OrganEffectsPointApi.clearRuntimePoint(entity, pointKey);
    }

    public static void recompute(LivingEntity entity) {
        OrganEffectsPointApi.recompute(entity);
    }

    public static void refresh(LivingEntity entity) {
        OrganEffectsPointApi.refresh(entity);
    }

    public static void registerSkill(String skillId, String nameKey, String descriptionKey, int maxLevel, String callback) {
        registerSkill(skillId, nameKey, descriptionKey, 0, maxLevel, callback);
    }

    public static void registerSkill(String skillId, String nameKey, String descriptionKey, int cooldownTicks, int maxLevel, String callback) {
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
                Math.max(0, cooldownTicks),
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

    public static long invokeNearbyEntityAction(ServerPlayer player, LivingEntity target, String callback, long availablePoints, EffectDefinition.BonusAction action) {
        return OrganKubejsEvents.postNearbyEntityAction(callback, new NearbyEntityActionEventJS(player, target, callback, availablePoints, action));
    }

    public static long invokeTargetAction(ServerPlayer player, LivingEntity target, String callback, long availablePoints,
                                          EffectDefinition.BonusAction action, cn.kuzuanpa.organeffects.api.extension.OrganEffectsRuntimeEvent runtimeEvent) {
        return OrganKubejsEvents.postTargetAction(callback, new TargetActionEventJS(player, target, callback, availablePoints, action, runtimeEvent));
    }

    private static String normalizeSkillId(String skillId) {
        ResourceLocation parsed = ResourceLocation.tryParse(skillId);
        return parsed != null ? parsed.toString() : (skillId == null ? "" : skillId);
    }
}
