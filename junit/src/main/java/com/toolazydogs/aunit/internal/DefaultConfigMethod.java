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
import java.util.Arrays;
import java.util.List;

import static com.toolazydogs.aunit.NullArgumentException.validateNotNull;

import com.toolazydogs.aunit.AntlrConfigMethod;
import com.toolazydogs.aunit.CompositeOption;
import com.toolazydogs.aunit.Configuration;
import com.toolazydogs.aunit.Option;
import com.toolazydogs.aunit.RequiresConfiguration;


/**
 * Configuration method marked with {@link Configuration} and {@link RequiresConfiguration} annotations.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @version $Revision: $ $Date: $
 */
public class DefaultConfigMethod extends AntlrConfigMethod
{
    /**
     * Instance of the class containing the configuration method. If null then the method is supposed to be static.
     */
    private final Object configInstance;

    /**
     * Configuration options. Lazy initialized only when the getter is called.
     */
    private Option[] options;

    /**
     * Constructor.
     *
     * @param configMethod   configuration method (cannot be null)
     * @param configInstance instance of the class containing the test method.
     *                       If null then the method is supposed to be static.
     * @throws IllegalArgumentException - If method is null
     */
    public DefaultConfigMethod(final Method configMethod, final Object configInstance)
    {
        super(configMethod);

        validateNotNull(configMethod, "Configuration method");

        this.configInstance = configInstance;
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

        final RequiresConfiguration requiresConfigAnnotation = method.getAnnotation(RequiresConfiguration.class);
        final String[] requiredConfigs;
        if (requiresConfigAnnotation != null)
        {
            requiredConfigs = requiresConfigAnnotation.value();
        }
        else
        {
            requiredConfigs = new String[]{".*"};
        }
        for (String requiredConfig : requiredConfigs)
        {
            if (getName().matches(requiredConfig))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the configuration options for this configuration method.
     *
     * @return array of configuration options
     * @throws IllegalAccessException    - Re-thrown, from invoking the configuration method via reflection
     * @throws InvocationTargetException - Re-thrown, from invoking the configuration method via reflection
     * @throws InstantiationException    - Re-thrown, from invoking the configuration method via reflection
     */
    public Option[] getOptions() throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (options == null)
        {
            List<Option> options = new ArrayList<Option>();

            Configuration config = getMethod().getAnnotation(Configuration.class);
            for (Class<? extends CompositeOption> option : config.extend())
            {
                options.addAll(Arrays.asList(option.newInstance().getOptions()));
            }

            options.addAll(Arrays.asList((Option[])getMethod().invoke(configInstance)));

            this.options = options.toArray(new Option[options.size()]);
        }

        return options;
    }

    @Override
    public String toString()
    {
        return getMethod().toString();
    }
}
