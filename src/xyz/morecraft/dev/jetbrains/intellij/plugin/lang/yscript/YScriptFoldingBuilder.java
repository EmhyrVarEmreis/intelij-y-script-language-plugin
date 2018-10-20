package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.TokenSets;

import java.util.ArrayList;
import java.util.List;

public class YScriptFoldingBuilder implements FoldingBuilder {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        appendDescriptors(node, descriptors);
        return descriptors.toArray(new FoldingDescriptor[0]);
    }

    private void appendDescriptors(final ASTNode node, final List<FoldingDescriptor> descriptors) {
        if (TokenSets.BLOCK_SET.contains(node.getElementType())) {
            descriptors.add(new FoldingDescriptor(node, node.getPsi().getTextRange()));
        }
        ASTNode child = node.getFirstChildNode();
        while (child != null) {
            appendDescriptors(child, descriptors);
            child = child.getTreeNext();
        }
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode astNode) {
        return null;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return false;
    }

}
