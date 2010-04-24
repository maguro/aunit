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

import java.lang.reflect.Method;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RuleReturnScope;


/**
 * @version $Revision: $ $Date: $
 */
class SelectedProduction
{
    private final Method method;
    private Object[] arguments;

    SelectedProduction(Method method, Object... arguments)
    {
        this.method = method;
        this.arguments = arguments;

    }

    RuleReturnScope invoke(Parser parser) throws Exception
    {
        return (RuleReturnScope)method.invoke(parser, arguments);
    }
}
