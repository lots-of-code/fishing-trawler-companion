# Fishing Trawler Companion

A RuneLite plugin that adds quality-of-life overlays to the Fishing Trawler minigame.

## Features

- **Leak highlights** — outlines small leaks on the hull so you can find and bail/patch them quickly.
- **Hole highlights** — outlines large hull holes (the kind that need a plank to plug) in a separate, attention-grabbing color.
- **Trip HUD** — a small panel showing elapsed trip time and a live count of active leaks and holes.

The plugin is purely informational: it reads in-game object state and renders overlays. It does not click for you, does not predict mechanics, and does not modify any input.

## Building

```
./gradlew shadowJar
```

## Running locally (dev client)

```
./gradlew run
```

This launches a RuneLite development client with the plugin sideloaded. For login instructions see [Using Jagex Accounts](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

## Plugin Hub submission

This plugin is built against the rules at [runelite/plugin-hub](https://github.com/runelite/plugin-hub) and the conduct guidelines in [AGENTS.md](AGENTS.md). Fishing Trawler is a non-combat skilling minigame, so it is not subject to the boss/combat restrictions that prohibit attack-style indicators and prayer-switch helpers.

## License

BSD 2-Clause
