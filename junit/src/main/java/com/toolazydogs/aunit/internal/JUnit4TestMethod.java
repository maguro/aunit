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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import static com.toolazydogs.aunit.NullArgumentException.validateNotNull;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;

import com.toolazydogs.aunit.Option;


/**
 * A {@link TestMethod} that upon invocation creates a lexer, parser, or tree walker and executes the test.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 * @author Alan D. Cabrera (adc@toolazydogs.com)
 * @version $Revision: $ $Date: $
 */
public class JUnit4TestMethod extends TestMethod
{
    /**
     * JUL logger.
     */
    private static final Logger LOG = Logger.getLogger(JUnit4TestMethod.class.getName());

    /**
     * Test method. Cannot reuse the one from super class as it is not public.
     */
    private final Method testMethod;

    /**
     * Configuration options.
     */
    private final Option[] options;

    /**
     * Configuration method name (test method name and eventual the framework and framework version)
     */
    private final String name;

    /**
     * Constructor.
     *
     * @param testMethod  test method (cannot be null)
     * @param testClass   test class (cannot be null)
     * @param userOptions user options (can be null)
     */
    public JUnit4TestMethod(final Method testMethod, final TestClass testClass, final Option... userOptions)
    {
        super(testMethod, testClass);
        validateNotNull(testMethod, "Test method");
        validateNotNull(testClass, "Test class");

        this.testMethod = testMethod;
        options = userOptions;
        name = testMethod.getName();
    }

    /**
     * {@inheritDoc} Starts the test container, installs the test bundle and executes the test within the container.
     */
    @Override
    public void invoke(Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        final String fullTestName = name + "(" + testMethod.getDeclaringClass().getName() + ")";
        LOG.info("Starting test " + fullTestName);


        LOG.finest("Execute test [" + name + "]");
        try
        {
            LOG.info("Starting test " + fullTestName);
            ((CallableTestMethod)test).call();
            LOG.info("Test " + fullTestName + " ended succesfully");
        }
        catch (InstantiationException e)
        {
            throw new InvocationTargetException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new InvocationTargetException(e);
        }
    }

    /**
     * Getter.
     *
     * @return test method
     */
    public Method getTestMethod()
    {
        return testMethod;
    }

    /**
     * Getter.
     *
     * @return test method name
     */
    public String getName()
    {
        return name;
    }
}
