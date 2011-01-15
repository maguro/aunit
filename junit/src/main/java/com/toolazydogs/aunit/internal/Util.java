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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 *
 */
public class Util
{
    /**
     * Validate that a method is public, has no arguments.
     *
     * @param method the method to be tested
     * @param errors a list to place the errors
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public static void validatePublicNoArg(Method method, List<Throwable> errors)
    {
        if (!Modifier.isPublic(method.getModifiers()))
        {
            errors.add(new Exception("Method " + method.getName() + "() should be public"));
        }
        if (method.getParameterTypes().length != 0)
        {
            errors.add(new Exception("Method " + method.getName() + " should have no parameters"));
        }
    }

    /**
     * Validate that a method returns no value.
     *
     * @param method the method to be tested
     * @param errors a list to place the errors
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public static void validateVoid(Method method, List<Throwable> errors)
    {
        if (method.getReturnType() != Void.TYPE)
        {
            errors.add(new Exception("Method " + method.getName() + "() should be void"));
        }
    }

    /**
     * Validate that a method returns primitive array of specific type.
     *
     * @param method the method to be tested
     * @param errors a list to place the errors
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public static void validatePrimitiveArray(Method method, Class type, List<Throwable> errors)
    {
        Class returnType = method.getReturnType();
        if (!returnType.isArray() || !returnType.getComponentType().equals(type))
        {
            errors.add(new Exception("Method " + method.getName() + "() should return " + type.getName() + "[]"));
        }
    }

    /**
     * Validate that a method is static.
     *
     * @param method the method to be tested
     * @param errors a list to place the errors
     */
    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    public static void validateIsStatic(Method method, List<Throwable> errors)
    {
        if (!Modifier.isStatic(method.getModifiers()))
        {
            errors.add(new Exception("Method " + method.getName() + "() should be static"));
        }
        if (!Modifier.isPublic(method.getDeclaringClass().getModifiers()))
        {
            errors.add(new Exception("Class " + method.getDeclaringClass().getName() + " should be public"));
        }
    }

    private Util() { }
}
