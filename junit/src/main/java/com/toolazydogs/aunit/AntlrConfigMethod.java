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
import java.util.List;

import org.junit.runners.model.FrameworkMethod;

import com.toolazydogs.aunit.internal.Util;


/**
 * A source of configuration options.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Alan D. Cabrera (adc@toolazydogs.com)
 */
public abstract class AntlrConfigMethod extends FrameworkMethod
{
    /**
     * Returns a new {@code FrameworkMethod} for {@code method}
     */
    public AntlrConfigMethod(Method method)
    {
        super(method);
    }

    /**
     * Returns true if the configuration method matches (applies) to the test method about to be run.
     *
     * @param testMethod test method that is about to be run
     * @return true, if the configuration method applies to the test method
     */
    abstract public boolean matches(Method testMethod);

    /**
     * Returns an array of options.
     *
     * @return array of options (cannot be null)
     * @throws Exception - If determining the options fails
     */
    abstract public Option[] getOptions() throws Exception;

    /**
     * Validate that the configuration method is valid
     *
     * @param errors a place to put error messages if there are validation errors
     */
    public void validatePublicVoidNoArg(List<Throwable> errors)
    {
        final Method m = getMethod();
        Util.validateIsStatic(m, errors);
        Util.validatePublicNoArg(m, errors);
        Util.validatePrimitiveArray(m, Option.class, errors);
    }

}
