package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class YScriptLexerAdapter extends FlexAdapter {

    public YScriptLexerAdapter() {
        super(new _YScriptLexer((Reader) null));
    }

}

