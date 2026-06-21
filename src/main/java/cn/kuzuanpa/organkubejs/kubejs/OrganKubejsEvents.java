package cn.kuzuanpa.organkubejs.kubejs;

import cn.kuzuanpa.organkubejs.kubejs.event.PointActionEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.PredicateEventJS;
import cn.kuzuanpa.organkubejs.kubejs.event.SkillCastEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.event.Extra;
import dev.latvian.mods.kubejs.script.ScriptType;

public final class OrganKubejsEvents {
    public static final EventGroup GROUP = EventGroup.of("OrganKubejsEvents");
    public static final EventHandler PREDICATE = GROUP.server("predicate", () -> PredicateEventJS.class).extra(Extra.STRING).hasResult();
    public static final EventHandler POINT_ACTION = GROUP.server("pointAction", () -> PointActionEventJS.class).extra(Extra.STRING).hasResult();
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
