/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.tasks.testing.testng

import org.junit.Before
import org.junit.Test
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*
import org.gradle.api.tasks.testing.AbstractTestFrameworkOptionsTest
import org.gradle.api.JavaVersion
import org.gradle.external.testng.TestNGTestFramework;

/**
 * @author Tom Eyckmans
 */

public class TestNGOptionsTest extends AbstractTestFrameworkOptionsTest<TestNGTestFramework> {

    TestNGOptions testngOptions;

    String[] groups = ['fast', 'unit']

    @Before public void setUp()
    {
        super.setUp(TestNGTestFramework)

        testngOptions = new TestNGOptions(testFrameworkMock, new File("projectDir"))
    }

    @Test public void verifyDefaults()
    {
        assertNull(testngOptions.annotations)

        assertNull(testngOptions.testResources)

        assertFalse(testngOptions.dumpCommand)

        assertTrue(testngOptions.enableAssert)

        assertNotNull(testngOptions.includeGroups)
        assertTrue(testngOptions.includeGroups.empty)

        assertNotNull(testngOptions.excludeGroups)
        assertTrue(testngOptions.excludeGroups.empty)

        assertNull(testngOptions.jvm)

        assertNotNull(testngOptions.listeners)
        assertTrue(testngOptions.listeners.empty)

        assertNull(testngOptions.skippedProperty)

        assertNull(testngOptions.suiteRunnerClass)

        assertNull(testngOptions.parallel)

        assertEquals(testngOptions.threadCount, 1)

        assertEquals(testngOptions.timeOut, Long.MAX_VALUE)

        assertNull(testngOptions.suiteName)

        assertNull(testngOptions.testName)
    }

    @Test public void jdk14SourceCompatibilityAnnotationsDefaulting()
    {
        assertNull(testngOptions.annotations)

        testngOptions.setAnnotationsOnSourceCompatibility(JavaVersion.VERSION_1_4)

        assertEquals(testngOptions.annotations, TestNGOptions.JAVADOC_ANNOTATIONS)
    }

    @Test public void jdk15SourceCompatibilityAnnotationsDefaulting()
    {
        assertNull(testngOptions.annotations)

        testngOptions.setAnnotationsOnSourceCompatibility(JavaVersion.VERSION_1_5)

        assertEquals(testngOptions.annotations, TestNGOptions.JDK_ANNOTATIONS)
    }

    @Test public void jdk16SourceCompatibilityAnnotationsDefaulting()
    {
        assertNull(testngOptions.annotations)

        testngOptions.setAnnotationsOnSourceCompatibility(JavaVersion.VERSION_1_6)

        assertEquals(testngOptions.annotations, TestNGOptions.JDK_ANNOTATIONS)
    }

    @Test public void testIncludeGroups()
    {
        assertTrue(testngOptions.excludeGroups.empty);
        assertTrue(testngOptions.includeGroups.empty);

        testngOptions.includeGroups(groups);

        assertFalse(testngOptions.includeGroups.empty)
        assertThat(testngOptions.includeGroups, hasItems(groups))
        assertTrue(testngOptions.excludeGroups.empty);
    }

    @Test public void testExcludeGroups()
    {
        assertTrue(testngOptions.excludeGroups.empty);
        assertTrue(testngOptions.includeGroups.empty);

        testngOptions.excludeGroups(groups)

        assertFalse(testngOptions.excludeGroups.empty)
        assertThat(testngOptions.excludeGroups, hasItems(groups))
        assertTrue(testngOptions.includeGroups.empty);
    }

    @Test public void createsForkOptions() {
        testngOptions.systemProperties.prop = 'value'
        def forkOptions = testngOptions.createForkOptions()
        assertThat(forkOptions.systemProperties, equalTo(testngOptions.systemProperties))
    }
}
