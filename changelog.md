Template
---
*.*.*

New Features
- ...

Fixes
- ...

Removed Features
- ...

---

2.1.0
New Features
- Commands
- Added menu editor with alot of feature inside it
- Add support for plugin CraftEngine
- New changelog file (agil: i think this will need more effort to make lol)
- Add Casting skill from hand
- Add Placeholder(Still in development, some works but some mybe not).
- Add Support for AuraSkills.

Changes:
- Change max cooldown value location to mythicmobs, then when you not set cooldown in ItemCaster but in MythicMobs skill config will use the config instead.
- Change config system to normal bukkit.

Changes for Developer
- Change code for other plugin support.
- Conditions & Mechanic register reflection change to using mythicmobs api instead and registered static(i not make it flexible).
- New Parser for parsing List/Map data.

Fixes
- Fix cooldown skill in item is override
- Fix skill that cast form ItemCaster, doest have damage

Removed Features
- remove recipe class beacuse i think you can just use plugin like Nexo/CraftEngine/ItemsAdder/Oraxe for making crafting recipe
