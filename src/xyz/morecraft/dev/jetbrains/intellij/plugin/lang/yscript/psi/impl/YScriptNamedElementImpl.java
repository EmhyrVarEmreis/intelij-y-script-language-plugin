package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptNamedElement;

public abstract class YScriptNamedElementImpl extends ASTWrapperPsiElement implements YScriptNamedElement {

    public YScriptNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

}
