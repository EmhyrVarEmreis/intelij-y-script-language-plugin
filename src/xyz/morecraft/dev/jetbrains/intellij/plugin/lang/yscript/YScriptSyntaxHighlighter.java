package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

@SuppressWarnings("WeakerAccess")
public class YScriptSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey SEPARATOR =
            createTextAttributesKey("YSCRIPT_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEY =
            createTextAttributesKey("YSCRIPT_KEY", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE =
            createTextAttributesKey("YSCRIPT_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("YSCRIPT_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey SEMICOLON =
            createTextAttributesKey("YSCRIPT_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA =
            createTextAttributesKey("YSCRIPT_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENTHESES =
            createTextAttributesKey("YSCRIPT_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("YSCRIPT_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("YSCRIPT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{SEMICOLON};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new YScriptLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(YScriptTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_IMPORT)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_PROGRAM)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_DEFINE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_EXTENSION)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_EXTERNAL)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_VAR)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_AS)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_BEGIN)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_END)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_RETURN)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_RETURNS)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_ARRAY)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_OF)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_IF)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_THEN)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_ELSE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_WITH)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_DO)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_WHILE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_CREATE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_SORT)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_INDEX)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_USING)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_DELETE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_TRY)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_CATCH)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_THROW)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_NEW)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_FOR)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_TO)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_MERGE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_AND)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_OR)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_NEGATION)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_TRUE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.KEY_FALSE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.F_KEY_IS_INSTANCE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.F_KEY_IS_TYPE)) {
            return KEY_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_DOT)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_COMMA)) {
            return COMMA_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_COLON)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_ASSIGN)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_SEMICOLON)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_DOUBLE_COLON)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_OPEN_BRACKET)) {
            return PARENTHESES_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_CLOSE_BRACKET)) {
            return PARENTHESES_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_OPEN_SQUARE_BRACKET)) {
            return BRACKETS_KEYS;
        } else if (tokenType.equals(YScriptTypes.X_CLOSE_SQUARE_BRACKET)) {
            return BRACKETS_KEYS;
        } else if (tokenType.equals(YScriptTypes.V_STRING)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(YScriptTypes.V_NUMBER)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }

}
