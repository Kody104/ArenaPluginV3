# Arena Notes and Ideas
*This is an internal markdown file used for communication between us.*

# Current Issues (as of 26 July)
1. In YMLReader, getLocation() throws an IndexArrayOutBoundsException for the list.
2. Phantoms can currently spawn as they please.
3. ChatSystem is too convoluted. A simplification is needed.
4. Not all SpecialItems are cleared through arena -csi. A stronger approach may be required.

# Current Detailed Features in the Arena

## Commands
*key: command <required-args> (optional-args)*

* all <message> : messages all players on the server

* arena -cc  : clears all creatures from the arena.
* arena -csi : clears all special items from the arena.
* arena -fr  : readies all the arena players.
* arena -spawn <quantity> : spawns a set number of zombies after a brief delay.
* arena -round <quantity> : sets (but does not start) the arena round number.
* arena -xp <player> <quantity> : gives the player a set amount of arena-xp.
* arena -lvl <player> <quantity> : sets the level of an arena player.

* chsys role <player> : returns the role of the player. *likely to be removed in future chat update*
* chsys remove <player> : removes a player from the chat system.
* chsys debug <true|false> : toggles if various debug messages should be sent to the dev channel.
* chsys color <true|false> : toggles if color codes are allow in-line. *untested as it may be removed*
* chsys set <player> <role> : set a player's role. *likely to be replaced by set <player> <channel>*

* dev <message> : messages the dev channel. *deprecated* 

* gai <item> <player> : gives an ability item to an arena player.

* gci <item> <player> (quantity) : gives a special item to an arena player.

* join : allows a player to join the arena, if the arena hasn't already started.

* leave: allows a player to leave the arena, if the arena isn't already in progress.

* player <message>  : messages the player channel. *deprecated* 

* quit : allows a player to quit their role, if the arena isn't already in progress and they haven't readied up.

* ready : readies an arena player for the fight.

* role: show the arena player their current role (if any).
* role all: shows the arena player a list of available roles.
* role <role> : assigns an arena player their role.
* role <player> : shows the role of the target player.
* role about <role> : shows the description of a role.

* vip <message> : messages the VIP channel. *deprecated* 

## Experience and Levels
* Player levels range from [1, 18].
* After they are max level, they should not gain any further experience.
* How much experience a player needs to level up is given by the ceiling of the curve: f(x) = 2.5x^3 + 250, where x = the level of the player.
* Experience is given to players directly when they kill a creature. Creatures DO NOT drop vanilla items or vanilla experience.
* How much experience a creature rewards a player is given by the truncation of the curve: g(x) = 15 + 0.03x^2 (+ bonus), where x = level of the creature, and the bonus is an optional amount dropped.
* Players are reward experience at the end of every round they survive.
* How much experience they gain is not yet set. The temporary curve is truncation of the line: h(x) = 1 + x/10, where x = the round.
* When an ArenaPlayer dies, they DO NOT drop any experience they've earned in the Arena.
* When a boss dies, all players are granted experience.

## How to implement a new special item
1. Add the special item as an Enumeration inside of SpecialItems. 
2. The Enumeration value should have the arguments (ID, DISPLAY_NAME).
3. If the item is a usable-item, go to step 4. If not, go to step 7.
4. Create a class for the usable-item and implement UsableItem.
5. The constructor should call the super constructor. Use SpecialItems.NEW_ITEM.getUID() and SpecialItems.NEW_ITEM.getDisplayName() to set the UID and Display name.
6. Implement useItem(Player player) and program what should happen when the item is used.
7. In the SpecialItems Enumeration, find the function getSpecialItem()
8. Add the new special item in as a new case in the switch branch.
9. If the item is unusable, return a new SpecialItem. Otherwise, return the class for the UsableItem.

