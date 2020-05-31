package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util;

import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStruct;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStructBundle;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;

import java.util.*;

public final class Std {

    public final static String STD_DEFAULT = "std::default";
    public final static String STD_QS = "std::qs";
    public final static String STD_DS = "std::ds";
    public final static String STD_FMT = "std::fmt";
    public final static String STD_ADHOC = "std::adhoc";
    public final static String STD_DICT = "std::dict";
    public final static String STD_STORAGE = "std::storage";
    public final static String STD_CACHE = "std::cache";
    public final static String STD_TEMPLATE = "std::template";

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
        BUILT_IN_PROGRAMS.put("std::applyTemplate", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "dict::applyTemplate",
                        STD_TEMPLATE,
                        new ProgramArgument[]{
                                new ProgramArgument("template", "String", null),
                                new ProgramArgument("data", "AnyType", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("dict::getValue", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "dict::getValue",
                        STD_DICT,
                        new ProgramArgument[]{
                                new ProgramArgument("dictionaryName", "String", null),
                                new ProgramArgument("keyName", "String", null),
                                new ProgramArgument("timeout", "Integer", null)
                        },
                        new VariableType("AnyType", null)
                ),
                new YScriptProgramStruct(
                        "dict::getValue",
                        STD_DICT,
                        new ProgramArgument[]{
                                new ProgramArgument("dictionaryName", "String", null),
                                new ProgramArgument("keyName", "String", null)
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("dict::createDictionary", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "dict::createDictionary",
                        STD_DICT,
                        new ProgramArgument[]{
                                new ProgramArgument("dictionary", "Dictionary", "http://www.invenireaude.org/qsystem/workers/dict")
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("dict::newItem", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "dict::newItem",
                        STD_DICT,
                        new ProgramArgument[]{
                                new ProgramArgument("key", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        new VariableType("Item", "http://www.invenireaude.org/qsystem/workers/dict")
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::createBucket", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::createBucket",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::fetchBucket", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::fetchBucket",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::deleteBucket", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::deleteBucket",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::releaseBucket", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::releaseBucket",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::getItem", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::getItem",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null)
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("cache::createOrUpdate", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "cache::createOrUpdate",
                        STD_CACHE,
                        new ProgramArgument[]{
                                new ProgramArgument("cache", "String", null),
                                new ProgramArgument("key", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getLocalValue", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getLocalValue",
                        STD_STORAGE,
                        new ProgramArgument[]{
                                new ProgramArgument("key", "String", null)
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getGlobalValue", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getGlobalValue",
                        STD_STORAGE,
                        new ProgramArgument[]{
                                new ProgramArgument("key", "String", null)
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::setLocalValue", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::setLocalValue",
                        STD_STORAGE,
                        new ProgramArgument[]{
                                new ProgramArgument("key", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("std::setGlobalValue", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::setGlobalValue",
                        STD_STORAGE,
                        new ProgramArgument[]{
                                new ProgramArgument("key", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("fmt::serialize", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "fmt::serialize",
                        STD_FMT,
                        new ProgramArgument[]{
                                new ProgramArgument("format", "String", null),
                                new ProgramArgument("data", "AnyType", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("fmt::parse", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "fmt::parse",
                        STD_FMT,
                        new ProgramArgument[]{
                                new ProgramArgument("format", "String", null),
                                new ProgramArgument("payload", "String", null)
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::send", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::send",
                        STD_QS,
                        new ProgramArgument[]{
                                new ProgramArgument("name", "String", null),
                                new ProgramArgument("ctx", "Context", "http://www.invenireaude.org/qsystem/workers"),
                                new ProgramArgument("data", "AnyType", null),
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("std::receive", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::receive",
                        STD_QS,
                        new ProgramArgument[]{
                                new ProgramArgument("name", "String", null),
                                new ProgramArgument("ctx", "Context", "http://www.invenireaude.org/qsystem/workers")
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::execute", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::execute",
                        STD_ADHOC,
                        new ProgramArgument[]{
                                new ProgramArgument("program", "String", null),
                                new ProgramArgument("source", "String", null),
                                new ProgramArgument("argument", "AnyType", null),
                        },
                        new VariableType("AnyType", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("ds::commitAll", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "ds::commitAll",
                        STD_DS,
                        new ProgramArgument[]{},
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("ds::rollbackAll", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "ds::rollbackAll",
                        STD_DS,
                        new ProgramArgument[]{},
                        null
                )
        )));
    }

}
