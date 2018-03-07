/*
 * Copyright 2018 the original author or authors.
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

package org.gradle.api.internal.tasks.properties;

import com.google.common.base.Equivalence;

import javax.annotation.Nullable;

public abstract class AbstractPropertyNode<SELF extends AbstractPropertyNode<SELF>> {
    private final String propertyName;
    private final SELF parentNode;
    private final TypeMetadata typeMetadata;

    public AbstractPropertyNode(@Nullable SELF parentNode, @Nullable String propertyName, TypeMetadata typeMetadata) {
        this.propertyName = propertyName;
        this.parentNode = parentNode;
        this.typeMetadata = typeMetadata;
    }

    public String getQualifiedPropertyName(String childPropertyName) {
        return propertyName == null ? childPropertyName : propertyName + "." + childPropertyName;
    }

    @Nullable
    public String getPropertyName() {
        return propertyName;
    }

    public TypeMetadata getTypeMetadata() {
        return typeMetadata;
    }

    @Nullable
    protected SELF findNodeCreatingCycle(SELF childNode, Equivalence<? super SELF> nodeEquivalence) {
        if (parentNode == null) {
            return null;
        }
        if (nodeEquivalence.equivalent(parentNode, childNode)) {
            return parentNode;
        }
        return parentNode.findNodeCreatingCycle(childNode, nodeEquivalence);
    }

    @Override
    public String toString() {
        //noinspection ConstantConditions
        return propertyName == null ? "<root>" : getPropertyName();
    }
}
