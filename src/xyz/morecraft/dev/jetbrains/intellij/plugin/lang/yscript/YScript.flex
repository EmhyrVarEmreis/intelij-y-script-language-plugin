package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes.*;

%%

%public
%class YScriptLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

X_DOT="."
X_COMMA=","
X_COLON=":"
X_ASSIGN=\??=
X_SEMICOLON=";"
X_DOUBLE_COLON="::"
X_OPEN_BRACKET="("
X_CLOSE_BRACKET=")"
X_OPEN_SQUARE_BRACKET="["
X_CLOSE_SQUARE_BRACKET="]"
//X_QUOTATION_MARK=\"

V_STRING=\"(\\.|[^\"\\])*\"
V_NUMBER=-?\d+(\.\d+)?

IDENTIFIER=[a-zA-Z_]\w*

COMMENT_1="//"[^\r\n]*
COMMENT_2="/*"( [^*] | (\*+[^*/]) )*\*+\/

KEY_IMPORT="IMPORT"
KEY_PROGRAM="PROGRAM"
KEY_DEFINE="DEFINE"
KEY_EXTENSION="EXTENSION"
KEY_EXTERNAL="EXTERNAL"
KEY_VAR="VAR"
KEY_AS="AS"
KEY_BEGIN="BEGIN"
KEY_END="END"
KEY_RETURN="RETURN"
KEY_RETURNS="RETURNS"
KEY_ARRAY="ARRAY"
KEY_OF="OF"
KEY_IF="IF"
KEY_THEN="THEN"
KEY_ELSE="ELSE"
KEY_WITH="WITH"
KEY_DO="DO"
KEY_WHILE="WHILE"
KEY_CREATE="CREATE"
KEY_SORT="SORT"
KEY_INDEX="INDEX"
KEY_USING="USING"
KEY_DELETE="DELETE"
KEY_TRY="TRY"
KEY_CATCH="CATCH"
KEY_THROW="THROW"
KEY_NEW="NEW"
KEY_FOR="FOR"
KEY_TO="TO"
KEY_MERGE="MERGE"

KEY_AND="AND"
KEY_OR="OR"
KEY_NEGATION="NOT"

KEY_TRUE="TRUE"
KEY_FALSE="FALSE"

KEY_NULL="NULL"

F_KEY_IS_INSTANCE="ISINSTANCE"
F_KEY_IS_TYPE="ISTYPE"
F_KEY_SIZE_OF="SIZEOF"
F_KEY_IS_SET="ISSET"
F_KEY_COPY_OF="COPYOF"
F_KEY_IS_NULL="ISNULL"
F_KEY_TYPE="TYPE"

OP_LT="<"
OP_GT=">"
OP_LTE="<="
OP_GTE=">="
OP_PLUS="+"
OP_MINUS="-"
OP_MUL="*"
OP_DIVIDE="/"
OP_MODULO="%"
OP_EQIALS="=="
OP_NEQUALS="<>"

%state WAITING_VALUE

%%

<YYINITIAL> {X_DOT} { return X_DOT; }
<YYINITIAL> {X_COMMA} { return X_COMMA; }
<YYINITIAL> {X_COLON} { return X_COLON; }
<YYINITIAL> {X_ASSIGN} { return X_ASSIGN; }
<YYINITIAL> {X_SEMICOLON} { return X_SEMICOLON; }
<YYINITIAL> {X_DOUBLE_COLON} { return X_DOUBLE_COLON; }
<YYINITIAL> {X_OPEN_BRACKET} { return X_OPEN_BRACKET; }
<YYINITIAL> {X_CLOSE_BRACKET} { return X_CLOSE_BRACKET; }
<YYINITIAL> {X_OPEN_SQUARE_BRACKET} { return X_OPEN_SQUARE_BRACKET; }
<YYINITIAL> {X_CLOSE_SQUARE_BRACKET} { return X_CLOSE_SQUARE_BRACKET; }

<YYINITIAL> {V_STRING} { return V_STRING; }
<YYINITIAL> {V_NUMBER} { return V_NUMBER; }

