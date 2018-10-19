package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

public class YScriptPairedBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(YScriptTypes.X_OPEN_BRACKET, YScriptTypes.X_CLOSE_BRACKET, false),
            new BracePair(YScriptTypes.X_OPEN_SQUARE_BRACKET, YScriptTypes.X_CLOSE_SQUARE_BRACKET, false)
    };

    @NotNull
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lBraceType, @Nullable IElementType t) {
        return false;
    }

    @Override
    public int getCodeConstructStart(PsiFile psiFile, int openingBraceOffset) {
        return openingBraceOffset;
    }

}
