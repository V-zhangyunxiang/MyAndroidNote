package com.example.apt_processor;

import com.example.apt_annotaition.ViewHolder;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
// AutoService 则是固定的写法，加个注解即可
// 通过 auto-service 中的 @AutoService 可以自动生成 AutoService 注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)
public class ProcessorClass extends AbstractProcessor {
  // Messager 用来报告错误，警告和其他提示信息
  private Messager mMessager;
  // 操作 Element 工具类 (类、函数、属性都是 Element)
  private Elements mElementUtils;
  // 文件生成器类/资源，Filter 用来创建新的源文件，class 文件以及辅助文件
  private Filer mFiler;

  /**
   * 主要用于一些初始化的操作，通过该方法的参数 ProcessingEnvironment 可以获取一些列有用的工具类
   *
   * @param processingEnvironment 当前或是之前的运行环境,可以通过该对象查找找到的注解。
   */
  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    mMessager = processingEnvironment.getMessager();
    mFiler = processingEnvironment.getFiler();
    mElementUtils = processingEnvironment.getElementUtils();
    // 通过ProcessingEnvironment 去获取 build.gradle 传过来的参数
    String content = processingEnvironment.getOptions().get("content");
    mMessager.printMessage(Diagnostic.Kind.NOTE, content);
  }

  /**
   * 相当于 main 函数，开始处理注解处理器的核心方法，处理具体的注解，生成 Java 文件
   *
   * @param set 使用了支持处理注解的节点集合（类上面写了注解）
   * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找找到的注解
   * @return true 表示后续处理器不会再处理（已经处理完成）
   */
  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    if (set.isEmpty()) return false;
    // 获取所有带 ViewHolder 注解的类节点
    Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ViewHolder.class);
    // 遍历所有类节点
    for (Element element : elements) {
      // 通过类节点获取包节点（全路径：com.example.xxx）
      String packageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
      // 获取简单类名
      String className = element.getSimpleName().toString();
      mMessager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + className);
      // 最终想生成的类文件名
      String finalClassName = className + "$ViewHolder";
      // 利用javapoet
      javapoetProcess(mFiler, element, packageName, finalClassName, className);
    }
    return true;
  }

  /**
   * javapoet 构建工具
   *
   * @param filer
   * @param element
   * @param packageName
   * @param finalClassName
   * @param className
   */
  private void javapoetProcess(
      Filer filer, Element element, String packageName, String finalClassName, String className) {
    try {
      // 获取类之上 ViewHolder 注解的 path 值
      ViewHolder viewHolder = element.getAnnotation(ViewHolder.class);
      // 构建方法
      MethodSpec method =
          MethodSpec.methodBuilder("findTargetClass") // 方法名
              // .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
              .returns(Class.class) // 返回值Class<?>
              .addParameter(String.class, "path") // 参数(String path)
              .addStatement(
                  "return path.equals($S) ? $T.class : null",
                  viewHolder.path(),
                  ClassName.get((TypeElement) element))
              .build(); // 构建
      // 构建类
      TypeSpec type =
          TypeSpec.classBuilder(finalClassName)
              // .addModifiers(Modifier.PUBLIC)
              // .addMethod(method) // 添加方法到类中
              .build(); // 构建

      // 在指定的包名下, 生成 Java 类文件
      JavaFile.builder(packageName, type).build().writeTo(filer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 指明有哪些注解需要被扫描到, 返回注解的全路径（包名+类名） */
  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(ViewHolder.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  protected synchronized boolean isInitialized() {
    return super.isInitialized();
  }

  /*
   为什么两个模块一定要是 Java Library 而不是 Android Library？
   如果创建 Android Library 模块会发现不能找到 AbstractProcessor 这个类，这是因为 Android 平台是基于 OpenJDK 的，而 OpenJDK
   中不包含 APT 的相关代码。因此，在使用 APT 时，必须在 Java Library 中进行。
  */

}
