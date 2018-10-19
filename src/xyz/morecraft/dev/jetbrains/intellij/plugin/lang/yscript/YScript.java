package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.Language;

public class YScript extends Language {

    public static final YScript INSTANCE = new YScript();

    private YScript() {
        super("YScript");
    }

}
