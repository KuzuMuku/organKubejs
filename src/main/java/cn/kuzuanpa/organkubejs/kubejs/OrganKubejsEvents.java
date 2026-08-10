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

package cn.kuzuanpa.organkubejs.kubejs;

import cn.kuzuanpa.organkubejs.kubejs.event.NearbyEntityActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PointActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PredicateEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.SkillCastEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.TargetActionEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.event.Extra;
import dev.latvian.mods.kubejs.script.ScriptType;

public final class OrganKubejsEvents {
    public static final EventGroup GROUP = EventGroup.of("OrganKubejsEvents");
    public static final EventHandler PREDICATE = GROUP.server("predicate", () -> PredicateEventJS.class).extra(Extra.STRING).hasResult();
    public static final EventHandler POINT_ACTION = GROUP.server("pointAction", () -> PointActionEventJS.class).extra(Extra.STRING).hasResult();
    public static final EventHandler NEARBY_ENTITY_ACTION = GROUP.server("nearbyEntityAction", () -> NearbyEntityActionEventJS.class).extra(Extra.STRING).hasResult();
    public static final EventHandler TARGET_ACTION = GROUP.server("targetAction", () -> TargetActionEventJS.class).extra(Extra.STRING).hasResult();
    public static final EventHandler SKILL_CAST = GROUP.server("skillCast", () -> SkillCastEventJS.class).extra(Extra.STRING).hasResult();

    private OrganKubejsEvents() {
    }

    public static boolean postPredicate(String callback, PredicateEventJS event) {
        if (callback == null || callback.isBlank() || !PREDICATE.hasListeners(callback)) {
            return false;
        }

        EventResult result = PREDICATE.post(ScriptType.SERVER, callback, event);
        Object value = result.value();
        if (value instanceof Boolean bool) {
            return bool;
        }
        if (result.interruptTrue()) {
            return true;
        }
        if (result.interruptFalse()) {
            return false;
        }
        return false;
    }

    public static long postPointAction(String callback, PointActionEventJS event) {
        if (callback == null || callback.isBlank() || !POINT_ACTION.hasListeners(callback)) {
            return 0L;
        }

        EventResult result = POINT_ACTION.post(ScriptType.SERVER, callback, event);
        Object value = result.value();
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof Boolean bool) {
            return bool ? 1L : 0L;
        }
        return event.getConsumePoints();
    }

    public static long postNearbyEntityAction(String callback, NearbyEntityActionEventJS event) {
        if (callback == null || callback.isBlank() || !NEARBY_ENTITY_ACTION.hasListeners(callback)) {
            return 0L;
        }

        EventResult result = NEARBY_ENTITY_ACTION.post(ScriptType.SERVER, callback, event);
        Object value = result.value();
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof Boolean bool) {
            return bool ? 1L : 0L;
        }
        return event.getConsumePoints();
    }

    public static long postTargetAction(String callback, TargetActionEventJS event) {
        if (callback == null || callback.isBlank() || !TARGET_ACTION.hasListeners(callback)) {
            return 0L;
        }

        EventResult result = TARGET_ACTION.post(ScriptType.SERVER, callback, event);
        Object value = result.value();
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof Boolean bool) {
            return bool ? 1L : 0L;
        }
        return event.getConsumePoints();
    }

    public static boolean postSkillCast(String callback, SkillCastEventJS event) {
        if (callback == null || callback.isBlank() || !SKILL_CAST.hasListeners(callback)) {
            return false;
        }

        EventResult result = SKILL_CAST.post(ScriptType.SERVER, callback, event);
        Object value = result.value();
        if (value instanceof Boolean bool) {
            return bool;
        }
        if (result.interruptTrue()) {
            return true;
        }
        if (result.interruptFalse()) {
            return false;
        }
        return false;
    }
}
