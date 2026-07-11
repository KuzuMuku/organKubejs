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

package cn.kuzuanpa.organkubejs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class SkillCastEventJS extends EventJS {
    private ServerPlayer player;
    private String skillId;
    private String callback;
    private int level;

    public SkillCastEventJS() {
    }

    public SkillCastEventJS(ServerPlayer player, String skillId, String callback, int level) {
        this.player = player;
        this.skillId = skillId;
        this.callback = callback;
        this.level = level;
    }

    public MinecraftServer getServer() {
        return player != null ? player.server : null;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public String getSkillId() {
        return skillId;
    }

    public String getCallback() {
        return callback;
    }

    public int getLevel() {
        return level;
    }
}
