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

package cn.kuzuanpa.organkubejs.compat.condition;

import cn.kuzuanpa.organapi.api.query.OrganPosition;
import cn.kuzuanpa.organeffects.api.EffectDefinition;
import cn.kuzuanpa.organeffects.api.extension.ConditionHandler;
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
                condition.configString("callback"),
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
