package org.hotal.servershutdownplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;

public class ServerShutdownPlugin extends JavaPlugin {

    // 設定ファイルから取得する時間
    private int shutdownHour;
    private int shutdownMinute;

    @Override
    public void onEnable() {
        // config.ymlファイルがなければ生成する
        saveDefaultConfig();

        // config.ymlファイルから設定を読み込む
        shutdownHour = getConfig().getInt("shutdown.hour");
        shutdownMinute = getConfig().getInt("shutdown.minute");

        // 毎分システム時刻をチェックするタスクを開始する
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                checkShutdownTime();
            }
        }, 0L, 20L * 60L);
    }

    // プラグインが無効化されたときに呼ばれる
    @Override
    public void onDisable() {
        // プラグインが停止されるときに何かする場合はここに書く
    }

    // システム時刻が停止時間になっているかをチェックする
    private void checkShutdownTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        if (hour == shutdownHour && minute == shutdownMinute) {
            Bukkit.getServer().shutdown();
        }
    }
}
