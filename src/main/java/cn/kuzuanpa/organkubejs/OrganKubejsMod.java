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

package cn.kuzuanpa.organkubejs;

import cn.kuzuanpa.organkubejs.compat.OrganKubejsCompat;
import net.minecraftforge.fml.common.Mod;

@Mod(OrganKubejsMod.MOD_ID)
public class OrganKubejsMod {
    public static final String MOD_ID = "organkubejs";

    public OrganKubejsMod() {
        OrganKubejsCompat.register();
    }
}
