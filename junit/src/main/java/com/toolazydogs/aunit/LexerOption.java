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

import org.antlr.runtime.Lexer;
import org.junit.runners.model.Statement;

import com.toolazydogs.aunit.internal.LexerFactory;


/**
 * Lexer options to be used for configuring tests.
 */
public class LexerOption<L extends Lexer> implements Option
{
    private final Class<L> lexerClass;
    private final LexerSetup<L> lexerSetup;
    private boolean failOnError = true;

    LexerOption(Class<L> lexerClass, LexerSetup<L> lexerSetup)
    {
        assert lexerClass != null;

        this.lexerClass = lexerClass;
        this.lexerSetup = lexerSetup;
    }

    Class<L> getLexerClass()
    {
        return lexerClass;
    }

    /**
     * Determine if the lexer will throw an exception if there is an error
     * during a lexical analysis.
     *
     * @return <code>true</code> if the lexer will throw an exception if there
     *         is an error during a lexical analysis, <code>false</code>
     *         otherwise
     */
    boolean isFailOnError()
    {
        return failOnError;
    }

    /**
     * Direct that a lexer is to throw an exception if there is an error
     * during a lexical analysis.
     *
     * @return this lexer option for more configuration
     */
    public LexerOption failOnError()
    {
        failOnError = true;
        return this;
    }

    /**
     * Direct that a lexer is to throw an exception if there is an error
     * during a lexical analysis.
     *
     * @param fail <code>true</code> if the lexer is to throw an exception if
     *             there is an error during a lexical analysis,
     *             <code>false</code> otherwise
     * @return this lexer option for more configuration
     */
    public LexerOption failOnError(boolean fail)
    {
        failOnError = fail;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public Statement generateSetupStatement()
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                AunitRuntime.setLexerFactory(new LexerFactory(lexerClass, failOnError, lexerSetup));
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    public Statement generateTeardownStatement()
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                AunitRuntime.setLexerFactory(null);
            }
        };
    }
}
