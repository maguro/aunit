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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.toolazydogs.aunit.NullArgumentException.validateNotNull;

import com.toolazydogs.aunit.AntlrConfigMethod;
import com.toolazydogs.aunit.AppliesTo;
import com.toolazydogs.aunit.CompositeOption;
import com.toolazydogs.aunit.Configuration;
import com.toolazydogs.aunit.Option;


/**
 *
 */
public class AppliesToConfigMethod extends AntlrConfigMethod
{
    /**
     * Instance of the class containing the configuration method. If null then the method is supposed to be static.
     */
    private final Object m_configInstance;
    /**
     * Array of regular expression that are matched against test method name (cannot be null or empty).
     */
    private final String[] m_patterns;
    /**
     * Configuration options. Lazy initialized only when the getter is called.
     */
    private Option[] m_options;

    /**
     * Constructor.
     *
     * @param configMethod   configuration method (cannot be null)
     * @param configInstance instance of the class containing the test method.
     *                       If null then the method is supposed to be static.
     * @throws IllegalArgumentException - If method is null
     */
    public AppliesToConfigMethod(final Method configMethod,
                                 final Object configInstance)
    {
        super(configMethod);
        validateNotNull(configMethod, "Configuration method");
        m_configInstance = configInstance;

        final AppliesTo appliesToAnnotation = configMethod.getAnnotation(AppliesTo.class);

        if (appliesToAnnotation != null)
        {
            m_patterns = appliesToAnnotation.value();
        }
        else
        {
            m_patterns = new String[]{".*"};
        }
    }

    /**
     * Matches a test method name against this configuration method.
     *
     * @param method test method name (cannot be null or empty)
     * @return true if the test method name matches the configuration method, false otherwise
     * @throws IllegalArgumentException - If method name is null or empty
     */
    public boolean matches(final Method method)
    {
        validateNotNull(method, "Method");

        if (m_patterns != null)
        {
            for (String pattern : m_patterns)
            {
                if (method.getName().matches(pattern))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the configuration options for this configuration method.
     *
     * @return array of configuration options
     * @throws IllegalAccessException - Re-thrown, from invoking the configuration method via reflection
     * @throws java.lang.reflect.InvocationTargetException
     *                                - Re-thrown, from invoking the configuration method via reflection
     * @throws InstantiationException - Re-thrown, from invoking the configuration method via reflection
     */
    public Option[] getOptions() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (m_options == null)
        {
            List<Option> options = new ArrayList<Option>();

            Configuration config = getMethod().getAnnotation(Configuration.class);
            for (Class<? extends CompositeOption> option : config.extend())
            {
                for (Option o : option.newInstance().getOptions())
                {
                    options.add(o);
                }
            }

            for (Option o : (Option[])getMethod().invoke(m_configInstance))
            {
                options.add(o);
            }
            m_options = options.toArray(new Option[options.size()]);
        }

        return m_options;
    }

    public void validatePublicVoidNoArg(List<Throwable> errors)
    {
        final Method m = getMethod();
        Util.validateIsStatic(m, errors);
        Util.validatePublicNoArg(m, errors);
        Util.validatePrimitiveArray(m, Option.class, errors);
    }

    @Override
    public String toString()
    {
        return getMethod().toString();
    }
}
