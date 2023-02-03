/*
 * Copyright (c) 2020 Core2002
 * ZxbSkyWorldPlugin is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package fun.fifu.nekokecore.zxbskyworld.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MengTiGame implements CommandExecutor {

	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (command.getName().equalsIgnoreCase("mengti")) {
                if (args.length != 0) {
                    new Thread(() -> {
                        String str = "§a";
                        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                            while (i % 5 == 0 && i + 0 != 0) {
                                str = str + ";";
                                break;
                            }
                            switch ((int) (Math.random() * 4)) {
                                case 0:
                                    str = str + "A";
                                    break;
                                case 1:
                                    str = str + "B";
                                    break;
                                case 2:
                                    str = str + "C";
                                    break;
                                case 3:
                                    str = str + "D";
                                    break;
                            }

                        }
                        sender.sendMessage(str + Integer.parseInt(args[0]));
                    }).start();
				} else {
                    new Thread(() -> {
                        String str = "§a";
                        for (int i = 0; i < 80; i++) {

                            while (i % 5 == 0 && i + 0 != 0) {
                                str = str + ";";
                                break;
                            }

                            switch ((int) (Math.random() * 4)) {
                                case 0:
                                    str = str + "A";
                                    break;
                                case 1:
                                    str = str + "B";
                                    break;
                                case 2:
                                    str = str + "C";
                                    break;
                                case 3:
                                    str = str + "D";
                                    break;
                            }
                        }
                        sender.sendMessage(str);
                    }).start();
				}
				return true;
			}
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
