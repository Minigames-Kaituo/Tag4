package fun.kaituo;


import fun.kaituo.event.PlayerChangeGameEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

import static fun.kaituo.GameUtils.unregisterGame;
import static fun.kaituo.GameUtils.world;

public class Tag4 extends JavaPlugin implements Listener {
    static List<Player> players;
    static long gameTime;
    Scoreboard scoreboard;
    Team tag4norden;
    Team tag4cheshirecat;
    Team tag4redhat;
    Team tag4alice;
    Team tag4mabel;
    Team tag4kelti;
    Team tag4bill;
    Team tag4eunice;
    Team tag4dodo;
    Team tag4faketurtle;
    Team tag4victoria;
    Team tag4leaf;

    Team tag4miranda;
    Team tag4hein;
    Team tag4lindamayer;
    Team tag4baphomet;
    List<Team> teams;
    BoundingBox box = new BoundingBox(-1200, 0, 1800, -800, 200, 2200);

    public static Tag4Game getGameInstance() {
        return Tag4Game.getInstance();
    }

    @EventHandler
    public void displayChest(InventoryClickEvent ice) {
        if (ice.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (ice.getInventory().getHolder() instanceof Chest) {
            Location location = ((Chest) ice.getInventory().getHolder()).getBlock().getLocation();
            long x = location.getBlockX(); long y = location.getBlockY(); long z = location.getBlockZ();
            if (x == -1016 && y == 81 && z == 2020) {
                ice.setCancelled(true);
            } else if (x == -1016 && y == 81 && z == 2016) {
                ice.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void preventClickingTrapDoor(PlayerInteractEvent pie) {
        if (!pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!(pie.getClickedBlock().getBlockData() instanceof TrapDoor) && !(pie.getClickedBlock().getBlockData() instanceof Gate)) {
            return;
        }
        if (!pie.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (box.contains(pie.getClickedBlock().getLocation().toVector())) {
                pie.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onButtonClicked(PlayerInteractEvent pie) {
        if (!pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!pie.getClickedBlock().getType().equals(Material.OAK_BUTTON)) {
            return;
        }
        if (pie.getClickedBlock().getLocation().equals(new Location(world,-1003,82,2027))) {
            Tag4Game.getInstance().startGame();
        }
    }
    @EventHandler
    public void setGameTime(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        if (pie.getClickedBlock() == null) {
            return;
        }
        Location location = pie.getClickedBlock().getLocation();
        long x = location.getBlockX(); long y = location.getBlockY(); long z = location.getBlockZ();
        if (x == -1003 && y == 83 && z == 2027) {
            switch ((int)gameTime) {
                case 8400:
                case 10800:
                case 13200:
                case 15600:
                    gameTime += 2400;
                    break;
                case 18000:
                    gameTime = 8400;
                    break;
                default:
                    break;
            }
            Sign sign = (Sign) pie.getClickedBlock().getState();
            sign.setLine(2,"??????????????? " + gameTime/1200 + " ??????");
            sign.update();
        }
        if (Tag4Game.getInstance().running) {
            return;
        }
        if (x == -1005 && y == 81 && z == 2027) {
            if (tag4norden.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "??????", "??f");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4norden " + player.getName());
            player.sendMessage("??f????????f??? ???????????????" + player.getName() + "??????");
        } else if (x == -1006 && y == 81 && z == 2027) {
            if (tag4cheshirecat.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??d");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4cheshirecat " + player.getName());
            player.sendMessage("??d???????????f??? ????????????????????????????????????");
        } else if (x == -1007 && y == 81 && z == 2027) {
            if (tag4redhat.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??c");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4redhat " + player.getName());
            player.sendMessage("??c???????????f??? .......??????,???????????????");
        } else if (x == -1008 && y == 81 && z == 2027) {
            if (tag4alice.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??b");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4alice " + player.getName());
            player.sendMessage("??b???????????f??? ????????????????????????~???");
        } else if (x == -1009 && y == 81 && z == 2027) {
            if (tag4mabel.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??7");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4mabel " + player.getName());
            player.sendMessage("??7???????????f??? ?????????????????????????????????~");
        } else if (x == -1010 && y == 81 && z == 2027) {
            if (tag4kelti.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "??????", "??9");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4kelti " + player.getName());
            player.sendMessage("??9????????f??? ???????????????????????????????????????????????????"+ player.getName() + "???");
        } else if (x == -1000 && y == 81 && z == 2027) {
            if (tag4hein.hasPlayer(player)) {
                return;
            }
            broadcastDevilChoiceMessage(player, "??????", "??8");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4hein " + player.getName());
            player.sendMessage("??8????????f??? ????????????????????????????????????????????????");
        } else if (x == -999 && y == 81 && z == 2027) {
            if (tag4baphomet.hasPlayer(player)) {
                return;
            }
            broadcastDevilChoiceMessage(player, "?????????", "??8");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4baphomet " + player.getName());
            player.sendMessage("??8???????????f??? ????????????????????????????????????????????????~");
        } else if (x == -1002 && y == 81 && z == 2027) {
            if (tag4lindamayer.hasPlayer(player)) {
                return;
            }
            broadcastDevilChoiceMessage(player, "????????????", "??8");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4lindamayer " + player.getName());
            player.sendMessage("??8??????????????f??? ???????????????????????????????????????????????????????????????????????????");
        } else if (x == -1001 && y == 81 && z == 2027) {
            if (tag4miranda.hasPlayer(player)) {
                return;
            }
            broadcastDevilChoiceMessage(player, "?????????", "??8");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4miranda " + player.getName());
            player.sendMessage("??8???????????f??? ????????????????????????????????????????????????????????????......");
        }else if (x == -1011 && y == 81 && z == 2027) {
            if (tag4bill.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "??????", "??2");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4bill " + player.getName());
            player.sendMessage("??2????????f??? ??????...??????"+ player.getName()+"????????????...?????????????????????????????????~");
        }else if (x == -1012 && y == 81 && z == 2027) {
            if (tag4eunice.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??f");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4eunice " + player.getName());
            player.sendMessage("??f???????????f??? ?????????????????????????????????????????????????????????");
        }else if (x == -1013 && y == 81 && z == 2027) {
            if (tag4dodo.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "??????", "??7");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4dodo " + player.getName());
            player.sendMessage("??7????????f??? ????????????????????????????????????????????????????????????????????????");
        }else if (x == -1014 && y == 81 && z == 2027) {
            if (tag4faketurtle.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??3");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4faketurtle " + player.getName());
            player.sendMessage("??3???????????f??? ??????...???????????????????????????????????????......");
        }else if (x == -1015 && y == 81 && z == 2027) {
            if (tag4victoria.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "????????????", "??d");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4victoria " + player.getName());
            player.sendMessage("??d??????????????f??? ????????????,????????????");
        }else if (x == -1016 && y == 81 && z == 2027) {
            if (tag4leaf.hasPlayer(player)) {
                return;
            }
            broadcastHumanChoiceMessage(player, "?????????", "??a");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join tag4leaf " + player.getName());
            player.sendMessage("??a???????????f??? ??????,?????????????????????????????????!");
        }
    }
    public void onEnable() {
        saveDefaultConfig();
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        players = new ArrayList<>();
        tag4norden = scoreboard.getTeam("tag4norden");
        tag4baphomet = scoreboard.getTeam("tag4baphomet");
        tag4cheshirecat = scoreboard.getTeam("tag4cheshirecat");
        tag4redhat = scoreboard.getTeam("tag4redhat");
        tag4alice = scoreboard.getTeam("tag4alice");
        tag4mabel = scoreboard.getTeam("tag4mabel");
        tag4kelti = scoreboard.getTeam("tag4kelti");
        tag4bill = scoreboard.getTeam("tag4bill");
        tag4eunice = scoreboard.getTeam("tag4eunice");
        tag4dodo = scoreboard.getTeam("tag4dodo");
        tag4faketurtle = scoreboard.getTeam("tag4faketurtle");
        tag4victoria = scoreboard.getTeam("tag4victoria");
        tag4leaf = scoreboard.getTeam("tag4leaf");

        tag4miranda = scoreboard.getTeam("tag4miranda");
        tag4hein = scoreboard.getTeam("tag4hein");
        tag4lindamayer = scoreboard.getTeam("tag4lindamayer");
        tag4baphomet = scoreboard.getTeam("tag4baphomet");
        teams = List.of(tag4norden,tag4cheshirecat, tag4redhat,tag4alice,tag4mabel,tag4kelti,tag4bill,tag4eunice,tag4dodo,tag4faketurtle,tag4victoria,tag4leaf,tag4miranda,tag4hein,tag4lindamayer,tag4baphomet);
        Bukkit.getPluginManager().registerEvents(this, this);
        gameTime = 8400;
        Sign sign = (Sign) world.getBlockAt(-1003, 83, 2027).getState();
        sign.setLine(2,"??????????????? " + gameTime/1200 + " ??????");
        sign.update();
        GameUtils.registerGame(getGameInstance());
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll((Plugin)this);
        if (players.size() > 0) {
            for (Player p : players) {
                p.teleport(new Location(world, 0.5,89.0,0.5));
                Bukkit.getPluginManager().callEvent(new PlayerChangeGameEvent(p, getGameInstance(), null));
            }
        }
        unregisterGame(getGameInstance());
    }

    private void broadcastHumanChoiceMessage(Player player, String role, String color) {
        for (Team team: teams) {
            for (String entryName : team.getEntries()) {
                Player p = Bukkit.getPlayer(entryName);
                if (p != null) {
                    if (p.isOnline()) {
                        p.sendMessage(color + player.getName()+" ??r????????? " + color + role);
                    }
                }
            }
        }
    }
    private void broadcastDevilChoiceMessage(Player player, String role, String color) {
        for (Team team: teams) {
            for (String entryName : team.getEntries()) {
                Player p = Bukkit.getPlayer(entryName);
                if (p != null) {
                    if (p.isOnline()) {
                        p.sendMessage(color + player.getName()+" ??r???????????? " + color + role);
                    }
                }
            }
        }
    }
}

