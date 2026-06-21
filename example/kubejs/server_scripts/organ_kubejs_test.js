const SpellRegistry = Java.loadClass('io.redspace.ironsspellbooks.api.registry.SpellRegistry')
const CastSource = Java.loadClass('io.redspace.ironsspellbooks.api.spells.CastSource')
const ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation')

BlockEvents.broken(event => {
  if (!event.player) {
    return
  }

  OrganKubeJS.addRuntimePoint(event.player, 'runtime:kubejs:break_charge', 1, 20 * 60)
})

OrganKubejsEvents.pointAction('break_charge_logger', event => {
  if (!event.player || event.availablePoints <= 0) {
    return
  }

  console.info(`[OrganKubeJS Test] ${event.player.username} break_charge available=${event.availablePoints}, consuming 1`)
  event.setConsumePoints(1)
})

OrganKubejsEvents.skillCast('golem_arc_burst_cast', event => {
  if (!event.player) {
    return
  }

  const player = event.player.minecraftEntity ?? event.player
  const spellId = ResourceLocation.fromNamespaceAndPath('irons_spellbooks', 'magic_missile')
  const spell = SpellRegistry['getSpell(net.minecraft.resources.ResourceLocation)'](spellId)

  spell.attemptInitiateCast(Item.of('air'), event.level, player.level, player, CastSource.NONE, true, 'main_hand')

  if (!spell) {
    console.warn('[OrganKubeJS Test] irons_spellbooks:magic_missile was not found')
    return
  }

  spell.castSpell(player.level, event.level, player, CastSource.SPELLBOOK, true)
  console.info(`[OrganKubeJS Test] ${event.player.username} cast irons_spellbooks:magic_missile`)
})
