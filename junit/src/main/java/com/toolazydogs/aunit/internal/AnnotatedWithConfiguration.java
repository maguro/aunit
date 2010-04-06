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
package com.toolazydogs.aunit.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import com.toolazydogs.aunit.AntlrConfigMethod;
import com.toolazydogs.aunit.AppliesTo;
import com.toolazydogs.aunit.Configuration;
import com.toolazydogs.aunit.Option;


/**
 * Configuration strategy that will consider as configuration any static/non static methods that are marked with
 * {@link Configuration} annotation, has no parameters and has an {@link Option} array as return value.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @version $Revision: $ $Date: $
 */
public class AnnotatedWithConfiguration implements AntlrConfigMethods
{
    /**
     * {@inheritDoc}
     */
    public Collection<? extends AntlrConfigMethod> getConfigMethods(final TestClass testClass, final Object testInstance)
    {
        final List<AntlrConfigMethod> configMethods = new ArrayList<AntlrConfigMethod>();
        for (FrameworkMethod configMethod : testClass.getAnnotatedMethods(Configuration.class))
        {
            final Method method = configMethod.getMethod();

            if (!Modifier.isAbstract(method.getModifiers())
                && method.getParameterTypes() != null
                && method.getParameterTypes().length == 0
                && method.getReturnType().isArray()
                && Option.class.isAssignableFrom(method.getReturnType().getComponentType()))
            {
                final AppliesTo appliesToAnnotation = configMethod.getAnnotation(AppliesTo.class);
                if (appliesToAnnotation != null)
                {
                    configMethods.add(new AppliesToConfigMethod(method, Modifier.isStatic(method.getModifiers()) ? null : testInstance));
                }
                else
                {
                    configMethods.add(new DefaultConfigMethod(method, Modifier.isStatic(method.getModifiers()) ? null : testInstance));
                }
            }
        }
        return configMethods;
    }

}
