package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util;

import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStruct;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStructBundle;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;

import java.util.*;

public final class Std {

    public final static String STD_DEFAULT = "std::default";

    public final static Set<String> BUILT_IN_TYPES;
    public final static Map<String, YScriptProgramStructBundle> BUILT_IN_PROGRAMS;

    static {
        BUILT_IN_TYPES = new HashSet<>();
        BUILT_IN_TYPES.add("Integer");
        BUILT_IN_TYPES.add("String");
        BUILT_IN_TYPES.add("DateTime");
        BUILT_IN_TYPES.add("Time");
        BUILT_IN_TYPES.add("Date");
        BUILT_IN_TYPES.add("Raw");
        BUILT_IN_TYPES.add("Float");
        BUILT_IN_TYPES.add("Boolean");
        BUILT_IN_TYPES.add("AnyType");
        BUILT_IN_PROGRAMS = new HashMap<>();
        BUILT_IN_PROGRAMS.put("std::strlen", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::strlen",
                        STD_DEFAULT,
                        new ProgramArgument[]{new ProgramArgument("strArgument", "String", null)},
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getTime", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getTime",
                        STD_DEFAULT,
                        new ProgramArgument[]{},
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::modifydate", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::modifydate",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("tTime", "DateTime", null),
                                new ProgramArgument("iDeltaYears", "Integer", null),
                                new ProgramArgument("iDeltaMonths", "Integer", null),
                                new ProgramArgument("iDeltaDays", "Integer", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::modifytime", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::modifytime",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("tTime", "DateTime", null),
                                new ProgramArgument("iDeltaHours", "Integer", null),
                                new ProgramArgument("iDeltaMinutes", "Integer", null),
                                new ProgramArgument("iDeltaSeconds", "Integer", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::save", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::save",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("file", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2ts", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2ts",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strTs", "String", null),
                                new ProgramArgument("strFmt", "String", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::replaceRegExp", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::replaceRegExp",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("strPattern", "String", null),
                                new ProgramArgument("strNewText", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::replace", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::replace",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("strPattern", "String", null),
                                new ProgramArgument("strNewText", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2lower", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2lower",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2upper", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2upper",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getHour", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getHour",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getMinute", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getMinute",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getSecond", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getSecond",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getDay", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getDay",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getDay",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getMonth", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getMonth",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getMonth",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getYear", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getYear",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getYear",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::substring", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::substring",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("iStart", "Integer", null),
                                new ProgramArgument("iLength", "Integer", null)
                        },
                        new VariableType("String", null)
                ),
                new YScriptProgramStruct(
                        "std::substring",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("iStart", "Integer", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::find", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::find",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("strPattern", "String", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::find",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("strPattern", "String", null),
                                new ProgramArgument("iStart", "Integer", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::randString", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::randString",
                        STD_DEFAULT,
                        new ProgramArgument[]{
                                new ProgramArgument("length", "Integer", null)
                        },
                        new VariableType("String", null)
                )
        )));
    }

}
