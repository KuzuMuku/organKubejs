# Organ KubeJS Extension

OrganEffects的KubeJS兼容层.

目前有三个可用的兼容器:

- `kubejs_predicate` 使用一个返回boolean的kubejs方法作为条件
- `kubejs_call` 获得点数时调用返回long的kubejs方法，返回数值为消耗的点数
- `OrganKubeJS` 修改玩家点数的js类

## 示例
1. 注册物品  kubejs/startup_scripts/OrganEffects_items.js

StartupEvents.registry('item', event => {
  event.create('storm_organ')
    .displayName('Storm Organ')
    .maxStackSize(1)
})

2. 让它能放进器官槽  kubejs/server_scripts/OrganEffects_tags.js

ServerEvents.tags('item', event => {
  event.add('organapi:organs', 'kubejs:storm_organ')
})

3. 器官定义  kubejs/data/kubejs/organapi/organs/storm_organ.json

{
  "item": "kubejs:storm_organ",
  "valid_parts": ["organapi:chest"],
  "size": 1,
  "tooltips": ["A script-driven organ."],
  "tags": ["kubejs", "storm"],
  "effects": [
    {
      "conditions": [
        {
          "type": "kubejs_predicate",
          "callback": "storm_predicate"
        }
      ],
      "grants": [
        {
          "type": "counter",
          "id": "kubejs:storm_charge",
          "amount": 3
        }
      ],
      "events": [],
      "executions": [
        {
          "type": "kubejs_call",
          "callback": "storm_burst",
          "point_type": "counter",
          "point_id": "kubejs:storm_charge",
          "max_consume": 10,
          "consume_points": true
        }
      ]
    }
  ]
}

4. KubeJS 回调  kubejs/server_scripts/OrganEffects_callbacks.js

OrganKubejsEvents.predicate('storm_predicate', event => {
  const player = event.player
  if (!player) {
    return event.success(false)
  }

  const isRaining = player.level.isRaining()
  const yOk = player.y > 80

  event.success(isRaining && yOk)
})

OrganKubejsEvents.pointAction('storm_burst', event => {
  const player = event.player
  if (!player) {
    return 0
  }

  if (event.availablePoints < 3) {
    return 0
  }

  player.tell(`Storm burst triggered, consumed 3 points`)
  player.minecraftEntity.heal(2.0)

  return 3
})

5. 主动给玩家加点的例子  比如登录时给一个 runtime 点：
PlayerEvents.loggedIn(event => {
  OrganKubeJS.addRuntimePoint(event.player, 'runtime:kubejs:welcome_charge', 1, 200)
})

或者加 source 点：
PlayerEvents.loggedIn(event => {
  OrganKubeJS.addSourcePoint(event.player, 'kubejs_bonus', 'counter:kubejs:storm_charge', 5)
})

这个示例的效果
- 玩家安装 kubejs:storm_organ
- 如果“正在下雨且玩家高度 > 80”，kubejs_predicate 返回 true
- 器官提供 counter:kubejs:storm_charge = 3
- kubejs_call 检测到这个点后调用 storm_burst
- storm_burst 返回 3，OrganEffects 就消费 3 点  - 同时脚本里给玩家回血并发消息
