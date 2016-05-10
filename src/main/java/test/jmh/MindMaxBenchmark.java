/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package test.jmh;

import org.openjdk.jmh.annotations.*;
import test.MaxMind2Holder;
import test.MaxMind2v2Holder;
import test.MaxMindHolder;
import test.ParamsHolder;
import test.wrapper.LocationData;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@BenchmarkMode(Mode.All)
@Warmup(iterations = 25, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
@Threads(1)
public class MindMaxBenchmark {


    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public LocationData maxMind1(States s) {
        InetAddress next = s.addresses[s.counter.get().getAndIncrement()%s.addresses.length];
        LocationData resolve = MaxMindHolder.getInstance().getService().resolve(next);
        return resolve;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public LocationData maxMind2(States s) {
        InetAddress next = s.addresses[s.counter.get().getAndIncrement()%s.addresses.length];
        LocationData resolve = MaxMind2Holder.getInstance().getService().resolve(next);
        return resolve;
    }

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public LocationData maxMind2v2(States s) {
        InetAddress next = s.addresses[s.counter.get().getAndIncrement()%s.addresses.length];
        LocationData resolve = MaxMind2v2Holder.getInstance().getService().resolve(next);
        return resolve;
    }

    @State(Scope.Thread)
    public static class States {
        InetAddress[] addresses;
        ThreadLocal<AtomicInteger> counter = ThreadLocal.withInitial(()->new AtomicInteger(0));
        @Setup
        public void setup(){
            ParamsHolder.getInstance().init();
            addresses = ParamsHolder.getInstance().getInetAddresses();
        }

    }

}
