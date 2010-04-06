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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.antlr.runtime.Lexer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.toolazydogs.aunit.internal.AntlrConfigMethods;
import com.toolazydogs.aunit.internal.DefaultCompositeOption;
import com.toolazydogs.aunit.internal.RunBefores;


/**
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Alan D. Cabrera (adc@toolazydogs.com)
 * @version $Revision: $ $Date: $
 */
public class AntlrTestRunner extends BlockJUnit4ClassRunner
{
    public AntlrTestRunner(Class<?> klass) throws InitializationError
    {
        super(klass);
    }

    protected void collectInitializationErrors(List<Throwable> errors)
    {
        super.collectInitializationErrors(errors);

        validateConfigurationMethods(errors);
    }

    protected void validateConfigurationMethods(List<Throwable> errors)
    {
        try
        {
            final Collection<AntlrConfigMethod> configMethods = getConfigurationMethods();
            for (AntlrConfigMethod method : configMethods)
            {
                method.validatePublicVoidNoArg(errors);
            }
        }
        catch (Exception e)
        {
            errors.add(e);
        }
    }

    /**
     * Returns a {@link org.junit.runners.model.Statement}: run all non-overridden {@code @BeforeClass} methods on this class
     * and superclasses before executing {@code statement}; if any throws an
     * Exception, stop execution and pass the exception on.
     */
    protected Statement withBeforeClasses(Statement statement)
    {
        try
        {
            final List<? extends FrameworkMethod> configMethods = getConfigurationMethods();

            statement = new RunBefores(super.withBeforeClasses(statement), configMethods, null);
        }
        catch (Exception ignore)
        {
        }

        return statement;
    }

    /**
     * Finds the configuration methods based on the configured {@link ConfigurationStrategy}.
     *
     * @return collection of configuration methods (cannot be null but can be empty)
     */
    protected List<AntlrConfigMethod> getConfigurationMethods()
    {
        final List<AntlrConfigMethod> configMethods = new ArrayList<AntlrConfigMethod>();

        try
        {
            final Object testInstance = getTestClass().getJavaClass().newInstance();

            ConfigurationStrategy configStrategy = getTestClass().getJavaClass().getAnnotation(ConfigurationStrategy.class);
            if (configStrategy == null)
            {
                configStrategy = DefaultConfigurationStrategy.class.getAnnotation(ConfigurationStrategy.class);
            }

            final Class<? extends AntlrConfigMethods>[] configMethodsClasses = configStrategy.value();
            for (final Class<? extends AntlrConfigMethods> configMethodsClass : configMethodsClasses)
            {
                final Collection<? extends AntlrConfigMethod> methods = configMethodsClass.newInstance().getConfigMethods(getTestClass(), testInstance);
                if (methods != null)
                {
                    configMethods.addAll(methods);
                }
            }

            Configuration profileConfiguration = getTestClass().getJavaClass().getAnnotation(Configuration.class);
            if (profileConfiguration != null)
            {
                for (final Class<? extends CompositeOption> options : profileConfiguration.extend())
                {
                    configMethods.add(new AntlrConfigMethod(null)
                    {
                        public boolean matches(Method testMethod)
                        {
                            return true;
                        }

                        public Option[] getOptions() throws Exception
                        {
                            return options.newInstance().getOptions();
                        }

                        @Override
                        public void validatePublicVoidNoArg(List<Throwable> errors)
                        {
                        }
                    }
                    );
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
        }

        return configMethods;
    }

    private static Option getOptions(final Method methodName, final Collection<AntlrConfigMethod> configMethods) throws Exception
    {
        // always add the junit extender
        final DefaultCompositeOption option = new DefaultCompositeOption();

        // add options based on available configuration options from the test itself
        for (AntlrConfigMethod configMethod : configMethods)
        {
            if (configMethod.matches(methodName))
            {
                option.add(configMethod.getOptions());
            }
        }
        return option;
    }

    private List<Field> getDeclaredFields(Class clazz)
    {
        return getDeclaredFields(clazz, new ArrayList<Field>());
    }

    private List<Field> getDeclaredFields(Class clazz, ArrayList<Field> fields)
    {
        if (clazz == null) return fields;

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        return getDeclaredFields(clazz.getSuperclass(), fields);
    }

    @ConfigurationStrategy
    private class DefaultConfigurationStrategy
    {

    }
}
