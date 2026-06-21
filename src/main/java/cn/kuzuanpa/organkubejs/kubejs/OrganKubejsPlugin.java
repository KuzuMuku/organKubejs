package cn.kuzuanpa.organkubejs.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public final class OrganKubejsPlugin extends KubeJSPlugin {
    private static final OrganKubejsBindings BINDINGS = new OrganKubejsBindings();

    @Override
    public void registerEvents() {
        OrganKubejsEvents.GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("OrganKubeJS", BINDINGS);
    }
}