## Implemented Special Items
1. Boom Stick - when a player drops this item, it explodes (power=1f) after three seconds.
2. Panic Powder - when a player drops this item, it causes all Creatures to randomly target each other.
3. Decoy Boy Troy - when a player drops this item, it spawns a cow (Decoy Boy Troy) who all Creatures in a 15-block radius will attack.
4. Emergency Teleport - **INCOMPLETE** when a player throws this item, it teleports them to a random location inside the arena.
5. Tasty Steak - **INCOMPLETE** when a player consumes this item, for the next 15 seconds, killing a creature will heal them by 1% max health.
6. Test Dummy Stick - Meant to be a testing item for development ideas *may be changed to an awkward potion*
7. Shop chest - When an Arena Player clicks on this item, it will open the arena shop's inventory.

## Money and the Shop
* The baseline currency is Golden Scraps or (GS). *Suggestion: maybe add a symbol?*
* Scraps can be stored as Golden Bars. (1 Golden Bar = 9 Golden Scraps).
* Emeralds are worth 100 Golden Bars. (1 emerald = 900 scraps).
* Diamonds are worth 100 Emeralds. (1 diamond = 90,000 scraps).
* The player can use their scraps, emeralds, and diamonds to buy various items at the shop.
* The value of all shop items have not yet been determined.
* The shop removes diamonds, emeralds, and scraps when a player purchases an item.
* The shop also returns the correct amount of change through diamonds, emeralds, and scraps. 
* ArenaPlayers can earn an undetermined amount of scraps/bars/emeralds.
* Creatures can drop an undetermined amount of scraps/bars/emeralds on an undetermined small chance.
* Bosses are much more likely than regular creatures to drop gold.

## Boos Ideas

### The Skeleton Riders

How the Fight Goes:
* Six powerful Jockies spawn with a Big Magma Cube.
* Damaging the Magma Cube deals damage to the attacker and one random player.
* Magma cubes deal more damage the smaller they are; currently (10%/20%/30%) of max health. 
* Killing the first Jokey spawns (4) big magma cubes.
* Killing the second Jokey spawns (2-5) small magma cubes and splits all big cubes into two small cubes.
* Killing the third Jokey spawns (2-5) small magma cubes.
* Killing the fourth Jokey spawns (2-5) tiny magma cubes and splits all small cubes into tiny cubes.
* Killing the fifth Jokey spawns (2-5) tiny magma cubes.
* Killing the sixth Jokey wins the fight.

Notes:
* Final phase currently has a minimum of 40 tiny magma cubes.

### Petite Pete and Towering Tim

How the Fight Goes:
* Petite Peat (a baby zombie) spawns in the arena.
* After 10 seconds a dangerous sounds players to all players.
* Towering Tim (a giant zombie) spawns in the arena.
* The two zombies share a health bar.
* [Baby Zombie] is highly resistant to magical damange, but weak to physical damage.
* [Giant Zombie] is highly resistant to physical damange, but weak to magic damage.
* [Baby Zombie] can summon other zombies to fight and heal the duo. *Uncertain how healing ability will work yet.*
* [Giant Zombie] can rally the [Baby zombie] and its spawns to increase their damage.
* Killing the duo wins the fight.

### Great Slime and Absymal Cube
* A Slime and Magma Cube spawn.

## Custom Item Ideas
* Emergency Teleport - Teleports a player to a random location in the arena (will blind them for a short time after).
* Decoy Boy Troy - Spawns a cow that will draw aggro from nearby entities.
* Glistening Melon - For ten seconds, killing an enemy will heal the user by 2% max health. (50 enemies to full health)

## Various Ideas

Arena Marked by YAML File
* Create the Arenas where desired and then set their location through a YAML file.

Bonus Loot Rounds:
* Players will have an arena filled with glowstone.
* There will be a number of creatures who can damage these blocks.
* The players must fight to protect the glowstone.
* Any glowstone left after the rounds each glowstone is converted into 3 golden scraps for the players.

Chest Loot:
* At the start of the five round cycle, players can find random loot in chests.
* These chests disappear after the first round, and can destroyed.
