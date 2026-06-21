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
