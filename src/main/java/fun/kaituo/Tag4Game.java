package fun.kaituo;


import fun.kaituo.event.PlayerChangeGameEvent;
import fun.kaituo.event.PlayerEndGameEvent;
import fun.kaituo.utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;

import static fun.kaituo.GameUtils.*;

public class Tag4Game extends Game implements Listener {
    private static final Tag4Game instance = new Tag4Game((Tag4) Bukkit.getPluginManager().getPlugin("Tag4"));
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    Scoreboard tag4 = Bukkit.getScoreboardManager().getNewScoreboard();
    Tag4 plugin;
    List<Player> humans = new ArrayList<>();
    List<Player> devils = new ArrayList<>();


    List<Location> locations = new ArrayList<>();


    long startTime;
    long gameTime;
    Team team;

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

    boolean running = false;
    int countDownSeconds = 10;
    FileConfiguration c;


    HashMap<Player, List<Long>> coolDown = new HashMap<>();
    HashMap<ArmorStand, ArmorStand> armourStandMap = new HashMap<>();
    HashMap<ArmorStand, Player> playerMap = new HashMap<>();

    ItemStack feather = generateItemStack(Material.FEATHER, "??l??b????????????", new String[]{"????????????????????????", "??7????????????????????????????????????????????????????????????????????????????????????"});
    ItemStack cooked_chicken = generateItemStack(Material.COOKED_CHICKEN, "??l??b????????????", new String[]{"????????????????????????", "??7????????????????????????????????????????????????????????????????????????????????????"});
    ItemStack glass_bottle = generateItemStack(Material.GLASS_BOTTLE, "??l??7????????????", new String[]{"?????????????????????", "??7????????????"});
    ItemStack nether_star = generateItemStack(Material.NETHER_STAR, "??l??c??????", new String[]{"???????????????", "??7?????????????????????????????????????????????...", "??7???????????????????????????"});
    ItemStack clock = generateItemStack(Material.CLOCK, "??b??l???????????????", new String[]{"???????????????????????????", "??7???????????????????????????????????????????????????????????????"});
    ItemStack coal = generateItemStack(Material.COAL, "??l??8??????????????????", new String[]{"??????????????????????????????????????????????????????", "??7??????????????????????????????????????????????????????"});
    ItemStack potion = generateItemStack(Material.POTION, "??l??1??????", new String[]{"?????????????????????????????????????????????????????????????????????????????????", "??7????????????????????????????????????????????????????????????????????????????????????"});
    ItemStack dragon_breath = generateItemStack(Material.DRAGON_BREATH, "??l??d???????????????", new String[]{"????????????????????????????????????????????????????????????????????????", "??7???????????????????????????"});
    ItemStack honey_bottle = generateItemStack(Material.HONEY_BOTTLE, "??l??6???????????????", new String[]{"??????????????????????????????", "??7?????????????????????????????????????????????"});
    ItemStack enchanted_book = generateItemStack(Material.ENCHANTED_BOOK, "??l??8??k?????????????????????????????", new String[]{"????????????????????????????????????????????????", "??7??m?????????????????????"});
    ItemStack experience_bottle = generateItemStack(Material.EXPERIENCE_BOTTLE, "??l??d?????????", new String[] {"??????????????????????????????", "??????????????????","??7?????????????????????","??7??????????????????????????????????????????"});
    ItemStack amethyst_shard = generateItemStack(Material.AMETHYST_SHARD, "??l??8??????", new String[] {"???????????????????????????","????????????????????????","??7?????????????????????????????????????????????????????????????????????"});
    ItemStack ghast_tear = generateItemStack(Material.GHAST_TEAR, "??l??9???????????????", new String[]{"???????????????????????????","??????????????????????????????","(??????????????????????????????)","??7??????...????????????????...","??7????????????????????????......"});
    ItemStack enchanted_book2 = generateItemStack(Material.ENCHANTED_BOOK, "??l??9????????????", new String[]{"?????????????????????????????????","???????????????????????????","?????????????????????????????????????????????????????????????????","??7?????????????????????","??7????????????","??7?????????????????????????????????????????????","??7?????????????????????????????????????????????????????????????????????????????????"});
    ItemStack mushroom_stew = generateItemStack(Material.MUSHROOM_STEW, "??2???????????????", new String[]{"?????????????????????????????????????????????????????????????????????","","??7??o????????????????????????????????????????????????????????????????????????"});
    ItemStack medium_amethyst_bud = generateItemStack(Material.MEDIUM_AMETHYST_BUD, "??d??l???????????????????????????", new String[]{"???????????????????????????","????????????????????????????????????????????????","?????????????????????????????????????????????????????????","??7??o?????????????????????????????????????????????????????????", "??7??o???????????????????????????????????????????????????????????????????????????","??c??o????????????"});
    ItemStack large_amethyst_bud = generateItemStack(Material.LARGE_AMETHYST_BUD, "??d??l???????????????????????????", new String[]{"???????????????????????????","???????????????????????????????????????????????????????????????(????????????)????????????????????????","??7??o?????????????????????????????????????????????????????????", "??7??o???????????????????????????????????????????????????????????????????????????","??c??o????????????"});


    List<ItemStack> gadgets = Arrays.asList(feather, glass_bottle, nether_star, clock, potion, honey_bottle, coal, dragon_breath,experience_bottle,amethyst_shard);
    List<Integer> gadgetWeights = Arrays.asList(10, 5, 1, 10, 1, 10, 5, 10, 5, 10);

