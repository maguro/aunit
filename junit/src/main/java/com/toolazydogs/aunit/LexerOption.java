/**
 *
 * Copyright 2010 (C) The original author or authors
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
 * @version $Revision: $ $Date: $
 */
public class LexerOption implements Option
{
    private final Class<? extends Lexer> lexerClass;
    private boolean failOnError = false;

    public LexerOption(Class<? extends Lexer> lexerClass)
    {
        this.lexerClass = lexerClass;
    }

    public Class<? extends Lexer> getLexerClass()
    {
        return lexerClass;
    }

    public boolean isFailOnError()
    {
        return failOnError;
    }

    public LexerOption failOnError()
    {
        failOnError = true;
        return this;
    }

    public LexerOption failOnError(boolean fail)
    {
        failOnError = fail;
        return this;
    }

    public Statement generateSetupStatement()
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                AunitRuntime.setLexerFactory(new LexerFactory(lexerClass));
            }
        };
    }

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
