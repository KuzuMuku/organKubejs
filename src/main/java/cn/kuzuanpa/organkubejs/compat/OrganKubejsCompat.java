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

package cn.kuzuanpa.organkubejs.compat;

import cn.kuzuanpa.organeffects.api.extension.OrganEffectsExtensionApi;
import cn.kuzuanpa.organkubejs.compat.condition.KubejsPredicateCondition;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsCallbackExecutor;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsNearbyEntitiesExecutor;
import cn.kuzuanpa.organkubejs.compat.execution.KubejsTargetCallbackExecutor;

public final class OrganKubejsCompat {
    private OrganKubejsCompat() {
    }

    public static void register() {
        OrganEffectsExtensionApi.registerConditionHandler("kubejs_predicate", new KubejsPredicateCondition());
        OrganEffectsExtensionApi.registerPointExecutor(new KubejsCallbackExecutor());
        OrganEffectsExtensionApi.registerPointExecutor(new KubejsNearbyEntitiesExecutor());
        OrganEffectsExtensionApi.registerTargetPointExecutor(new KubejsTargetCallbackExecutor());
    }
}