    int totalWeight;

    private Tag4Game(Tag4 plugin) {
        this.plugin = plugin;
        initializeGame(plugin, "Tag4",  "??3????????????", new Location(world, -1003.0, 81, 2021.0),
                new BoundingBox(-1200, 0, 1800, -800, 200, 2200));
        initializeButtons(new Location(world, -1003, 82, 2027), BlockFace.NORTH,
                new Location(world, -1004, 82, 2027), BlockFace.NORTH);
        players = Tag4.players;
        tag4.registerNewObjective("tag4", "dummy", "?????????");
        tag4.getObjective("tag4").setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i : gadgetWeights) {
            totalWeight += i;
        }


        c = plugin.getConfig();
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

        ConfigurationSection section = c.getConfigurationSection("chest-locations");

        for (String key : section.getKeys(false)) {
            int x = c.getInt("chest-locations." + key + ".x");
            int y = c.getInt("chest-locations." + key + ".y");
            int z = c.getInt("chest-locations." + key + ".z");
            locations.add(new Location(world, x, y, z));
        }
    }

    public static Tag4Game getInstance() {
        return instance;
    }

    @EventHandler
    public void clearCoolDown(PlayerDeathEvent pde) {
        clearCoolDown(pde.getEntity());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent ede) { //????????????
        if (!(ede.getEntity() instanceof Player)) {
            return;
        }
        if (ede.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if ((((EntityDamageByEntityEvent) ede).getDamager() instanceof Player)) {
                if (humans.contains(((EntityDamageByEntityEvent) ede).getDamager())) {
                    return;
                }
            }
        }//????????????????????????

        Player p = (Player) ede.getEntity();
        if (tag4cheshirecat.hasPlayer(p)) {
            if (ede.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, c.getInt("cheshirecat.speed-duration"), c.getInt("cheshirecat.speed-amplifier"), true, false));
                ede.setCancelled(true);
            } else if (p.getInventory().contains(Material.CLOCK, 1)) {
                removeItem(p, Material.CLOCK);
                ede.setCancelled(true);
            }
        } else if (p.getInventory().contains(Material.CLOCK, 1)) {
            removeItem(p, Material.CLOCK);
            ede.setCancelled(true);
        }


        //?????????????????????
        if (tag4kelti.hasPlayer(p)) {
            p.sendMessage("??b?????????????????????");
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, c.getInt("kelti.invisibility-duration"), 0, false, false));

        } else if (tag4dodo.hasPlayer(p)) {
            p.sendMessage("???????????????");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, c.getInt("dodo.speed-duration"), 1));
        } else if (tag4redhat.hasPlayer(p)) {
            p.sendMessage("??????????????????????????????");
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, c.getInt("redhat.duration"), c.getInt("redhat.regeneration-amplifier"), false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, c.getInt("redhat.duration"), 0, false, false));
        }
    }

    private void initializePlayer(Player p) {
        switch (scoreboard.getPlayerTeam(p).getName()) {
            case "tag4hein" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 1, false, false));
            }
            case "tag4miranda", "tag4baphomet", "tag4lindamayer" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000, 0, false, false));
            }
            case "tag4norden" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10000000, 4, true, false));
            }
            case "tag4kelti" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 10000000, 0, false, false));
            }
            case "tag4redhat" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10000000, c.getInt("redhat.resistance-amplifier"), false, false));
            }
            case "tag4dodo" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 0, false, false));
            }
            case "tag4faketurtle" -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10000000, c.getInt("faketurtle.resistance-amplifier"), false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000000,  c.getInt("faketurtle.slowness-amplifier"), false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000000,  c.getInt("faketurtle.jump-amplifier"), false, false));
            }
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 0, false, false));
    }

    private Player revivePlayer(ArmorStand s) {
        if ((armourStandMap.get(s) == null)) {
            return null;
        }
        if (!playerMap.containsKey(s)) {
            return null;
        }
        Player p = playerMap.get(s);
        if (!(players.contains(p) && !humans.contains(p))) {
            return null;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Location l = s.getLocation();
            l.setY(l.getY() + 1.4);
            humans.add(p);
            p.teleport(l);
            p.setGameMode(GameMode.ADVENTURE);
            initializePlayer(p);
        }, 1);
        armourStandMap.get(s).remove();
        s.remove();
        armourStandMap.remove(s);
        playerMap.remove(s);
        return p;
    }

    @EventHandler
    public void armorStandOperation(PlayerInteractAtEntityEvent piaee) {
        if (!piaee.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }
        if (!(players.contains(piaee.getPlayer()))) {
            return;
        }
        if (!(piaee.getRightClicked() instanceof ArmorStand)) {
            return;
        }
        if (piaee.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            piaee.setCancelled(true);
        }

        ItemStack item = piaee.getPlayer().getInventory().getItemInMainHand();
        if (item.getType().equals(Material.NETHER_STAR)) {
            revivePlayer((ArmorStand) piaee.getRightClicked());
            item.setAmount(item.getAmount() - 1);
        }
    }

    @EventHandler
    public void destroyChest(PlayerInteractEvent pie) {
        if (!pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }// ??????????????????
        Player executor = pie.getPlayer();
        if (!(devils.contains(executor))) {
            return;
        } //?????????
        Block block = pie.getClickedBlock();
        if (block.getType().equals(Material.TRAPPED_CHEST)) {
            if (pie.getItem() == null) {
                return;
            }//????????????
            if (!pie.getItem().getType().equals(Material.POPPED_CHORUS_FRUIT)) {
                return;
            }
            pie.setCancelled(true);
            if (((Chest) (block.getState())).getBlockInventory().isEmpty()) {
                executor.sendMessage("??c????????????????????????");
            } else if (checkCoolDown(executor, 600)) {
                ((Chest) (block.getState())).getBlockInventory().clear();
                executor.sendMessage("??a???????????????????????????????????????");
            }
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent epee) {
        if (!epee.getCause().equals(EntityPotionEffectEvent.Cause.PLUGIN)) {
            return;
        }
        if (epee.getNewEffect() == null) {
            return;
        }
        if (!epee.getNewEffect().getType().equals(PotionEffectType.GLOWING)) {
            return;
        }
        if (!players.contains(epee.getEntity())) {
            return;
        }
        for (Player p : devils) {
            if (tag4lindamayer.hasPlayer(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, epee.getNewEffect().getDuration(), 1, true, false));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent pie) { //????????????
        if (!pie.getAction().equals(Action.RIGHT_CLICK_AIR) && !pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }// ????????????
        Player executor = pie.getPlayer();
        if (!players.contains(executor)) {
            return;
        }//??????tag4???
        if (devils.contains(executor)) { //??????
            if (pie.getItem() == null) {
                return;
            }//????????????
            //????????????????????????

            switch (pie.getItem().getType()) {
                case NETHERITE_SWORD -> {
                    if (checkCoolDown(executor, 3600)) {
                        for (Player p: humans) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0, true, false));
                        }
                        for (Player p : devils) {
                            if (tag4hein.hasPlayer(p)) {
                                for (Player victim: humans) {
                                    victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0, true, false));
                                }
                                break;
                            }
                        }
                        for (Player p : players) {
                            p.sendMessage("??8??????????????c????????????????????????");
                        }
                    }
                }
            }
        } else if (humans.contains(executor)) { //??????
            if (pie.getClickedBlock() != null) {
                if (pie.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                    if (!pie.getPlayer().isSneaking()) {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0));
                        return;
                    }
                }
            }
            if (pie.getItem() == null) {
                return;
            }//????????????
            //????????????????????????

            switch (pie.getItem().getType()) {
                case MUSHROOM_STEW -> {
                    Player humanWhoLostMostHealth = executor;
                    double lostHealth = 0;
                    for (Player p : humans) {
                        if (tag4bill.hasPlayer(p)) {
                            continue;
                        }
                        double newLostHealth = p.getMaxHealth() - p.getHealth();
                        if (newLostHealth >= lostHealth) {
                            humanWhoLostMostHealth = p;
                            lostHealth = newLostHealth;
                        }
                    }
                    if (lostHealth == 0) {
                        clearCoolDown(executor);
                        executor.sendMessage("??c?????????????????????????????????????????????????????????");
                    } else {
                        pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                        humanWhoLostMostHealth.setHealth(humanWhoLostMostHealth.getMaxHealth());
                        executor.sendMessage("??a??? ??f" + humanWhoLostMostHealth.getName() + " ??a????????????????????????");
                        humanWhoLostMostHealth.sendMessage("??2" + executor.getName() + " ??f?????????????????????????????????");
                    }
                }
                case ENDER_EYE -> {
                    if (checkCoolDown(executor, c.getInt("mabel.active-cd"))) {
                        executor.sendMessage("??a????????????????????????????????????????????????");
                        for (Player p : devils) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, c.getInt("mabel.active-duration"), 0));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, c.getInt("mabel.active-duration"), 0));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, c.getInt("mabel.active-duration"), 1));
                            p.sendMessage("??c???????????????????????????????????????????????????");
                        }
                    }
                }

                case BOOK -> {
                    ArmorStand target = null;
                    for (Entity e : world.getNearbyEntities(executor.getLocation(), 200,200,200, e ->  playerMap.containsKey(e))) {
                        if (target == null) {
                            target = (ArmorStand) e;
                        } else {
                            if (executor.getLocation().distance(e.getLocation()) < executor.getLocation().distance(target.getLocation())) {
                                target = (ArmorStand) e;
                            }
                        }
                    }
                    if (target == null) {
                        executor.sendMessage("??c????????????????????????????????????");
                    } else {
                        Player p = revivePlayer(target);
                        if (p != null) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 3, false, false));
                        }
                        pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                        executor.getInventory().addItem(enchanted_book);
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 0));
                    }
                }

                case GHAST_TEAR -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.getInventory().addItem(enchanted_book2);
                    if (executor.getHealth() <= 10) {
                        executor.setHealth(1);
                    } else {
                        executor.setHealth(executor.getHealth() - 10);
                    }
                    //executor.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10000000, 0, true, false));
                    executor.sendMessage("??a???????????????????????????????????????????????????????????????");
                    for (Player p : humans) {
                        p.sendMessage("??a?????9????????a?????????????????????");
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false));
                    }
                }

                case ENCHANTED_BOOK -> {
                    if (tag4kelti.hasPlayer(executor)) {
                        if (checkCoolDown(executor, 1200)) {
                            executor.sendMessage("??a?????????????????????????????????????????????????????????????????????????????????");
                            if (executor.getMaxHealth() <= 3) {
                                executor.setHealth(0);
                            } else {
                                executor.setMaxHealth(executor.getMaxHealth() - 3);
                            }
                            for (Player p : humans) {
                                p.sendMessage("??a?????9????????a?????????????????????????????????????????????");
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 75, 1, false, false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 3, false, false));
                            }
                        }
                    }
                }

                case MEDIUM_AMETHYST_BUD -> {
                    if (checkCoolDown(executor, 50)) {
                        pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                        executor.getInventory().addItem(large_amethyst_bud);
                        executor.sendMessage("??d????????????????????????");
                    }
                }

                case LARGE_AMETHYST_BUD -> {
                    if (checkCoolDown(executor, 50)) {
                        pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                        executor.getInventory().addItem(medium_amethyst_bud);
                        executor.sendMessage("??d????????????????????????");
                    }
                }

                case COAL -> {
                    if (tag4norden.hasPlayer(executor) || tag4victoria.hasPlayer(executor)) {
                        executor.setHealth(executor.getMaxHealth());
                        executor.sendMessage("??c?????????????????????");
                        pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    } else {
                        if (executor.getMaxHealth() > 3) {
                            executor.sendMessage("??c?????????????????????????????????????????????");
                            executor.setMaxHealth(executor.getMaxHealth() - 3);
                            executor.setHealth(executor.getMaxHealth());

                            pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                        } else {
                            executor.sendMessage("??c????????????????????????????????????");
                        }
                    }
                }
                case POTION -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??c30???????????????30???????????????????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 254, false, false));
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        executor.setHealth(0);
                    }, 600);
                }
                case GLASS_BOTTLE -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??c??????10??????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0, false, false));
                }
                case DRAGON_BREATH -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??c????????????????????????????????????????????????????????????????????????");
                    if (executor.getHealth() * 2 < executor.getMaxHealth()) {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (int) (executor.getMaxHealth() * 10), 0, false, false));
                        executor.setHealth(executor.getHealth() + executor.getMaxHealth() / 2);

                    } else {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, (int) ((executor.getMaxHealth() - executor.getHealth()) * 20), 0, false, false));
                        executor.setHealth(executor.getMaxHealth());

                    }
                }
                case HONEY_BOTTLE -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??e???????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 40, 0, false, false));
                }
                case FEATHER -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.getInventory().addItem(cooked_chicken);
                    executor.sendMessage("??b???????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, false, false));
                    if ((tag4dodo.hasPlayer(executor))) {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0));
                    }
                }
                case COOKED_CHICKEN -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??b???????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1, false, false));
                }
                case BEETROOT_SOUP -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??a??????????????????????????????????????????");
                    if (executor.getHealth() * 2 < executor.getMaxHealth()) {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, c.getInt("faketurtle.invisibility-duration"), 0, false, false));
                        executor.setHealth(executor.getHealth() + executor.getMaxHealth() / 2);

                    } else {
                        executor.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false));
                        executor.setHealth(executor.getMaxHealth());
                    }
                }
                case EXPERIENCE_BOTTLE -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    pie.setCancelled(true);
                    executor.sendMessage("??a???????????????????????????????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0, false, false));
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10000000, 1, false, false));
                }
                case AMETHYST_SHARD -> {
                    pie.getItem().setAmount(pie.getItem().getAmount() - 1);
                    executor.sendMessage("??a???????????????????????c???????????????");
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,  150, 1, false, false));
                    executor.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,  300, 0, true, false));
                }
            }
        }
    }

    @EventHandler
    public void cancelItemMovement(InventoryClickEvent ice) {
        if (!(ice.getWhoClicked() instanceof Player)) {
            return;
        }
        if (ice.getCurrentItem() == null) {
            return;
        }
        Player p = (Player) ice.getWhoClicked();
        if (devils.contains(p)) {
            ice.setCancelled(true);
        } else if (humans.contains(p)) {
            if (ice.getCurrentItem().getType().equals(Material.BOOK)
                    || ice.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)
                    || ice.getCurrentItem().getType().equals(Material.HEART_OF_THE_SEA)
                    || ice.getCurrentItem().getType().equals(Material.RABBIT_FOOT)
                    || ice.getCurrentItem().getType().equals(Material.ENDER_EYE)
                    || ice.getCurrentItem().getType().equals(Material.STRING)
                    || ice.getCurrentItem().getType().equals(Material.RED_DYE)
                    || ice.getCurrentItem().getType().equals(Material.POPPED_CHORUS_FRUIT)
                    || ice.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)
                    || ice.getCurrentItem().getType().equals(Material.END_ROD)
                    || ice.getCurrentItem().getType().equals(Material.TURTLE_EGG)
                    || ice.getCurrentItem().getType().equals(Material.SWEET_BERRIES)
                    || ice.getCurrentItem().getType().equals(Material.MEDIUM_AMETHYST_BUD)
                    || ice.getCurrentItem().getType().equals(Material.BLAZE_POWDER)
                    || ice.getCurrentItem().getType().equals(Material.IRON_SWORD)
                    || ice.getCurrentItem().getType().equals(Material.CHARCOAL)
                    || ice.getCurrentItem().getType().equals(Material.NETHERITE_AXE)
                    || ice.getCurrentItem().getType().equals(Material.NETHERITE_SWORD)
                    || ice.getCurrentItem().getType().equals(Material.NETHERITE_HOE)) {
                ice.setCancelled(true);
            }
        }
    }

    public void removeItem(Player p, Material material) {
        p.getInventory().all(material).get(0);
        for (Map.Entry entry : p.getInventory().all(material).entrySet()) {
            ItemStack i = (ItemStack) entry.getValue();
            i.setAmount(i.getAmount() - 1);
            break;
        }
    }

    public void clearCoolDown(Player p) {
        p.setLevel(0);
        p.setExp(0);
        if (coolDown.get(p) != null) {
            coolDown.get(p).set(0, 0L);
            coolDown.get(p).set(1, 0L);
        }
    }

    private void setCoolDown(Player p, long remainingCoolDown, long totalCoolDown) {
        coolDown.get(p).set(0, remainingCoolDown);
        coolDown.get(p).set(1, totalCoolDown);
    }

        private boolean checkCoolDown(Player p, long cd) {
            if (coolDown.get(p).get(0) == 0) {
                coolDown.get(p).set(0, cd);
                coolDown.get(p).set(1, cd);
                return true;
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("??4??l??????????????????"));
                return false;
            }
        }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde) {
        if (!players.contains(pde.getEntity())) {
            return;
        }
        devils.remove(pde.getEntity());
        humans.remove(pde.getEntity());
        pde.getEntity().setGameMode(GameMode.SPECTATOR);
        for (Player p : players) {
            p.sendMessage("??f" + pde.getEntity().getName() + " ??c ???????????????");
        }
    }

    @EventHandler
    public void freezeGui(EntityDamageByEntityEvent edbee) {
        if (!(edbee.getDamager() instanceof Player)) {
            return;
        }
        if (!(edbee.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) edbee.getEntity();
        Player damager = (Player) edbee.getDamager();
        if (humans.contains(damager) && devils.contains(victim)) {
            if (tag4eunice.hasPlayer(damager)) {
                if (damager.getInventory().getItemInMainHand().getType().equals(Material.IRON_SWORD)) {
                    if (checkCoolDown(damager,  c.getInt("eunice.cd"))) {
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, c.getInt("eunice.duration"), 1, false, false));
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,  150, 1, false, false));
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,  c.getInt("eunice.duration"), 0, false, false));
                        damager.sendMessage("??c?????????????????????????????????");
                    }
                }
            } else {
                edbee.setCancelled(true);
            }
        } else if (humans.contains(damager) && humans.contains(victim)) {
            edbee.setCancelled(true);
        } else if (devils.contains(damager) && humans.contains(victim)) {
            if (tag4baphomet.hasPlayer(damager)) {
                if (tag4norden.hasPlayer(victim)) {
                    victim.sendMessage("??a???????????????????????????????????????");
                } else {
                    if (victim.getMaxHealth() > 3) {
                        victim.sendMessage("??c?????????????????????????????????????????????");
                        victim.setMaxHealth(victim.getMaxHealth() - 3);
                    } else {
                        victim.sendMessage("??a??????????????????????????????????????????");
                    }
                }
            }
            if (tag4mabel.hasPlayer(victim)) {
                damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, c.getInt("mabel.passive-blindness-duration"), 0));
            }
            int freezeTime = 60;
            if (tag4miranda.hasPlayer(damager)) { //?????????
                freezeTime = 110;
            } else if (tag4baphomet.hasPlayer(damager)) { //?????????
                freezeTime = 50;
            }
            Location l = damager.getLocation().clone();
            int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                damager.teleport(l);
            }, 1, 1);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.getScheduler().cancelTask(id);
            }, freezeTime);
            damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, freezeTime, 254, false, false));
        }
    }

    //seems not useful
    /*
    @EventHandler
    public void cancelPickup(PlayerPickupItemEvent ppie) {
        Player p = ppie.getPlayer();
        if (humans.contains(p)) {
            if (ppie.getItem().getItemStack().getType().equals(Material.CLOCK)
                    || ppie.getItem().getItemStack().getType().equals(Material.EMERALD)) {
                ppie.setCancelled(true);
            }
        } else if (devils.contains(p)) {
            if (ppie.getItem().getItemStack().getType().equals(Material.DIAMOND)
                    || ppie.getItem().getItemStack().getType().equals(Material.RED_DYE)
                    || ppie.getItem().getItemStack().getType().equals(Material.HEART_OF_THE_SEA)) {
                ppie.setCancelled(true);
            }
        }
    }

     */

    public ItemStack generateItemStack(Material material, String name, String[] lore) {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore.clone()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @EventHandler
    public void summonCorpse(PlayerDeathEvent pde) {
        if (!(players.contains(pde.getEntity()))) {
            return;
        }
        if ((devils.contains(pde.getEntity()))) {
            return;
        }
        if (pde.getEntity().getInventory().contains(Material.ENCHANTED_BOOK, 1)) {
            if (tag4alice.hasPlayer(pde.getEntity())) {
                for (Player p : players) {
                    p.sendMessage("??f" + pde.getEntity().getName() + " ??c ?????????????????????");
                }
                return;
            } else if (tag4kelti.hasPlayer(pde.getEntity())) {
                for (Player p : players) {
                    p.sendMessage("??f" + pde.getEntity().getName() + " ??c ?????????????????????");
                }
                return;
            }
        } else if (pde.getEntity().getKiller() != null) {
            if (tag4baphomet.hasPlayer(pde.getEntity().getKiller())) {
                for (Player p : players) {
                    p.sendMessage("??f" + pde.getEntity().getName() + " ??c ?????????????????????");
                }
                return;
            }
        }
        Location l = pde.getEntity().getLocation().clone();

        ArmorStand ice = (ArmorStand) world.spawnEntity(l, EntityType.ARMOR_STAND);
        ice.setBasePlate(false);
        ice.setInvisible(true);
        ice.setVelocity(new Vector(0, -50, 0));

        l.setY(0);

        ArmorStand head = (ArmorStand) world.spawnEntity(l, EntityType.ARMOR_STAND);
        ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) headItem.getItemMeta();
        skullMeta.setOwningPlayer(pde.getEntity());
        headItem.setItemMeta(skullMeta);
        head.setBasePlate(false);
        head.setSmall(true);
        head.getEquipment().setHelmet(headItem);
        head.setGravity(false);
        head.setCustomName(pde.getEntity().getName());
        head.setCustomNameVisible(true);
        head.setInvisible(true);
        EulerAngle angle = new EulerAngle(Math.PI, 0, 0);
        head.setLeftLegPose(angle);
        head.setRightLegPose(angle);

        armourStandMap.put(ice, head);
        playerMap.put(ice, pde.getEntity());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ice.setGravity(false);
            Location iceLocation = ice.getLocation().clone();
            iceLocation.setY(iceLocation.getY() - 1.4);
            ice.teleport(iceLocation);
            iceLocation.setY(iceLocation.getY() + 0.75);
            head.teleport(iceLocation);
        }, 5);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ice.getEquipment().setHelmet(new ItemStack(Material.BROWN_STAINED_GLASS));
            head.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 594, 0));
        }, 6);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (head.isValid()) {
                head.remove();
                ice.remove();
                for (Player p : players) {
                    p.sendMessage("??f" + pde.getEntity().getName() + " ??c ?????????????????????");
                }
            }
        }, 600);
    }

    @EventHandler
    public void preventDroppingItem(PlayerDropItemEvent pdie) {
        if (!(players.contains(pdie.getPlayer()))) {
            return;
        }
        if (!pdie.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }
        switch (pdie.getItemDrop().getItemStack().getType()) {
            case RABBIT_FOOT, BOOK, STRING, HEART_OF_THE_SEA, ENCHANTED_BOOK, ENDER_EYE, RED_DYE, POPPED_CHORUS_FRUIT,
                    TOTEM_OF_UNDYING, END_ROD, FEATHER, TURTLE_EGG,MEDIUM_AMETHYST_BUD,LARGE_AMETHYST_BUD, BLAZE_POWDER, MUSHROOM_STEW, BEETROOT_SOUP,
                    IRON_SWORD, NETHERITE_AXE, NETHERITE_HOE, NETHERITE_SWORD, CHARCOAL-> pdie.setCancelled(true);
            default -> {
            }
        }
    }

    @EventHandler
    public void preventRegen(EntityRegainHealthEvent erhe) {
        if (!(erhe.getEntity() instanceof Player)) {
            return;
        }
        if (!(players.contains(erhe.getEntity()))) {
            return;
        }
        if (!((Player) erhe.getEntity()).getGameMode().equals(GameMode.ADVENTURE)) {
            return;
        }
        if (erhe.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)) {
            erhe.setCancelled(true);
        } else if (erhe.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.EATING)) {
            erhe.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChangeGame(PlayerChangeGameEvent pcge) {
        players.remove(pcge.getPlayer());
        humans.remove(pcge.getPlayer());
        devils.remove(pcge.getPlayer());
    }

    public void savePlayerQuitData(Player p) {
        PlayerQuitData quitData = new PlayerQuitData(p, this, gameUUID);
        quitData.getData().put("team", whichGroup(p));
        setPlayerQuitData(p.getUniqueId(), quitData);
        players.remove(p);
        humans.remove(p);
        devils.remove(p);
    }

    @Override
    protected void rejoin(Player p) {
        if (!running) {
            p.sendMessage("??c?????????????????????");
            return;
        }
        if (!getPlayerQuitData(p.getUniqueId()).getGameUUID().equals(gameUUID)) {
            p.sendMessage("??c?????????????????????");
            return;
        }
        PlayerQuitData pqd = getPlayerQuitData(p.getUniqueId());
        pqd.restoreBasicData(p);
        players.add(p);
        team.addPlayer(p);
        p.setScoreboard(tag4);
        if (pqd.getData().get("team") != null) {
            ((List<Player>) pqd.getData().get("team")).add(p);
        }
        setPlayerQuitData(p.getUniqueId(), null);
    }

    private List<Player> whichGroup(Player p) {
        if (humans.contains(p)) {
            return humans;
        } else if (devils.contains(p)) {
            return devils;
        } else {
            return null;
        }
    }

    private void endGame(String msg, List<Player> winningPlayers) {
        List<Player> winningPlayersCopy = new ArrayList<>(winningPlayers);
        List<Player> playersCopy = new ArrayList<>(players);
        for (Player p : winningPlayersCopy) {
            spawnFireworks(p);
        }
        for (Player p : playersCopy) {
            p.sendTitle(msg, null, 5, 50, 5);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.teleport(new Location(world, -1003.0, 81, 2021.0));
                Bukkit.getPluginManager().callEvent(new PlayerEndGameEvent(p, this));
            }, 100);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                scoreboard.getTeam("tag4norden").addPlayer(p);
            }, 101);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Entity e : world.getNearbyEntities(new Location(world, -1000, 128, 2000), 200, 200, 200)) {
                if (e instanceof Item) {
                    e.remove();
                }
            }
            clearChests();
            for (Map.Entry<ArmorStand, ArmorStand> entry : armourStandMap.entrySet()) {
                entry.getKey().remove();
                entry.getValue().remove();
            }
            armourStandMap.clear();
            playerMap.clear();
            removeSpectateButton();
            placeStartButton();
            HandlerList.unregisterAll(this);
            tag4.getObjective("tag4").getScore("????????????").setScore(0);
            tag4.getObjective("tag4").getScore("????????????").setScore(0);
        }, 100);
        players.clear();
        humans.clear();
        devils.clear();
        team.unregister();
        running = false;
        gameUUID = UUID.randomUUID();
        cancelGameTasks();
    }

    @Override
    protected void initializeGameRunnable() {
        gameRunnable = () -> {
            gameTime = Tag4.gameTime;
            team = tag4.registerNewTeam("tag4");
            team.setNameTagVisibility(NameTagVisibility.NEVER);
            team.setCanSeeFriendlyInvisibles(false);
            team.setAllowFriendlyFire(true);
            for (Player p : getPlayersNearHub(50, 50, 50)) {
                if (scoreboard.getPlayerTeam(p) == null) {
                    continue;
                }
                switch (scoreboard.getPlayerTeam(p).getName()) {
                    case "tag4kelti", "tag4cheshirecat", "tag4mabel", "tag4norden", "tag4redhat", "tag4alice" , "tag4bill", "tag4eunice", "tag4dodo", "tag4faketurtle", "tag4victoria", "tag4leaf"-> {
                        humans.add(p);
                    }
                    case "tag4hein", "tag4miranda", "tag4lindamayer","tag4baphomet" -> {
                        devils.add(p);
                    }
                }
                players.add(p);
                team.addPlayer(p);
            }
            if (players.size() < 2) {
                for (Player p : players) {
                    p.sendMessage("??c????????????2????????????????????????");
                }
                players.clear();
                humans.clear();
                team.unregister();

            } else if (humans.size() == 0) {
                for (Player p : players) {
                    p.sendMessage("??c????????????1??????????????????????????????");
                }
                players.clear();
                humans.clear();
                team.unregister();
            } else if (devils.size() == 0) {
                for (Player p : players) {
                    p.sendMessage("??c????????????1???????????????????????????");
                }
                players.clear();
                humans.clear();
                team.unregister();
            } else {
                startTime = getTime(world) + countDownSeconds * 20L + 400;
                running = true;
                removeStartButton();
                startCountdown(countDownSeconds);
                Bukkit.getScheduler().runTask(plugin, () -> {
                    for (Player p : players) {
                        p.getInventory().clear();
                    }
                });
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Bukkit.getPluginManager().registerEvents(this, plugin);
                    for (Player p : humans) {
                        p.teleport(new Location(world, -1003.0,108,2014.0, 180, 0));
                    }
                    for (Player p : devils) {
                        p.teleport(new Location(world, -1002.5,103,2007.0, 180, 0));
                    }
                    for (Player p : players) {
                        initializePlayer(p);
                        p.setScoreboard(tag4);
                    }

                }, countDownSeconds * 20L);
                for (int i = 0; i < 5; i++) {
                    int finalI = i;
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        for (Player p : players) {
                            p.sendTitle("??a" + (5 - finalI), null, 2, 16, 2);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 1f);
                        }
                    }, countDownSeconds * 20L + 300 + i * 20);
                }

                taskIds.add(
                        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
                            for (Player p : players) {
                                if (coolDown.get(p) == null) {
                                    List<Long> l = new ArrayList<>(2);
                                    l.add(0,0L);
                                    l.add(1,0L);
                                    coolDown.put(p, l);
                                } else {
                                    List<Long> l = coolDown.get(p);
                                    //p.sendMessage(String.valueOf(l.get(0)));
                                    if (l.get(0) > 0) {
                                        l.set(0, l.get(0) - 1);
                                    }
                                    if (l.get(1) != 0) {
                                        p.setLevel((int) Math.ceil(((float)l.get(0)) / 20));
                                        p.setExp(((float)(l.get(1) - l.get(0))) / l.get(1));
                                    }

                                }
                            }
                        }, 1,1 ));

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    placeSpectateButton();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "function tag4:go");
                    for (Player p : devils) {
                        p.teleport(new Location(world, -1003.0,108,2014.0, 180, 0));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 4, false, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 0, false, false));
                        ItemStack skull = new ItemStackBuilder(Material.WITHER_SKELETON_SKULL).addEnchantment(Enchantment.BINDING_CURSE, 1).build();
                        ItemStack chestPlate = new ItemStackBuilder(Material.NETHERITE_CHESTPLATE).addEnchantment(Enchantment.BINDING_CURSE, 1).setUnbreakable(true).build();
                        ItemStack leggings = new ItemStackBuilder(Material.NETHERITE_LEGGINGS).addEnchantment(Enchantment.BINDING_CURSE, 1).setUnbreakable(true).build();
                        ItemStack boots = new ItemStackBuilder(Material.NETHERITE_BOOTS).addEnchantment(Enchantment.BINDING_CURSE, 1).setUnbreakable(true).build();
                        p.getInventory().setItem(EquipmentSlot.HEAD, skull);
                        p.getInventory().setItem(EquipmentSlot.CHEST, chestPlate);
                        p.getInventory().setItem(EquipmentSlot.LEGS, leggings);
                        p.getInventory().setItem(EquipmentSlot.FEET, boots);
                    }
                    for (Player p : players) {
                        if (tag4leaf.hasPlayer(p)) {
                            p.getInventory().addItem(coal);
                        } else if (tag4lindamayer.hasPlayer(p)) {
                            setCoolDown(p, 600, 3600);
                        } else if (tag4kelti.hasPlayer(p)) {
                            p.getInventory().addItem(ghast_tear);
                        } else if (tag4bill.hasPlayer(p)) {
                            p.getInventory().addItem(mushroom_stew);
                        }
                        p.setHealth(20);
                        p.sendTitle("??e???????????????", null, 2, 16, 2);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1f, 2f);
                    }

                }, countDownSeconds * 20L + 400);


                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : players) {
                        if (devils.contains(p)) {
                            for (Player victim : players) {
                                if (humans.contains(victim)) {
                                    if (p.getLocation().distance(victim.getLocation()) < 10) {
                                        victim.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.BLOCKS, 2f, 0f);
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            victim.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.BLOCKS, 2f, 0f);
                                        }, 3);
                                    }
                                    if (p.getLocation().distance(victim.getLocation()) < 5) {
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            victim.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.BLOCKS, 2f, 0f);
                                        }, 10);
                                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                            victim.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.BLOCKS, 2f, 0f);
                                        }, 13);
                                    }

                                }
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400, 20));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : players) {
                        p.sendMessage("??a??????????????????");
                    }
                    for (Location loc : locations) {
                        double spawnChance = random.nextDouble();
                        if (spawnChance < 0.5) {//overall chance
                            int spawnNo = random.nextInt(totalWeight);
                            int counter = 0;
                            for (int i = 0; i < gadgets.size(); i++) {
                                counter += gadgetWeights.get(i);
                                if (spawnNo < counter) {
                                    ((Chest) (world.getBlockAt(loc).getState())).getBlockInventory().addItem(gadgets.get(i));
                                    break;
                                }
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400 + 600, 1200));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : devils) {
                        if (tag4miranda.hasPlayer(p)) {
                            int counter = 0;
                            for (ItemStack i : p.getInventory().all(Material.ENDER_PEARL).values()) {
                                counter += i.getAmount();
                            }
                            if (counter < 1) {
                                p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                                p.sendMessage("??a?????????????????????");
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400 + 200, 1200));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : humans) {
                        if (tag4eunice.hasPlayer(p)) {
                            int counter = 0;
                            for (ItemStack i : p.getInventory().all(Material.CLOCK).values()) {
                                counter += i.getAmount();
                            }
                            if (counter < 1) {
                                p.getInventory().addItem(clock);
                                p.sendMessage("??a????????????????????????");
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400 + 600, 1200));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : humans) {
                        if (tag4bill.hasPlayer(p)) {
                            int counter = 0;
                            for (ItemStack i : p.getInventory().all(Material.MUSHROOM_STEW).values()) {
                                counter += i.getAmount();
                            }
                            if (counter < 2) {
                                p.getInventory().addItem(mushroom_stew);
                                p.sendMessage("??a???????????????");
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400 + 1200, 1200));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : humans) {
                        if (tag4leaf.hasPlayer(p)) {
                            int spawnNo = random.nextInt(totalWeight);
                            int counter = 0;
                            for (int i = 0; i < gadgets.size(); i++) {
                                counter += gadgetWeights.get(i);
                                if (spawnNo < counter) {
                                    p.getInventory().addItem(gadgets.get(i));
                                    p.sendMessage("??a?????????????????????");
                                    break;
                                }
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400 + c.getInt("leaf.gadget-interval"), c.getInt("leaf.gadget-interval")));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : humans) {
                        if (tag4victoria.hasPlayer(p)) {
                            if (p.getInventory().contains(Material.MEDIUM_AMETHYST_BUD)) {
                                //p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 55, 0, false, false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 55, 0, false, false));
                            } else if (p.getInventory().contains(Material.LARGE_AMETHYST_BUD)) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 55, 1, false, false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 55, 0, false, false));
                                for (Entity e : p.getNearbyEntities(c.getInt("victoria.radiusX"),c.getInt("victoria.radiusY"),c.getInt("victoria.radiusZ"))) {
                                    if (players.contains(e)) {
                                        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 0, false, false));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400, 50));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    for (Player p : humans) {
                        if (tag4kelti.hasPlayer(p)) {
                            if (p.isSwimming()) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 0, false, false));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 50, 0, true, false));
                            }
                        }
                    }
                }, countDownSeconds * 20L + 400, 50));

                taskIds.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    long time = getTime(world);
                    if (time - startTime > gameTime) {
                        endGame("??e???????????????????????????", humans);
                        return;
                    }
                    if (humans.size() <= 0) {
                        endGame("??e???????????????????????????", devils);
                        return;
                    }
                    if (devils.size() <= 0) {
                        endGame("??e?????????????????????????????????", humans);
                        return;
                    }
                    tag4.getObjective("tag4").getScore("????????????").setScore(humans.size());
                    tag4.getObjective("tag4").getScore("????????????").setScore((int) ((gameTime - (time - startTime)) / 20));
                }, countDownSeconds * 20L + 400, 1));

            }
        };
    }

    public void clearChests() {
        for (Location l : locations) {
            ((Chest) (world.getBlockAt(l).getState())).getBlockInventory().clear();
        }
    }
}

