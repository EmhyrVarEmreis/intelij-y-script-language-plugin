package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util;

import com.intellij.psi.tree.TokenSet;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

public class TokenSets {

    public static TokenSet BLOCK_SET = TokenSet.create(
            YScriptTypes.BLOCK,
            YScriptTypes.BLOCK_DEFINE
    );

    public static TokenSet RELEVANT_SET = TokenSet.create(
            YScriptTypes.FILE_CONTENT,
            YScriptTypes.PROGRAM,
            YScriptTypes.IMPORT,
            YScriptTypes.DEFINE
    );

}
