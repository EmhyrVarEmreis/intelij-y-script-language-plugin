package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file;

import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStruct;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStructBundle;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStructBundleExternalizer;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFile;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.Objects;

public class YScriptProgramNameFBIdx extends FileBasedIndexExtension<String, YScriptProgramStructBundle> {

    public static final ID<String, YScriptProgramStructBundle> KEY = ID.create("yScript.idx.fb.programName");

    private final DataExternalizer<YScriptProgramStructBundle> valueExternalizer = new YScriptProgramStructBundleExternalizer();

    @NotNull
    @Override
    public ID<String, YScriptProgramStructBundle> getName() {
        return KEY;
    }

    @NotNull
    @Override
    public DataIndexer<String, YScriptProgramStructBundle, FileContent> getIndexer() {
        return fileContent -> {
            final THashMap<String, YScriptProgramStructBundle> map = new THashMap<>();
            final YScriptFile yScriptFile = (YScriptFile) fileContent.getPsiFile();
            final YScriptFileContent yScriptFileContent = yScriptFile.findChildByClass(YScriptFileContent.class);
            if (Objects.isNull(yScriptFileContent)) {
                return map;
            }
            for (YScriptProgram yScriptProgram : yScriptFileContent.getProgramList()) {
                if(Objects.isNull(YScriptUtil.getPackageName(yScriptProgram.getContainingFile()))){
                    continue;
                }
                final YScriptProgramStruct yScriptProgramStruct = YScriptProgramStruct.create(yScriptProgram);
                final String key = yScriptProgramStruct.getName();
                final YScriptProgramStructBundle programStructBundle = map.get(key);
                if (Objects.isNull(programStructBundle)) {
                    final YScriptProgramStructBundle newProgramStructBundle = new YScriptProgramStructBundle();
                    newProgramStructBundle.add(yScriptProgramStruct);
                    map.put(key, newProgramStructBundle);
                } else {
                    programStructBundle.add(yScriptProgramStruct);
                }
            }
            return map;
        };
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @NotNull
    @Override
    public DataExternalizer<YScriptProgramStructBundle> getValueExternalizer() {
        return valueExternalizer;
    }

    @Override
    public int getVersion() {
        return 5;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return new DefaultFileTypeSpecificInputFilter(YScriptFileType.INSTANCE);
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

}
