package com.scaffus.survivors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SurvivorsUtils {

    private final Survivors plugin;
    public final String player_not_found;
    public final String prefix;
    public final String error;
    public final String no_perm;
    public final String test;
    public final String only_player_can_exec;

    // spawn
    public final String spawn_usage;
    public final String spawn_tped;
    public final String spawn_set;

    // back
    public final String back_back;
    public final String back_noback;

    // msg
    public final String msg_msg_usage;
    public final String msg_r_usage;
    public final String msg_view_sender;
    public final String msg_view_receiver;

    // kill
    public final String kill_usage;
    public final String kill_kill;

    // mute
    public final String mute_usage;
    public final String mute_mute;
    public final String mute_muted;
    public final String mute_unmute;
    public final String mute_wasnotmuted;
    public final String mute_unmuted;
    public final String mute_alreadymuted;

    // chat
    public final String chat_message_format;
    public final String chat_player_join;
    public final String chat_player_quit;

    // database
    public final String database_host;
    public final String database_database;
    public final String database_port;
    public final String database_username;
    public final String database_password;

    // tpa
    public final String tpa_usage;
    public final String tpa_view_sender;
    public final String tpa_view_target;
    public final String tpa_deny_usage;
    public final String tpa_deny_sender;
    public final String tpa_deny_target;
    public final String tpa_accept_usage;
    public final String tpa_accept_sender;
    public final String tpa_accept_target;
    public final String tpa_accept_norequest;

    // money
    public final String money_money_usage;
    public final String money_money_message;
    public final String money_pay_usage;
    public final String money_pay_view_sender;
    public final String money_pay_view_target;
    public final String money_pay_notenoughmoney;
    public final String money_pay_needpositiveammount;
    public final String money_add_usage;
    public final String money_add_message;

    // warp
    public final String setwarp_usage;
    public final String setwarp_set;

    public final String warp_usage;
    public final String warp_dontexist;
    public final String warp_tped;

    // invsee
    public final String invsee_usage;

    // rank
    public final String rank_usage;
    public final String rank_set_message;
    public final String rank_get_message;

    public SurvivorsUtils(Survivors plugin) {
        this.plugin = plugin;
        this.test = plugin.getConfig().getString("test");
        // database
        this.database_host = getFromConfig("Database.host");
        this.database_database = getFromConfig("Database.database");
        this.database_port = getFromConfig("Database.port");
        this.database_username = getFromConfig("Database.username");
        this.database_password = getFromConfig("Database.password");

        // general
        this.prefix = getFromConfig("Prefix").replace("%&", "§");
        this.error = getFromConfigAndFormat("Error");
        this.no_perm = getFromConfigAndFormat("NoPerm");
        this.only_player_can_exec = ChatColor.RED + "Seul un joueur peut executer cette commande.";
        this.player_not_found = getFromConfigAndFormat("PlayerNotFound");

        // chat
        this.chat_message_format = getFromConfigAndFormat("Chat.message");
        this.chat_player_quit = getFromConfigAndFormat("Chat.player_quit");
        this.chat_player_join = getFromConfigAndFormat("Chat.player_join");

        // /spawn messages
        this.spawn_usage = formatMessage("%prefix %&a/spawn");
        this.spawn_tped = getFromConfigAndFormat("Spawn.tped");
        this.spawn_set = getFromConfigAndFormat("Spawn.set");

        // /back messages
        this.back_back = getFromConfigAndFormat("Back.back");
        this.back_noback = getFromConfigAndFormat("Back.no_back");

        // /msg & /r messages
        this.msg_msg_usage = formatMessage("%prefix %&a/msg %&e<joueur> %&4<message>");
        this.msg_r_usage = formatMessage("%prefix %&a/r %&4<message>");
        this.msg_view_sender = getFromConfigAndFormat("Message.sender_view");
        this.msg_view_receiver = getFromConfigAndFormat("Message.receiver_view");

        // /kill messages
        this.kill_usage = formatMessage("%prefix %&a/kill %&e<joueur>");
        this.kill_kill = getFromConfigAndFormat("Kill.kill");

        // /mute messages
        this.mute_usage = formatMessage("%prefix %&a/mute %&e<player>");
        this.mute_mute = getFromConfigAndFormat("Mute.mute");
        this.mute_unmute = getFromConfigAndFormat("Mute.unmute");
        this.mute_muted = getFromConfigAndFormat("Mute.muted");
        this.mute_wasnotmuted = getFromConfigAndFormat("Mute.wasnotmuted");
        this.mute_unmuted = getFromConfigAndFormat("Mute.unmuted");
        this.mute_alreadymuted = getFromConfigAndFormat("Mute.alreadymuted");

        // /tpa
        this.tpa_usage = formatMessage( "%prefix %&a/tpa %&e<joueur>");
        this.tpa_view_sender = getFromConfigAndFormat("Tpa.tpa.sender");
        this.tpa_view_target = getFromConfigAndFormat("Tpa.tpa.target");

        this.tpa_accept_usage = formatMessage("%prefix %&a/tpaccept");
        this.tpa_accept_sender = getFromConfigAndFormat("Tpa.accept.sender");
        this.tpa_accept_target = getFromConfigAndFormat("Tpa.accept.target");
        this.tpa_accept_norequest = getFromConfigAndFormat("Tpa.accept.norequest");

        this.tpa_deny_usage = formatMessage("%prefix %&a/tpadeny");
        this.tpa_deny_sender = getFromConfigAndFormat("Tpa.deny.sender");
        this.tpa_deny_target = getFromConfigAndFormat("Tpa.deny.target");

        // /money
        this.money_money_usage = formatMessage("%prefix %&a/money");
        this.money_money_message = getFromConfigAndFormat("Money.money.message");

        this.money_pay_usage = formatMessage("%prefix %&a/pay %&e<joueur> %&b<montant>");
        this.money_pay_view_sender = getFromConfigAndFormat("Money.pay.sender");
        this.money_pay_view_target = getFromConfigAndFormat("Money.pay.target");
        this.money_pay_notenoughmoney = getFromConfigAndFormat("Money.pay.notenougmoney");
        this.money_pay_needpositiveammount = getFromConfigAndFormat("Money.pay.needpositiveammount");

        this.money_add_usage = formatMessage("%prefix %&a/money %&2add %&e<joueur> %&b<montant>");
        this.money_add_message = getFromConfigAndFormat("Money.add.message");

        // /setwarp
        this.setwarp_usage = formatMessage("%prefix %&a/setwarp %&3<nom>");
        this.setwarp_set = getFromConfigAndFormat("Warp.set.set");

        // /warp
        this.warp_usage = formatMessage("%prefix %&a/warp %&3<nom>");
        this.warp_tped = getFromConfigAndFormat("Warp.warp.tped");
        this.warp_dontexist = getFromConfigAndFormat("Warp.warp.dontexist");

        // /invsee
        this.invsee_usage = formatMessage("%prefix %&a/invsee %&ejoueur");

        // /rank
        this.rank_usage = formatMessage("%prefix %&a/rank %&b<set|get> %&e<joueur>");
        this.rank_set_message = getFromConfigAndFormat("Rank.set");
        this.rank_get_message = getFromConfigAndFormat("Rank.get");
    }

    public String formatMessage(String str) {
        try {
            str = str.replace("%&", "§").replace("%prefix", this.prefix);
        } catch (NullPointerException e) {
//            plugin.getLogger().info("nullpointexecption formatMessage");
        }
        return str;
    }

    public String getFromConfig(String path) {
        String str;
        try {
            str = plugin.getConfig().getString(path);
        } catch (Exception e) {
            plugin.getLogger().info(ChatColor.RED + e.toString());
            str = "failed to load from config";
        }
        return str;
    }

    public String getFromConfigAndFormat(String path) {
        String message;
        try {
            //message = plugin.getConfig().getString(path).replace("%&", "§").replace("%prefix", this.prefix);
            message = getFromConfig(path);
            message = formatMessage(message);
        } catch (Exception e) {
            plugin.getLogger().info(ChatColor.RED + e.toString());
            message = "failed to load or to format";
        }
        return message;
    }


}
