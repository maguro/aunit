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
package com.toolazydogs.aunit;

import com.toolazydogs.aunit.internal.ParserWrapper;
import com.toolazydogs.aunit.internal.TreeParserWrapper;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * A holder of static methods that assist in testing.
 * <p/>
 * These methods will usually feed results into methods of {@link Assert}.
 *
 * @see Assert
 */
public final class Work
{
    /**
     * Scans a set of characters and returns results which can be fed into
     * <code>Assert.assertToken(...)</code>.
     *
     * @param characters the characters to be scanned
     * @return results from the lexer that can be used by assert
     * @throws Exception if there is an error obtaining the lexer results
     */
    public static LexerResults scan(String characters) throws Exception
    {
        if (characters == null) throw new IllegalArgumentException("Characters cannot be null");
        if (AunitRuntime.getLexerFactory() == null) throw new IllegalStateException("Lexer factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(characters));
        return new LexerResults(lexer);
    }

    /**
     * Parse a set of characters and return an instance of {@link Tree}.
     * This tree can then be fed into <code>Assert.assertTree(...)</code>.
     * <p/>
     * This method will throw an exception if there was an error parsing.  It
     * will also throw {@link ParserException} if the parsing failed and the
     * test has indicated that the parser should fail on error; see
     * {@link ParserOption#failOnError(boolean)}.
     *
     * @param characters   the characters to be parsed
     * @param selectedRule the starting rule
     * @return the tree that results from a successful parse
     * @throws Exception if there is an error parsing the characters
     * @see #rule(String, com.toolazydogs.aunit.Work.ArgumentBuilder)
     */
    public static Tree parse(String characters, SelectedRule selectedRule) throws Exception
    {
        if (characters == null) throw new IllegalArgumentException("Characters cannot be null");
        if (selectedRule == null) throw new IllegalArgumentException("SelectedRule cannot be null, please use rule()");
        if (AunitRuntime.getLexerFactory() == null) throw new IllegalStateException("Lexer factory not set by configuration");
        if (AunitRuntime.getParserFactory() == null) throw new IllegalStateException("Parser factory not set by configuration");

        Lexer lexer = AunitRuntime.getLexerFactory().generate(new ANTLRStringStream(characters));
        Parser parser = AunitRuntime.getParserFactory().generate(new CommonTokenStream(lexer));

        RuleReturnScope rs = selectedRule.invoke(parser);

        ParserWrapper wrapper = (ParserWrapper)parser;
        if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
        {
            throw new ParserException(wrapper.getErrors());
        }

        return (Tree)rs.getTree();
    }

    /**
     * Select a rule to be used as a starting point in
     *
     * @param rule      the name of the rule to select
     * @return the selected rule
     * @throws Exception if no rule can be found
     * @see #parse(String, SelectedRule)
     */
    public static SelectedRule rule(String rule) throws Exception
    {
        return rule(rule, args());
    }

    /**
     * Select a rule to be used as a starting point in
     *
     * @param rule      the name of the rule to select
     * @param arguments arguments to be passed in the invocation of the rule when parsing
     * @return the selected rule
     * @throws Exception if no rule can be found
     * @see #parse(String, SelectedRule)
     * @see #args(Object...)
     */
    public static SelectedRule rule(String rule, ArgumentBuilder arguments) throws Exception
    {
        if (AunitRuntime.getParserFactory() == null) throw new IllegalStateException("Parser factory not set by configuration");

        for (Method method : collectMethods(AunitRuntime.getParserFactory().getParserClass()))
        {
            if (method.getName().equals(rule))
            {
                return new SelectedRule(method, arguments.get());
            }
        }

        throw new Exception("Rule " + rule + " not found");
    }


    /**
     * Select a tree rule be used as a starting point in tree walking
     *
     * @param rule      the name of the rule to select
     * @return the selected rule
     * @throws Exception if no rule can be found
     */
    public static SelectedRule withRule(String rule) throws Exception {
        return withRule(rule, args());
    }

    /**
     * Select a tree rule be used as a starting point in tree walking
     *
     * @param rule      the name of the rule to select
     * @param arguments arguments to be passed in the invocation of the rule when parsing
     * @return the selected rule
     * @throws Exception if no rule can be found
     */
    public static SelectedRule withRule(String rule, ArgumentBuilder arguments) throws Exception {
        if (AunitRuntime.getTreeParserFactory() == null) throw new IllegalStateException("TreeParser factory not set by configuration");

        for (Method method : collectMethods(AunitRuntime.getTreeParserFactory().getTreeParserClass()))
        {
            if (method.getName().equals(rule))
            {
                return new SelectedRule(method, arguments.get());
            }
        }

        throw new Exception("Rule " + rule + " not found");
    }

    /**
     * Parse a set of characters and return an instance of {@link Tree}.
     * This tree can then be fed into <code>Assert.assertTree(...)</code>.
     * <p/>
     * This method will throw an exception if there was an error parsing.  It
     * will also throw {@link ParserException} if the parsing failed and the
     * test has indicated that the parser should fail on error; see
     * {@link ParserOption#failOnError(boolean)}.
     *
     * @param walkerRule the starting walker rule
     * @param tree the tree to be walked
     * @return true on success
     * @throws Exception if there is an error during walking
     * @see #withRule(String, com.toolazydogs.aunit.Work.ArgumentBuilder)
     */
    public static boolean walk(SelectedRule walkerRule, TreeBuilder tree) throws Exception
    {
        if (walkerRule == null) throw new IllegalArgumentException("WalkerRule cannot be null, please use rule()");
        if (tree == null) throw new IllegalArgumentException("Tree cannot be null");
        if (AunitRuntime.getTreeParserFactory() == null) throw new IllegalStateException("Walker factory not set by configuration");

        TreeParser treeParser = AunitRuntime.getTreeParserFactory().generate(new CommonTreeNodeStream(tree.get()));

        RuleReturnScope rs = walkerRule.invoke(treeParser);

        TreeParserWrapper wrapper = (TreeParserWrapper)treeParser;
        if (wrapper.isFailOnError() && !wrapper.getErrors().isEmpty())
        {
            throw new ParserException(wrapper.getErrors());
        }

        return true;
    }


    public static ArgumentBuilder args(Object... arguments) {
        return new ArgumentBuilder(arguments);
    }

    public static TreeBuilder resultOf(Tree tree) {
        return new TreeBuilder(tree);
    }

    public static class ArgumentBuilder {
        private final Object[] arguments;
        ArgumentBuilder(Object... arguments){
            this.arguments = arguments;
        }

        Object[] get(){
            return this.arguments;
        }
    }

    public static class TreeBuilder {
        private final Tree tree;
        TreeBuilder(Tree tree){
            this.tree = tree;
        }

        Tree get(){
            return this.tree;
        }
    }

//    /**
//     * Generate an instance of the configured parser using a string as a
//     * source for input.
//     *
//     * @param src the string to use as input to the new parser instance.
//     * @param <T> the type of the parser
//     * @return an instance of the configured parser
//     * @throws Exception if there is an error generating the parser
//     */
//    public static <T> T generateParser(String src) throws Exception
//    {
//        ANTLRInputStream input = new ANTLRInputStream(new ByteArrayInputStream(src.getBytes()));
//        Lexer lexer = AunitRuntime.getLexerFactory().generate(input);
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        //noinspection unchecked
//        return (T)AunitRuntime.getParserFactory().generate(tokens);
//    }
//
//    /**
//     * Generate an instance of the configured parser using a file as a
//     * source for input.
//     *
//     * @param src the file to use as input to the new parser instance.
//     * @param <T> the type of the parser
//     * @return an instance of the configured parser
//     * @throws Exception if there is an error generating the parser
//     */
//    public static <T> T generateParser(File src) throws Exception
//    {
//        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(src));
//        Lexer lexer = AunitRuntime.getLexerFactory().generate(input);
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        //noinspection unchecked
//        return (T)AunitRuntime.getParserFactory().generate(tokens);
//    }

    /**
     * Recursively collect the set of declared methods of a class and
     * its super class.
     *
     * @param clazz the class whose methods to collect
     * @return the set of declared methods of a class
     */
    private static Set<Method> collectMethods(Class clazz)
    {
        if (clazz == null) return Collections.emptySet();

        Set<Method> s = new HashSet<Method>();

        s.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        s.addAll(collectMethods(clazz.getSuperclass()));

        return s;
    }

    private Work() { }
}
