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

import org.junit.runners.model.Statement;


/**
 * Marker interface for integration test user specified options.
 *
 * @author Alin Dreghiciu (adreghiciu@gmail.com)
 */
public interface Option
{
    /**
     * Generate statements to be executed during test setup.
     *
     * @return the statement to execute when a test is being setup
     */
    Statement generateSetupStatement();

    /**
     * Generate statements to be executed during test teardown.
     *
     * @return the statement to execute when a test is being torn down.
     */
    Statement generateTeardownStatement();
}
