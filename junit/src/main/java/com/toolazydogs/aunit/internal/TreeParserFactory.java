/**
 *
 * Copyright 2010-2011 (C) The original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.toolazydogs.aunit.internal;

import java.lang.reflect.Constructor;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.toolazydogs.aunit.TreeParserSetup;


/**
 *
 */
public class TreeParserFactory<T extends TreeParser> implements Opcodes
{
    private final Class<T> treeParserClass;
    private final TreeParserSetup<T> treeParserSetup;
    private final boolean failOnError;
    private Class<T> wrapperClass;

    public TreeParserFactory(Class<T> treeParserClass, boolean failOnError, TreeParserSetup<T> treeParserSetup)
    {
        assert treeParserClass != null;

        this.treeParserClass = treeParserClass;
        this.failOnError = failOnError;
        this.treeParserSetup = treeParserSetup;
    }

    public Class getTreeParserClass()
    {
        return treeParserClass;
    }

    public TreeParser generate(TreeNodeStream input) throws Exception
    {
        Class<T> wc = getWrapperClass();
        Constructor c = wc.getConstructor(TreeNodeStream.class);
        TreeParserWrapper wrapper = (TreeParserWrapper)c.newInstance(input);

        if (treeParserSetup != null)
        {
            treeParserSetup.config((T)wrapper);
        }

        wrapper.setFailOnError(failOnError);

        return (TreeParser)wrapper;
    }

    public TreeParser generate(TreeNodeStream input, RecognizerSharedState state) throws Exception
    {
        Class<T> wc = getWrapperClass();
        Constructor c = wc.getConstructor(TreeNodeStream.class, RecognizerSharedState.class);
        TreeParserWrapper wrapper = (TreeParserWrapper)c.newInstance(input, state);

        if (treeParserSetup != null)
        {
            treeParserSetup.config((T)wrapper);
        }

        wrapper.setFailOnError(failOnError);

        return (TreeParser)wrapper;
    }

    private Class<T> getWrapperClass()
    {
        if (wrapperClass == null) wrapperClass = loadClass();
        return wrapperClass;
    }

    private Class<T> loadClass()
    {
        final String parent = treeParserClass.getName().replace('.', '/');
        final String name = "com/toolazydogs/aunit/asm/" + parent;

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, name, null, parent, new String[]{"com/toolazydogs/aunit/internal/TreeParserWrapper"});

        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "errors", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE, "failOnError", "Z", null, null);
            fv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/antlr/runtime/tree/TreeNodeStream;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESPECIAL, parent, "<init>", "(Lorg/antlr/runtime/tree/TreeNodeStream;)V");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
            mv.visitFieldInsn(PUTFIELD, name, "errors", "Ljava/util/List;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ICONST_1);
            mv.visitFieldInsn(PUTFIELD, name, "failOnError", "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/antlr/runtime/tree/TreeNodeStream;Lorg/antlr/runtime/RecognizerSharedState;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKESPECIAL, parent, "<init>", "(Lorg/antlr/runtime/tree/TreeNodeStream;Lorg/antlr/runtime/RecognizerSharedState;)V");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
            mv.visitFieldInsn(PUTFIELD, name, "errors", "Ljava/util/List;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ICONST_1);
            mv.visitFieldInsn(PUTFIELD, name, "failOnError", "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(3, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "emitErrorMessage", "(Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, name, "errors", "Ljava/util/List;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z");
            mv.visitInsn(POP);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getErrors", "()Ljava/util/List;", "()Ljava/util/List<Ljava/lang/String;>;", null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, name, "errors", "Ljava/util/List;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "isFailOnError", "()Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, name, "failOnError", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "setFailOnError", "(Z)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitFieldInsn(PUTFIELD, name, "failOnError", "Z");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        byte[] b = cw.toByteArray();

        AunitClassloader ac = new AunitClassloader(treeParserClass.getClassLoader());
        return ac.defineClass(name.replace('/', '.'), b);
    }
}
