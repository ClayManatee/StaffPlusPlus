# StaffPlusPlus 
A straightforward Bukkit/Spigot plugin to help your server's staff team chat privately! Complete with a few extra customization and chat options to smooth out moderators' experience.

<p align="center">
  <img src="https://github.com/user-attachments/assets/03645cf4-196b-45c1-9b96-b0b23ce17ca5" />
</p>

# Features
- Permission-based chatting - access it with a command or toggle to always send private chats.
- Customize your chat with up to two basic Minecraft chat colors.
- List all online staff members & some EssentialsX info about them (vanished, AFK, etc.).
- Admins can list all saved staff members or purge the staff info database.
- PlaceholderAPI integration - see in a scoreboard/tab whether your staff chat is toggled on.

# Plugin Info
### Commands
- /staffchat (/sch) \<toggle | textcolor | accentcolor\> ...words
  - Base version - say some words to send messages to other staff members!
  - /sch toggle will swap you to staff chat - no need to prepend your messages with /sch!
  - /sch textcolor \<colorcode\> will update your text color; /sch accentcolor \<colorcode\> will do the same with your accent color.
    - These use basic Bukkit ChatColor characters (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e, f).
- /stafflist (/sl) will show all online staff!
- /staffroster is an admin-only command that will show info for all staff (online or not).
- /staffdelete \<name | uuid\> is a WIP admin-only command to delete the info of a staff member from the database.
- /staffpurge is an admin-only command to purge all staff data (but leave the database intact).

### Permissions
- staffplusplus.staffchat: needed to use /staffchat and /stafflist & to see %staffplusplus_staffchat_toggled% placeholder.
- staffplusplus.admin: needed to use /staffroster, /staffdelete, & /staffpurge.

### Placeholders
- %staffplusplus_staffchat_toggled%: displays "On" if staff chat is toggled on, "Off" if toggled off, and "N/A" without staffplusplus.staffchat permission.

# Requirements
### Runtime requirements:
- Some current Java
- Bukkit, Spigot, or Paper

### Build requirements:
- Some current JDK
- Gradle
- Git

# Build from source:
Just clone this repo & use Gradle to build. Should look something like this from a CLI:
``` 
git clone https://github.com/ClayManatee/StaffPlusPlus/
cd StaffPlusPlus
./gradlew build
```
The .jar generated should be in the /build folder!

# Extra stuff:
Many thanks to CreepyLemonz, creator of [Staffplus](https://github.com/CreepyLemonz/staffplus), for providing the basis for this whole plugin!

WIP - feel free to contact me via email (claymanatee@gmail.com) or via Discord (claymanatee) with any questions. :D
