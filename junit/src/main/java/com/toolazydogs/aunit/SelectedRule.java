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

import java.lang.reflect.Method;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;


/**
 * Holds a selected parser method and its arguments for subsequent invocation.
 *
 * @see Work#rule(String, Object...)
 */
class SelectedRule
{
    private final Method method;
    private final Object[] arguments;

    /**
     * Initialize an instance with a selected parser method and its arguments
     * for subsequent invocation
     *
     * @param method    the selected parser method
     * @param arguments the arguments to use when invoking the method
     */
    SelectedRule(Method method, Object... arguments)
    {
        assert method != null;
        assert arguments != null;

        this.method = method;
        this.arguments = arguments;
    }

    /**
     * Invoke the selected parser method with its arguments
     *
     * @param parser the parser whose method will be invoked
     * @return the <code>RuleReturnScope</code> returned by the parser
     * @throws Exception if an error occurs
     */
    RuleReturnScope invoke(Parser parser) throws Exception
    {
        assert parser != null;

        return (RuleReturnScope)method.invoke(parser, arguments);
    }
}
