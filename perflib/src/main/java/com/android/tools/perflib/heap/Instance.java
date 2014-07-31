/*
 * Copyright (C) 2008 Google Inc.
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

package com.android.tools.perflib.heap;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public abstract class Instance {

    long mId;

    //  Id of the ClassObj of which this object is an instance
    ClassObj mClass;

    //  The stack in which this object was allocated
    StackTrace mStack;

    //  The heap in which this object was allocated (app, zygote, etc)
    Heap mHeap;

    //  The size of this object
    int mSize;

    //  The retained size of this object, indexed by heap (app, zygote, default).
    //  Intuitively, this represents the amount of memory that could be reclaimed in each heap if
    //  the instance were removed.
    private final Map<Heap, Long> mRetainedSizes = Maps.newHashMap();

    //  List of all objects that hold a live reference to this object
    private final ArrayList<Instance> mReferences = new ArrayList<Instance>();

    public abstract void accept(Visitor visitor);

    public ClassObj getClassObj() {
        return mClass;
    }

    public void setClass(ClassObj aClass) {
        assert mClass == null;
        mClass = aClass;
        aClass.addInstance(this);
    }

    public final int getCompositeSize() {
        CollectingVisitor visitor = new CollectingVisitor();
        this.accept(visitor);

        int size = 0;
        for (Instance instance : visitor.getVisited()) {
            size += instance.getSize();
        }
        return size;
    }

    //  Returns the instrinsic size of a given object
    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public void setHeap(Heap heap) {
        mHeap = heap;
    }

    public void setRetainedSize(Heap heap, long size) {
        mRetainedSizes.put(heap, size);
    }

    public long getRetainedSize(Heap heap) {
        Long result = mRetainedSizes.get(heap);
        return result == null ? 0L : result;
    }

    //  Add to the list of objects that have a hard reference to this Instance
    public void addReference(Instance reference) {
        mReferences.add(reference);
    }

    public ArrayList<Instance> getReferences() {
        return mReferences;
    }


    public static class CollectingVisitor implements Visitor {

        private final Set<Instance> mVisited = Sets.newHashSet();

        @Override
        public boolean visitEnter(Instance instance) {
            //  If we're in the set then we and our children have been visited
            return mVisited.add(instance);
        }

        @Override
        public void visitLeave(Instance instance) {
        }

        public Set<Instance> getVisited() {
            return mVisited;
        }
      }
}