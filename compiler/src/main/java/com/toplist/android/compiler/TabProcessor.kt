package com.toplist.android.compiler

import com.toplist.android.annotation.Tab
import com.toplist.android.annotation.model.TabInfo
import java.io.Writer
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.JavaFileObject
import kotlin.collections.HashSet

/**
 * @author yyf
 * @desc:
 *  暂不支持子 module 调用
 *  原因 子 Module ResourceId 不是 final 的
 *  可以采用 R2 的方案
 *  https://github.com/JakeWharton/butterknife/blob/master/butterknife-compiler/src/main/java/butterknife/compiler/ButterKnifeProcessor.java
 */
@SupportedAnnotationTypes(
    TabProcessor.ANNOTATION_TYPE_TAB
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class TabProcessor: AbstractProcessor() {

    companion object{
        const val ANNOTATION_TYPE_TAB = "com.toplist.android.annotation.Tab"
    }

    private val SUPPORTED_ANNOTATIONS: Set<Class<out Annotation>> = HashSet(Collections.singleton(Tab::class.java))

    private var elementUtils: Elements? = null

    private var messager: Messager? = null

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): Set<String>? {
        val supported: MutableSet<String> = HashSet()
        for (cls in SUPPORTED_ANNOTATIONS) {
            supported.add(cls.canonicalName)
        }
        return supported
    }

    @Synchronized
    override fun init(env: ProcessingEnvironment?) {
        super.init(env)
        elementUtils = env?.elementUtils
        messager = env?.messager
    }

    override fun process(set: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val dataSet = arrayListOf<TabInfo>()

        for (element in roundEnv.getElementsAnnotatedWith(Tab::class.java)) {
            if (element is TypeElement) {
                val annotation = element.getAnnotation(Tab::class.java)
                val titleRes = annotation.titleRes
                val iconRes = annotation.iconRes
                val position = annotation.position
                dataSet.add(TabInfo(titleRes, iconRes, position))
            }
        }

        dataSet.sortBy {
            it.position
        }

        if (dataSet.size > 0) {
            generateTabFile(dataSet)
        }

        return true
    }

    private fun generateTabFile(dataSet: ArrayList<TabInfo>) {
        val builder = StringBuilder()
        val packageName = "com.toplist.android.ui.tab"
        builder.append("package ").append(packageName).append(";\n\n")

        builder.append("import com.google.android.material.bottomnavigation.BottomNavigationView;\n")
        builder.append("import com.toplist.android.annotation.model.TabInfo;\n\n")
        builder.append("import java.util.ArrayList;\n")
        builder.append("import java.util.Arrays;\n\n")

        builder.append("/**\n")
        builder.append(" * @author yyf\n")
        builder.append(" */\n")

        builder.append("public final class ").append(generateClassName()).append(" {\n\n")
        builder.append("    public static ArrayList<TabInfo> tabs = new ArrayList<>(Arrays.asList(\n")
        for ((index, info) in dataSet.withIndex()) {
            builder.append("            new TabInfo(")
                .append(info.titleRes)
                .append(", ")
                .append(info.iconRes)
                .append(", ")
                .append(info.position)
            if (index == dataSet.size - 1) {
                builder.append(")\n")
            } else {
                builder.append("), \n")
            }
        }
        builder.append("    ));\n\n")

        builder.append("    public static void injectBottomNavigationViewByMenu(BottomNavigationView bottomNav) {\n")
        builder.append("        for (TabInfo info : tabs) {\n")
        builder.append("            bottomNav.getMenu().add(0, 0, info.position, info.titleRes).setIcon(info.iconRes);\n")
        builder.append("        }\n")
        builder.append("    }\n")

        builder.append("}\n")

        val `object`: JavaFileObject?
        try {
            `object` = processingEnv.filer.createSourceFile("$packageName.${generateClassName()}")
            val writer: Writer = `object`.openWriter()
            writer.write(builder.toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateClassName() = "TabInfoInject"

}

