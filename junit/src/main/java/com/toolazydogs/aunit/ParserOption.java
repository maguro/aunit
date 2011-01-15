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

import org.antlr.runtime.Parser;
import org.junit.runners.model.Statement;

import com.toolazydogs.aunit.internal.ParserFactory;


/**
 * @version $Revision: $ $Date: $
 */
class ParserOption implements Option
{
    private final Class<? extends Parser> parserClass;
    private boolean failOnError = true;

    ParserOption(Class<? extends Parser> parserClass)
    {
        this.parserClass = parserClass;
    }

    Class<? extends Parser> getParserClass()
    {
        return parserClass;
    }

    boolean isFailOnError()
    {
        return failOnError;
    }

    ParserOption failOnError()
    {
        failOnError = true;
        return this;
    }

    ParserOption failOnError(boolean fail)
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
                AunitRuntime.setParserFactory(new ParserFactory(parserClass, failOnError));
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
                AunitRuntime.setParserFactory(null);
            }
        };
    }
}
