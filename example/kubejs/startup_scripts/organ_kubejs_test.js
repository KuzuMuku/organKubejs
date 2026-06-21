StartupEvents.registry('item', event => {
  event.create('golem_arc_core')
    .displayName('Golem Arc Core')
    .maxStackSize(1)
})

OrganKubeJS.registerSkill(
  'kubejs:golem_arc_burst',
  'point.organeffectprocessor.skill.kubejs.golem_arc_burst',
  'point.organeffectprocessor.skill.kubejs.golem_arc_burst.desc',
  1,
  'golem_arc_burst_cast'
)
