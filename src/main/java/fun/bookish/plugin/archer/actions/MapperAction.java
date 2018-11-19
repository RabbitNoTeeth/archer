package fun.bookish.plugin.archer.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import freemarker.template.Template;
import fun.bookish.plugin.archer.config.FreeMarkerCfg;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class MapperAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {

        try {
            Editor editor = event.getData(CommonDataKeys.EDITOR);
            PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
            PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
            PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            Template template = FreeMarkerCfg.getTemplate("MapperInterface.ftl");
            Writer out = new OutputStreamWriter(System.out);
            Map<String, String> data = new HashMap<>();
            data.put("modelName", psiClass.getName());
            data.put("package", "aaa");
            template.process(data, out);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