<YYINITIAL> {KEY_IMPORT} { return KEY_IMPORT; }
<YYINITIAL> {KEY_PROGRAM} { return KEY_PROGRAM; }
<YYINITIAL> {KEY_DEFINE} { return KEY_DEFINE; }
<YYINITIAL> {KEY_EXTENSION} { return KEY_EXTENSION; }
<YYINITIAL> {KEY_EXTERNAL} { return KEY_EXTERNAL; }
<YYINITIAL> {KEY_VAR} { return KEY_VAR; }
<YYINITIAL> {KEY_AS} { return KEY_AS; }
<YYINITIAL> {KEY_BEGIN} { return KEY_BEGIN; }
<YYINITIAL> {KEY_END} { return KEY_END; }
<YYINITIAL> {KEY_RETURN} { return KEY_RETURN; }
<YYINITIAL> {KEY_RETURNS} { return KEY_RETURNS; }
<YYINITIAL> {KEY_ARRAY} { return KEY_ARRAY; }
<YYINITIAL> {KEY_OF} { return KEY_OF; }
<YYINITIAL> {KEY_IF} { return KEY_IF; }
<YYINITIAL> {KEY_THEN} { return KEY_THEN; }
<YYINITIAL> {KEY_ELSE} { return KEY_ELSE; }
<YYINITIAL> {KEY_WITH} { return KEY_WITH; }
<YYINITIAL> {KEY_DO} { return KEY_DO; }
<YYINITIAL> {KEY_WHILE} { return KEY_WHILE; }
<YYINITIAL> {KEY_CREATE} { return KEY_CREATE; }
<YYINITIAL> {KEY_SORT} { return KEY_SORT; }
<YYINITIAL> {KEY_INDEX} { return KEY_INDEX; }
<YYINITIAL> {KEY_USING} { return KEY_USING; }
<YYINITIAL> {KEY_DELETE} { return KEY_DELETE; }
<YYINITIAL> {KEY_TRY} { return KEY_TRY; }
<YYINITIAL> {KEY_CATCH} { return KEY_CATCH; }
<YYINITIAL> {KEY_THROW} { return KEY_THROW; }
<YYINITIAL> {KEY_NEW} { return KEY_NEW; }
<YYINITIAL> {KEY_FOR} { return KEY_FOR; }
<YYINITIAL> {KEY_TO} { return KEY_TO; }
<YYINITIAL> {KEY_MERGE} { return KEY_MERGE; }

<YYINITIAL> {KEY_AND} { return KEY_AND; }
<YYINITIAL> {KEY_OR} { return KEY_OR; }
<YYINITIAL> {KEY_NEGATION} { return KEY_NEGATION; }

<YYINITIAL> {KEY_TRUE} { return KEY_TRUE; }
<YYINITIAL> {KEY_FALSE} { return KEY_FALSE; }

<YYINITIAL> {KEY_NULL} { return KEY_NULL; }

<YYINITIAL> {F_KEY_IS_INSTANCE} { return F_KEY_IS_INSTANCE; }
<YYINITIAL> {F_KEY_IS_TYPE} { return F_KEY_IS_TYPE; }
<YYINITIAL> {F_KEY_SIZE_OF} { return F_KEY_SIZE_OF; }
<YYINITIAL> {F_KEY_IS_SET} { return F_KEY_IS_SET; }
<YYINITIAL> {F_KEY_COPY_OF} { return F_KEY_COPY_OF; }
<YYINITIAL> {F_KEY_IS_NULL} { return F_KEY_IS_NULL; }
<YYINITIAL> {F_KEY_TYPE} { return F_KEY_TYPE; }

<YYINITIAL> {OP_LT} { return OP_LT; }
<YYINITIAL> {OP_GT} { return OP_GT; }
<YYINITIAL> {OP_LTE} { return OP_LTE; }
<YYINITIAL> {OP_GTE} { return OP_GTE; }
<YYINITIAL> {OP_PLUS} { return OP_PLUS; }
<YYINITIAL> {OP_MINUS} { return OP_MINUS; }
<YYINITIAL> {OP_MUL} { return OP_MUL; }
<YYINITIAL> {OP_DIVIDE} { return OP_DIVIDE; }
<YYINITIAL> {OP_MODULO} { return OP_MODULO; }
<YYINITIAL> {OP_EQIALS} { return OP_EQIALS; }
<YYINITIAL> {OP_NEQUALS} { return OP_NEQUALS; }

<YYINITIAL> {WHITE_SPACE} { return WHITE_SPACE; }
<YYINITIAL> {COMMENT_1} { return COMMENT; }
<YYINITIAL> {COMMENT_2} { return COMMENT; }
<YYINITIAL> {EOL} { return EOL; }

<YYINITIAL> {IDENTIFIER} { return IDENTIFIER; }

<WAITING_VALUE> {EOL}({EOL}|{WHITE_SPACE})+ { return WHITE_SPACE; }
<WAITING_VALUE> {WHITE_SPACE}+ { return WHITE_SPACE; }

({EOL}|{WHITE_SPACE})+ { return WHITE_SPACE; }

[^] { return BAD_CHARACTER; }
