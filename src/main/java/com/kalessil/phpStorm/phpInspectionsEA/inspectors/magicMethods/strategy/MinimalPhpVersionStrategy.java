package com.kalessil.phpStorm.phpInspectionsEA.inspectors.magicMethods.strategy;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.config.PhpLanguageLevel;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.jetbrains.php.lang.psi.elements.Method;
import com.kalessil.phpStorm.phpInspectionsEA.utils.NamedElementUtil;

public class MinimalPhpVersionStrategy {
    private static final String strProblemDescription = "%m% is introduced only in version %v%, hence it's unused.";

    static public void apply(final Method method, final ProblemsHolder holder, final PhpLanguageLevel neededVersion) {
        final PsiElement nameNode = NamedElementUtil.getNameIdentifier(method);
        if (nameNode != null) {
            final PhpLanguageLevel php = PhpProjectConfigurationFacade.getInstance(holder.getProject()).getLanguageLevel();
            if (php.compareTo(neededVersion) < 0) { // at least required version
                final String message = strProblemDescription
                        .replace("%m%", method.getName())
                        .replace("%v%", neededVersion.getVersionString());
                holder.registerProblem(nameNode, message, ProblemHighlightType.LIKE_UNUSED_SYMBOL);
            }
        }
    }
}
