///*
// * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// *
// */
//
///*
// *
// *
// *
// *
// *
// * Written by Doug Lea with assistance from members of JCP JSR-166
// * Expert Group and released to the public domain, as explained at
// * http://creativecommons.org/publicdomain/zero/1.0/
// */
//
//package com.jasu.concurrent.executor;
//
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.AbstractQueuedSynchronizer;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * An {@link ExecutorService} that executes each submitted task using
// * one of possibly several pooled threads, normally configured
// * using {@link Executors} factory methods.
// *
// * <p>Thread pools address two different problems: they usually
// * provide improved performance when executing large numbers of
// * asynchronous tasks, due to reduced per-task invocation overhead,
// * and they provide a means of bounding and managing the resources,
// * including threads, consumed when executing a collection of tasks.
// * Each {@code ThreadPoolExecutor} also maintains some basic
// * statistics, such as the number of completed tasks.
// *
// * <p>To be useful across a wide range of contexts, this class
// * provides many adjustable parameters and extensibility
// * hooks. However, programmers are urged to use the more convenient
// * {@link Executors} factory methods {@link
// * Executors#newCachedThreadPool} (unbounded thread pool, with
// * automatic thread reclamation), {@link Executors#newFixedThreadPool}
// * (fixed size thread pool) and {@link
// * Executors#newSingleThreadExecutor} (single background thread), that
// * preconfigure settings for the most common usage
// * scenarios. Otherwise, use the following guide when manually
// * configuring and tuning this class:
// *
// * <dl>
// *
// * <dt>Core and maximum pool sizes</dt>
// *
// * <dd>A {@code ThreadPoolExecutor} will automatically adjust the
// * pool size (see {@link #getPoolSize})
// * according to the bounds set by
// * corePoolSize (see {@link #getCorePoolSize}) and
// * maximumPoolSize (see {@link #getMaximumPoolSize}).
// *
// * When a new task is submitted in method {@link #execute(Runnable)},
// * and fewer than corePoolSize threads are running, a new thread is
// * created to handle the request, even if other worker threads are
// * idle.  If there are more than corePoolSize but less than
// * maximumPoolSize threads running, a new thread will be created only
// * if the queue is full.  By setting corePoolSize and maximumPoolSize
// * the same, you create a fixed-size thread pool. By setting
// * maximumPoolSize to an essentially unbounded value such as {@code
// * Integer.MAX_VALUE}, you allow the pool to accommodate an arbitrary
// * number of concurrent tasks. Most typically, core and maximum pool
// * sizes are set only upon construction, but they may also be changed
// * dynamically using {@link #setCorePoolSize} and {@link
// * #setMaximumPoolSize}. </dd>
// *
// * <dt>On-demand construction</dt>
// *
// * <dd>By default, even core threads are initially created and
// * started only when new tasks arrive, but this can be overridden
// * dynamically using method {@link #prestartCoreThread} or {@link
// * #prestartAllCoreThreads}.  You probably want to prestart threads if
// * you construct the pool with a non-empty queue. </dd>
// *
// * <dt>Creating new threads</dt>
// *
// * <dd>New threads are created using a {@link ThreadFactory}.  If not
// * otherwise specified, a {@link Executors#defaultThreadFactory} is
// * used, that creates threads to all be in the same {@link
// * ThreadGroup} and with the same {@code NORM_PRIORITY} priority and
// * non-daemon status. By supplying a different ThreadFactory, you can
// * alter the thread's name, thread group, priority, daemon status,
// * etc. If a {@code ThreadFactory} fails to create a thread when asked
// * by returning null from {@code newThread}, the executor will
// * continue, but might not be able to execute any tasks. Threads
// * should possess the "modifyThread" {@code RuntimePermission}. If
// * worker threads or other threads using the pool do not possess this
// * permission, service may be degraded: configuration changes may not
// * take effect in a timely manner, and a shutdown pool may remain in a
// * state in which termination is possible but not completed.</dd>
// *
// * <dt>Keep-alive times</dt>
// *
// * <dd>If the pool currently has more than corePoolSize threads,
// * excess threads will be terminated if they have been idle for more
// * than the keepAliveTime (see {@link #getKeepAliveTime(TimeUnit)}).
// * This provides a means of reducing resource consumption when the
// * pool is not being actively used. If the pool becomes more active
// * later, new threads will be constructed. This parameter can also be
// * changed dynamically using method {@link #setKeepAliveTime(long,
// * TimeUnit)}.  Using a value of {@code Long.MAX_VALUE} {@link
// * TimeUnit#NANOSECONDS} effectively disables idle threads from ever
// * terminating prior to shut down. By default, the keep-alive policy
// * applies only when there are more than corePoolSize threads. But
// * method {@link #allowCoreThreadTimeOut(boolean)} can be used to
// * apply this time-out policy to core threads as well, so long as the
// * keepAliveTime value is non-zero. </dd>
// *
// * <dt>Queuing</dt>
// *
// * <dd>Any {@link BlockingQueue} may be used to transfer and hold
// * submitted tasks.  The use of this queue interacts with pool sizing:
// *
// * <ul>
// *
// * <li> If fewer than corePoolSize threads are running, the Executor
// * always prefers adding a new thread
// * rather than queuing.</li>
// *
// * <li> If corePoolSize or more threads are running, the Executor
// * always prefers queuing a request rather than adding a new
// * thread.</li>
// *
// * <li> If a request cannot be queued, a new thread is created unless
// * this would exceed maximumPoolSize, in which case, the task will be
// * rejected.</li>
// *
// * </ul>
// *
// * There are three general strategies for queuing:
// * <ol>
// *
// * <li> <em> Direct handoffs.</em> A good default choice for a work
// * queue is a {@link SynchronousQueue} that hands off tasks to threads
// * without otherwise holding them. Here, an attempt to queue a task
// * will fail if no threads are immediately available to run it, so a
// * new thread will be constructed. This policy avoids lockups when
// * handling sets of requests that might have internal dependencies.
// * Direct handoffs generally require unbounded maximumPoolSizes to
// * avoid rejection of new submitted tasks. This in turn admits the
// * possibility of unbounded thread growth when commands continue to
// * arrive on average faster than they can be processed.  </li>
// *
// * <li><em> Unbounded queues.</em> Using an unbounded queue (for
// * example a {@link LinkedBlockingQueue} without a predefined
// * capacity) will cause new tasks to wait in the queue when all
// * corePoolSize threads are busy. Thus, no more than corePoolSize
// * threads will ever be created. (And the value of the maximumPoolSize
// * therefore doesn't have any effect.)  This may be appropriate when
// * each task is completely independent of others, so tasks cannot
// * affect each others execution; for example, in a web page server.
// * While this style of queuing can be useful in smoothing out
// * transient bursts of requests, it admits the possibility of
// * unbounded work queue growth when commands continue to arrive on
// * average faster than they can be processed.  </li>
// *
// * <li><em>Bounded queues.</em> A bounded queue (for example, an
// * {@link ArrayBlockingQueue}) helps prevent resource exhaustion when
// * used with finite maximumPoolSizes, but can be more difficult to
// * tune and control.  Queue sizes and maximum pool sizes may be traded
// * off for each other: Using large queues and small pools minimizes
// * CPU usage, OS resources, and context-switching overhead, but can
// * lead to artificially low throughput.  If tasks frequently block (for
// * example if they are I/O bound), a system may be able to schedule
// * time for more threads than you otherwise allow. Use of small queues
// * generally requires larger pool sizes, which keeps CPUs busier but
// * may encounter unacceptable scheduling overhead, which also
// * decreases throughput.  </li>
// *
// * </ol>
// *
// * </dd>
// *
// * <dt>Rejected tasks</dt>
// *
// * <dd>New tasks submitted in method {@link #execute(Runnable)} will be
// * <em>rejected</em> when the Executor has been shut down, and also when
// * the Executor uses finite bounds for both maximum threads and work queue
// * capacity, and is saturated.  In either case, the {@code execute} method
// * invokes the {@link
// * RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)}
// * method of its {@link RejectedExecutionHandler}.  Four predefined handler
// * policies are provided:
// *
// * <ol>
// *
// * <li> In the default {@link ThreadPoolExecutor.AbortPolicy}, the
// * handler throws a runtime {@link RejectedExecutionException} upon
// * rejection. </li>
// *
// * <li> In {@link ThreadPoolExecutor.CallerRunsPolicy}, the thread
// * that invokes {@code execute} itself runs the task. This provides a
// * simple feedback control mechanism that will slow down the rate that
// * new tasks are submitted. </li>
// *
// * <li> In {@link ThreadPoolExecutor.DiscardPolicy}, a task that
// * cannot be executed is simply dropped.  </li>
// *
// * <li>In {@link ThreadPoolExecutor.DiscardOldestPolicy}, if the
// * executor is not shut down, the task at the head of the work queue
// * is dropped, and then execution is retried (which can fail again,
// * causing this to be repeated.) </li>
// *
// * </ol>
// *
// * It is possible to define and use other kinds of {@link
// * RejectedExecutionHandler} classes. Doing so requires some care
// * especially when policies are designed to work only under particular
// * capacity or queuing policies. </dd>
// *
// * <dt>Hook methods</dt>
// *
// * <dd>This class provides {@code protected} overridable
// * {@link #beforeExecute(Thread, Runnable)} and
// * {@link #afterExecute(Runnable, Throwable)} methods that are called
// * before and after execution of each task.  These can be used to
// * manipulate the execution environment; for example, reinitializing
// * ThreadLocals, gathering statistics, or adding log entries.
// * Additionally, method {@link #terminated} can be overridden to perform
// * any special processing that needs to be done once the Executor has
// * fully terminated.
// *
// * <p>If hook or callback methods throw exceptions, internal worker
// * threads may in turn fail and abruptly terminate.</dd>
// *
// * <dt>Queue maintenance</dt>
// *
// * <dd>Method {@link #getQueue()} allows access to the work queue
// * for purposes of monitoring and debugging.  Use of this method for
// * any other purpose is strongly discouraged.  Two supplied methods,
// * {@link #remove(Runnable)} and {@link #purge} are available to
// * assist in storage reclamation when large numbers of queued tasks
// * become cancelled.</dd>
// *
// * <dt>Finalization</dt>
// *
// * <dd>A pool that is no longer referenced in a program <em>AND</em>
// * has no remaining threads will be {@code shutdown} automatically. If
// * you would like to ensure that unreferenced pools are reclaimed even
// * if users forget to call {@link #shutdown}, then you must arrange
// * that unused threads eventually die, by setting appropriate
// * keep-alive times, using a lower bound of zero core threads and/or
// * setting {@link #allowCoreThreadTimeOut(boolean)}.  </dd>
// *
// * </dl>
// *
// * <p><b>Extension example</b>. Most extensions of this class
// * override one or more of the protected hook methods. For example,
// * here is a subclass that adds a simple pause/resume feature:
// *
// *  <pre> {@code
// * class PausableThreadPoolExecutor extends ThreadPoolExecutor {
// *   private boolean isPaused;
// *   private ReentrantLock pauseLock = new ReentrantLock();
// *   private Condition unpaused = pauseLock.newCondition();
// *
// *   public PausableThreadPoolExecutor(...) { super(...); }
// *
// *   protected void beforeExecute(Thread t, Runnable r) {
// *     super.beforeExecute(t, r);
// *     pauseLock.lock();
// *     try {
// *       while (isPaused) unpaused.await();
// *     } catch (InterruptedException ie) {
// *       t.interrupt();
// *     } finally {
// *       pauseLock.unlock();
// *     }
// *   }
// *
// *   public void pause() {
// *     pauseLock.lock();
// *     try {
// *       isPaused = true;
// *     } finally {
// *       pauseLock.unlock();
// *     }
// *   }
// *
// *   public void resume() {
// *     pauseLock.lock();
// *     try {
// *       isPaused = false;
// *       unpaused.signalAll();
// *     } finally {
// *       pauseLock.unlock();
// *     }
// *   }
// * }}</pre>
// *
// * @since 1.5
// * @author Doug Lea
// */
//public class ThreadPoolExecutor extends AbstractExecutorService {
//    /**
//     * The main pool control state, ctl, is an atomic integer packing
//     * two conceptual fields
//     *   workerCount, indicating the effective number of threads
//     *   runState,    indicating whether running, shutting down etc
//     *
//     * In order to pack them into one int, we limit workerCount to
//     * (2^29)-1 (about 500 million) threads rather than (2^31)-1 (2
//     * billion) otherwise representable. If this is ever an issue in
//     * the future, the variable can be changed to be an AtomicLong,
//     * and the shift/mask constants below adjusted. But until the need
//     * arises, this code is a bit faster and simpler using an int.
//     *
//     * The workerCount is the number of workers that have been
//     * permitted to start and not permitted to stop.  The value may be
//     * transiently different from the actual number of live threads,
//     * for example when a ThreadFactory fails to create a thread when
//     * asked, and when exiting threads are still performing
//     * bookkeeping before terminating. The user-visible pool size is
//     * reported as the current size of the workers set.
//     *
//     * The runState provides the main lifecycle control, taking on values:
//     *
//     *   RUNNING:  Accept new tasks and process queued tasks
//     *   SHUTDOWN: Don't accept new tasks, but process queued tasks
//     *   STOP:     Don't accept new tasks, don't process queued tasks,
//     *             and interrupt in-progress tasks
//     *   TIDYING:  All tasks have terminated, workerCount is zero,
//     *             the thread transitioning to state TIDYING
//     *             will run the terminated() hook method
//     *   TERMINATED: terminated() has completed
//     *
//     * The numerical order among these values matters, to allow
//     * ordered comparisons. The runState monotonically increases over
//     * time, but need not hit each state. The transitions are:
//     *
//     * RUNNING -> SHUTDOWN
//     *    On invocation of shutdown(), perhaps implicitly in finalize()
//     * (RUNNING or SHUTDOWN) -> STOP
//     *    On invocation of shutdownNow()
//     * SHUTDOWN -> TIDYING
//     *    When both queue and pool are empty
//     * STOP -> TIDYING
//     *    When pool is empty
//     * TIDYING -> TERMINATED
//     *    When the terminated() hook method has completed
//     *
//     * Threads waiting in awaitTermination() will return when the
//     * state reaches TERMINATED.
//     *
//     * Detecting the transition from SHUTDOWN to TIDYING is less
//     * straightforward than you'd like because the queue may become
//     * empty after non-empty and vice versa during SHUTDOWN state, but
//     * we can only terminate if, after seeing that it is empty, we see
//     * that workerCount is 0 (which sometimes entails a recheck -- see
//     * below).
//     */
//    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
//    //29 代表高三位存state，低29位存worker数量
//    private static final int COUNT_BITS = Integer.SIZE - 3;
//    //代表线程池的最大线程数是 2^29-1=536870911
//    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
//
//    // 111 00000000000000000000000000000
//    private static final int RUNNING    = -1 << COUNT_BITS;
//    // 000 00000000000000000000000000000
//    private static final int SHUTDOWN   =  0 << COUNT_BITS;
//    // 001 00000000000000000000000000000
//    private static final int STOP       =  1 << COUNT_BITS;
//    // 010 00000000000000000000000000000
//    private static final int TIDYING    =  2 << COUNT_BITS;
//    // 011 00000000000000000000000000000
//    private static final int TERMINATED =  3 << COUNT_BITS;
//
//    // ctl的后29位变0，得到state
//    private static int runStateOf(int c)     { return c & ~CAPACITY; }
//    // 高3位变0，得到worker数量
//    private static int workerCountOf(int c)  { return c & CAPACITY; }
//    private static int ctlOf(int rs, int wc) { return rs | wc; }
//
//    /*
//     * Bit field accessors that don't require unpacking ctl.
//     * These depend on the bit layout and on workerCount being never negative.
//     */
//
//    private static boolean runStateLessThan(int c, int s) {
//        return c < s;
//    }
//
//    private static boolean runStateAtLeast(int c, int s) {
//        return c >= s;
//    }
//
//    private static boolean isRunning(int c) {
//        return c < SHUTDOWN;
//    }
//
//    /**
//     * Attempts to CAS-increment the workerCount field of ctl.
//     */
//    private boolean compareAndIncrementWorkerCount(int expect) {
//        return ctl.compareAndSet(expect, expect + 1);
//    }
//
//    /**
//     * Attempts to CAS-decrement the workerCount field of ctl.
//     */
//    private boolean compareAndDecrementWorkerCount(int expect) {
//        return ctl.compareAndSet(expect, expect - 1);
//    }
//
//    /**
//     * Decrements the workerCount field of ctl. This is called only on
//     * abrupt termination of a thread (see processWorkerExit). Other
//     * decrements are performed within getTask.
//     */
//    private void decrementWorkerCount() {
//        do {} while (! compareAndDecrementWorkerCount(ctl.get()));
//    }
//
//    /**
//     * The queue used for holding tasks and handing off to worker
//     * threads.  We do not require that workQueue.poll() returning
//     * null necessarily means that workQueue.isEmpty(), so rely
//     * solely on isEmpty to see if the queue is empty (which we must
//     * do for example when deciding whether to transition from
//     * SHUTDOWN to TIDYING).  This accommodates special-purpose
//     * queues such as DelayQueues for which poll() is allowed to
//     * return null even if it may later return non-null when delays
//     * expire.
//     */
//
//
//    /**
//     * Lock held on access to workers set and related bookkeeping.
//     * While we could use a concurrent set of some sort, it turns out
//     * to be generally preferable to use a lock. Among the reasons is
//     * that this serializes interruptIdleWorkers, which avoids
//     * unnecessary interrupt storms, especially during shutdown.
//     * Otherwise exiting threads would concurrently interrupt those
//     * that have not yet interrupted. It also simplifies some of the
//     * associated statistics bookkeeping of largestPoolSize etc. We
//     * also hold mainLock on shutdown and shutdownNow, for the sake of
//     * ensuring workers set is stable while separately checking
//     * permission to interrupt and actually interrupting.
//     */
//    // 线程池的锁，用于在访问workers时,以及统计线程池相关数据（如pool size)
//    // 和 shutdown等时候使用。
//    private final ReentrantLock mainLock = new ReentrantLock();
//
//    // 存放Worker的HashSet，必须加锁才能操作workers
//    private final HashSet<Worker> workers = new HashSet<Worker>();
//
//    /**
//     * Wait condition to support awaitTermination
//     */
//    private final Condition termination = mainLock.newCondition();
//
//    //最大的pool size，指曾经达到的最大worker（HashSet size）的数量。
//    private int largestPoolSize;
//
//    //完成的task计数
//    private long completedTaskCount;
//
//
//
//    private volatile boolean allowCoreThreadTimeOut;
//
//
//    //下面是构造器的参数了
//    private final BlockingQueue<Runnable> workQueue;
//
//    private volatile ThreadFactory threadFactory;
//
//    private volatile RejectedExecutionHandler handler;
//
//    private volatile long keepAliveTime;
//
//
//
//    private volatile int corePoolSize;
//
//    private volatile int maximumPoolSize;
//    /*
//     * All user control parameters are declared as volatiles so that
//     * ongoing actions are based on freshest values, but without need
//     * for locking, since no internal invariants depend on them
//     * changing synchronously with respect to other actions.
//     */
//
//    /**
//     * Factory for new threads. All threads are created using this
//     * factory (via method addWorker).  All callers must be prepared
//     * for addWorker to fail, which may reflect a system or user's
//     * policy limiting the number of threads.  Even though it is not
//     * treated as an error, failure to create threads may result in
//     * new tasks being rejected or existing ones remaining stuck in
//     * the queue.
//     *
//     * We go further and preserve pool invariants even in the face of
//     * errors such as OutOfMemoryError, that might be thrown while
//     * trying to create threads.  Such errors are rather common due to
//     * the need to allocate a native stack in Thread.start, and users
//     * will want to perform clean pool shutdown to clean up.  There
//     * will likely be enough memory available for the cleanup code to
//     * complete without encountering yet another OutOfMemoryError.
//     */
//
//
//    /**
//     * Handler called when saturated or shutdown in execute.
//     */
//
//
//    /**
//     * Timeout in nanoseconds for idle threads waiting for work.
//     * Threads use this timeout when there are more than corePoolSize
//     * present or if allowCoreThreadTimeOut. Otherwise they wait
//     * forever for new work.
//     */
//
//
//    /**
//     * If false (default), core threads stay alive even when idle.
//     * If true, core threads use keepAliveTime to time out waiting
//     * for work.
//     */
//
//
//    /**
//     * Core pool size is the minimum number of workers to keep alive
//     * (and not allow to time out etc) unless allowCoreThreadTimeOut
//     * is set, in which case the minimum is zero.
//     */
//
//
//    /**
//     * Maximum pool size. Note that the actual maximum is internally
//     * bounded by CAPACITY.
//     */
//
//
//    /**
//     * The default rejected execution handler
//     */
//    private static final RejectedExecutionHandler defaultHandler =
//        new AbortPolicy();
//
//    /**
//     * Permission required for callers of shutdown and shutdownNow.
//     * We additionally require (see checkShutdownAccess) that callers
//     * have permission to actually interrupt threads in the worker set
//     * (as governed by Thread.interrupt, which relies on
//     * ThreadGroup.checkAccess, which in turn relies on
//     * SecurityManager.checkAccess). Shutdowns are attempted only if
//     * these checks pass.
//     *
//     * All actual invocations of Thread.interrupt (see
//     * interruptIdleWorkers and interruptWorkers) ignore
//     * SecurityExceptions, meaning that the attempted interrupts
//     * silently fail. In the case of shutdown, they should not fail
//     * unless the SecurityManager has inconsistent policies, sometimes
//     * allowing access to a thread and sometimes not. In such cases,
//     * failure to actually interrupt threads may disable or delay full
//     * termination. Other uses of interruptIdleWorkers are advisory,
//     * and failure to actually interrupt will merely delay response to
//     * configuration changes so is not handled exceptionally.
//     */
//    private static final RuntimePermission shutdownPerm =
//        new RuntimePermission("modifyThread");
//
//
//    private final class Worker
//        extends AbstractQueuedSynchronizer
//        implements Runnable
//    {
//
//        private static final long serialVersionUID = 6138294804551838833L;
//
//        //真正的线程对象，在构造器处用ThreadFactory把自身作为Runnable，new出Thread赋值。
//        final Thread thread;
//        //顾名思义，第一次的Runnable任务，唯一的构造器的唯一参数，可以是我们执行execute时候
//        //传入的Runnable，也可以是null, 即我们以空任务为开始启动Worker
//        Runnable firstTask;
//        //完成的tasks计数，voliatile保证可见性。
//        volatile long completedTasks;
//
//
//        Worker(Runnable firstTask) {
//            //AQS 设置初始state为1，防止在runWorker前中断。
//            setState(-1);
//            this.firstTask = firstTask;
//            this.thread = getThreadFactory().newThread(this);
//        }
//
//        //run方法为自身实例作为runnable执行ThreadPoolExecutor的runWorker方法，
//        //调用线程start的上面的thread线程
//        public void run() {
//            runWorker(this);
//        }
//
//        // AQS 相关方法，0代表解锁状态，1代表加锁状态
//        //是否加锁态，加锁状态代表正在执行task
//        protected boolean isHeldExclusively() {
//            return getState() != 0;
//        }
//        // AQS 加锁的方法，CAS state 由0，变为1，初始态为-1，所以需要unlock一次，将state变为0再加锁。
//        // 只能从0变为1才成功加锁，就是必须在state=0的解锁状态才能加锁，是不可重入的锁
//        protected boolean tryAcquire(int unused) {
//            if (compareAndSetState(0, 1)) {
//                setExclusiveOwnerThread(Thread.currentThread());
//                return true;
//            }
//            return false;
//        }
//        // 解锁方法 state=0
//        protected boolean tryRelease(int unused) {
//            setExclusiveOwnerThread(null);
//            setState(0);
//            return true;
//        }
//
//        public void lock()        { acquire(1); }
//        public boolean tryLock()  { return tryAcquire(1); }
//        public void unlock()      { release(1); }
//        public boolean isLocked() { return isHeldExclusively(); }
//
//        void interruptIfStarted() {
//            Thread t;
//            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
//                try {
//                    t.interrupt();
//                } catch (SecurityException ignore) {
//                }
//            }
//        }
//    }
//
//    /*
//     * Methods for setting control state
//     */
//
//    /**
//     * Transitions runState to given target, or leaves it alone if
//     * already at least the given target.
//     *
//     * @param targetState the desired state, either SHUTDOWN or STOP
//     *        (but not TIDYING or TERMINATED -- use tryTerminate for that)
//     */
//    private void advanceRunState(int targetState) {
//        for (;;) {
//            int c = ctl.get();
//            if (runStateAtLeast(c, targetState) ||
//                ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
//                break;
//        }
//    }
//
//    /**
//     * Transitions to TERMINATED state if either (SHUTDOWN and pool
//     * and queue empty) or (STOP and pool empty).  If otherwise
//     * eligible to terminate but workerCount is nonzero, interrupts an
//     * idle worker to ensure that shutdown signals propagate. This
//     * method must be called following any action that might make
//     * termination possible -- reducing worker count or removing tasks
//     * from the queue during shutdown. The method is non-private to
//     * allow access from ScheduledThreadPoolExecutor.
//     */
//    final void tryTerminate() {
//        for (;;) {
//            int c = ctl.get();
//            if (isRunning(c) ||
//                runStateAtLeast(c, TIDYING) ||
//                (runStateOf(c) == SHUTDOWN && ! workQueue.isEmpty()))
//                return;
//            if (workerCountOf(c) != 0) { // Eligible to terminate
//                interruptIdleWorkers(ONLY_ONE);
//                return;
//            }
//
//            final ReentrantLock mainLock = this.mainLock;
//            mainLock.lock();
//            try {
//                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
//                    try {
//                        terminated();
//                    } finally {
//                        ctl.set(ctlOf(TERMINATED, 0));
//                        termination.signalAll();
//                    }
//                    return;
//                }
//            } finally {
//                mainLock.unlock();
//            }
//            // else retry on failed CAS
//        }
//    }
//
//    /*
//     * Methods for controlling interrupts to worker threads.
//     */
//
//    /**
//     * If there is a security manager, makes sure caller has
//     * permission to shut down threads in general (see shutdownPerm).
//     * If this passes, additionally makes sure the caller is allowed
//     * to interrupt each worker thread. This might not be true even if
//     * first check passed, if the SecurityManager treats some threads
//     * specially.
//     */
//    private void checkShutdownAccess() {
//        SecurityManager security = System.getSecurityManager();
//        if (security != null) {
//            security.checkPermission(shutdownPerm);
//            final ReentrantLock mainLock = this.mainLock;
//            mainLock.lock();
//            try {
//                for (Worker w : workers)
//                    security.checkAccess(w.thread);
//            } finally {
//                mainLock.unlock();
//            }
//        }
//    }
//
//    /**
//     * Interrupts all threads, even if active. Ignores SecurityExceptions
//     * (in which case some threads may remain uninterrupted).
//     */
//    private void interruptWorkers() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (Worker w : workers)
//                w.interruptIfStarted();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Interrupts threads that might be waiting for tasks (as
//     * indicated by not being locked) so they can check for
//     * termination or configuration changes. Ignores
//     * SecurityExceptions (in which case some threads may remain
//     * uninterrupted).
//     *
//     * @param onlyOne If true, interrupt at most one worker. This is
//     * called only from tryTerminate when termination is otherwise
//     * enabled but there are still other workers.  In this case, at
//     * most one waiting worker is interrupted to propagate shutdown
//     * signals in case all threads are currently waiting.
//     * Interrupting any arbitrary thread ensures that newly arriving
//     * workers since shutdown began will also eventually exit.
//     * To guarantee eventual termination, it suffices to always
//     * interrupt only one idle worker, but shutdown() interrupts all
//     * idle workers so that redundant workers exit promptly, not
//     * waiting for a straggler task to finish.
//     */
//    private void interruptIdleWorkers(boolean onlyOne) {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (Worker w : workers) {
//                Thread t = w.thread;
//                if (!t.isInterrupted() && w.tryLock()) {
//                    try {
//                        t.interrupt();
//                    } catch (SecurityException ignore) {
//                    } finally {
//                        w.unlock();
//                    }
//                }
//                if (onlyOne)
//                    break;
//            }
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Common form of interruptIdleWorkers, to avoid having to
//     * remember what the boolean argument means.
//     */
//    private void interruptIdleWorkers() {
//        interruptIdleWorkers(false);
//    }
//
//    private static final boolean ONLY_ONE = true;
//
//    /*
//     * Misc utilities, most of which are also exported to
//     * ScheduledThreadPoolExecutor
//     */
//
//    /**
//     * Invokes the rejected execution handler for the given command.
//     * Package-protected for use by ScheduledThreadPoolExecutor.
//     */
//    final void reject(Runnable command) {
//        handler.rejectedExecution(command, this);
//    }
//
//    /**
//     * Performs any further cleanup following run state transition on
//     * invocation of shutdown.  A no-op here, but used by
//     * ScheduledThreadPoolExecutor to cancel delayed tasks.
//     */
//    void onShutdown() {
//    }
//
//    /**
//     * State check needed by ScheduledThreadPoolExecutor to
//     * enable running tasks during shutdown.
//     *
//     * @param shutdownOK true if should return true if SHUTDOWN
//     */
//    final boolean isRunningOrShutdown(boolean shutdownOK) {
//        int rs = runStateOf(ctl.get());
//        return rs == RUNNING || (rs == SHUTDOWN && shutdownOK);
//    }
//
//    /**
//     * Drains the task queue into a new list, normally using
//     * drainTo. But if the queue is a DelayQueue or any other kind of
//     * queue for which poll or drainTo may fail to remove some
//     * elements, it deletes them one by one.
//     */
//    private List<Runnable> drainQueue() {
//        BlockingQueue<Runnable> q = workQueue;
//        ArrayList<Runnable> taskList = new ArrayList<Runnable>();
//        q.drainTo(taskList);
//        if (!q.isEmpty()) {
//            for (Runnable r : q.toArray(new Runnable[0])) {
//                if (q.remove(r))
//                    taskList.add(r);
//            }
//        }
//        return taskList;
//    }
//
//    /*
//     * Methods for creating, running and cleaning up after workers
//     */
//
//    /**
//     * Checks if a new worker can be added with respect to current
//     * pool state and the given bound (either core or maximum). If so,
//     * the worker count is adjusted accordingly, and, if possible, a
//     * new worker is created and started, running firstTask as its
//     * first task. This method returns false if the pool is stopped or
//     * eligible to shut down. It also returns false if the thread
//     * factory fails to create a thread when asked.  If the thread
//     * creation fails, either due to the thread factory returning
//     * null, or due to an exception (typically OutOfMemoryError in
//     * Thread.start()), we roll back cleanly.
//     *
//     * @param firstTask the task the new thread should run first (or
//     * null if none). Workers are created with an initial first task
//     * (in method execute()) to bypass queuing when there are fewer
//     * than corePoolSize threads (in which case we always start one),
//     * or when the queue is full (in which case we must bypass queue).
//     * Initially idle threads are usually created via
//     * prestartCoreThread or to replace other dying workers.
//     *
//     * @param core if true use corePoolSize as bound, else
//     * maximumPoolSize. (A boolean indicator is used here rather than a
//     * value to ensure reads of fresh values after checking other pool
//     * state).
//     * @return true if successful
//     */
//
//    // firstTask 即要提交的runnable，是可以null的
//    // core 代表是否核心线程，刚才我们分析其实是当前worker数量<corePoolSize
//    // 因为我们的worker也没有标记是否core的变量
//    // 另外就是addWorker是多线程的，我们这里肯定要判断，core=true，就是以corePoolSize为边界
//    // 如果是false，那就是以maximumPoolSize为边界
//    private boolean addWorker(Runnable firstTask, boolean core) {
//        retry:
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // 这里的逻辑是不创建worker的条件
//            // 1. 线程池状态>=SHUTDOWN （STOP, TIDYING, TERMINATED）
//            // 2. 不能是 （线程池SHUTDOWN的状态 + firstTask==null + 任务队列为空）这个状态，也就是这个状态的话是允许创建worker的
//            // 2是什么意思呢，SHUTDOWN的意思是：不能再提交新的任务了，但是当前任务队列里的task是要执行完的，所以允许添加firstTask==null的worker
//            // 什么时候firstTask==null呢，其实就是execute方法里double check woker数量那里
//            // 如果刚那里理解的话这里还是比较容易理解的
//            if (rs >= SHUTDOWN &&
//                ! (rs == SHUTDOWN &&
//                   firstTask == null &&
//                   ! workQueue.isEmpty()))
//                return false;
//            //这里就是无限循环add了
//            for (;;) {
//                int wc = workerCountOf(c);
//                // 这里就是刚讲的core的含义，使用哪个边界
//                if (wc >= CAPACITY ||
//                    wc >= (core ? corePoolSize : maximumPoolSize))
//                    return false;
//                // 到这里就说明worker数量满足条件
//                // 执行一次 CAS，成功就跳出最外面的循环了
//                // CAS 失败了，说明是其他的线程 CAS成功了 要重试
//                if (compareAndIncrementWorkerCount(c))
//                    break retry;
//                c = ctl.get();  // Re-read ctl
//                // 如果当前线程池的状态变了，则执行外层的循环
//                if (runStateOf(c) != rs)
//                    continue retry;
//                // else CAS failed due to workerCount change; retry inner loop
//                // 如果是因为其他线程导致的CAS失败，则执行内层循环
//            }
//        }
//
//        // 到这里，就说明符合条件，可以addWorker了
//
//        // worker是否执行了start
//        boolean workerStarted = false;
//        // worker有没有被add到 上面说的 workers HashSet
//        boolean workerAdded = false;
//        Worker w = null;
//        try {
//            // 传入firstTask new一个Worker
//            w = new Worker(firstTask);
//            // worker的thread成员变量，用于执行start方法
//            // 刚才讲的，Thread是用ThreadFactory创建的
//            final Thread t = w.thread;
//            if (t != null) {
//                // 刚才说了，对workers的操作要进行加锁
//                // shutdown线程池也是要加锁的，这里我们这个worker还没加到HashSet，如果我们这里获取到锁，则一定在shutdown之前add成功
//                // 如果是shutdown执行完之后再执行这里，也是可以的，shutdown操作的是workers HashSet里的worker，我们这里的还没add进去
//                final ReentrantLock mainLock = this.mainLock;
//                mainLock.lock();
//                try {
//                    // double check 线程池状态
//                    int rs = runStateOf(ctl.get());
//
//                    // 小于SHUTDOWN就是RUNNING，正常情况
//                    // SHUNTDOWN且firstTask==null 就是刚才讲的情况，不接受新任务，但得保证workQueue的执行完
//                    if (rs < SHUTDOWN ||
//                        (rs == SHUTDOWN && firstTask == null)) {
//                        // t不能是alive的
//                        // 什么意思呢？你在ThreadFactory里把线程start了就懂了 哈哈
//                        if (t.isAlive()) // precheck that t is startable
//                            throw new IllegalThreadStateException();
//                        // 添加到HashSet
//                        workers.add(w);
//                        // 这里就是用来记录达到最大的worker数量了
//                        int s = workers.size();
//                        if (s > largestPoolSize)
//                            largestPoolSize = s;
//                        workerAdded = true;
//                    }
//                } finally {
//                    mainLock.unlock();
//                }
//
//                // 成功添加了worker，Worker的thread start，执行的是ThreadPoolExecutor的runWorker方法
//                if (workerAdded) {
//                    t.start();
//                    workerStarted = true;
//                }
//            }
//        } finally {
//            // 如果worker启动失败了，执行addWorkerFailed
//            if (! workerStarted)
//                addWorkerFailed(w);
//        }
//        return workerStarted;
//    }
//
//    /**
//     * Rolls back the worker thread creation.
//     * - removes worker from workers, if present
//     * - decrements worker count
//     * - rechecks for termination, in case the existence of this
//     *   worker was holding up termination
//     */
//    private void addWorkerFailed(Worker w) {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            if (w != null)
//                workers.remove(w);
//            decrementWorkerCount();
//            tryTerminate();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Performs cleanup and bookkeeping for a dying worker. Called
//     * only from worker threads. Unless completedAbruptly is set,
//     * assumes that workerCount has already been adjusted to account
//     * for exit.  This method removes thread from worker set, and
//     * possibly terminates the pool or replaces the worker if either
//     * it exited due to user task exception or if fewer than
//     * corePoolSize workers are running or queue is non-empty but
//     * there are no workers.
//     *
//     * @param w the worker
//     * @param completedAbruptly if the worker died due to user exception
//     */
//    private void processWorkerExit(Worker w, boolean completedAbruptly) {
//        // 如果是异常导致的退出，那么是没执行ctl的worker数量-1的
//        // （另外一种情况是getTask返回null，执行了worker数量-1）
//        if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
//            decrementWorkerCount();
//
//        //对worker的操作都要加锁
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            // 线程池完成任务数量 累加worker完成的量
//            completedTaskCount += w.completedTasks;
//            // 把worker从HashSet remove掉
//            workers.remove(w);
//        } finally {
//            mainLock.unlock();
//        }
//        // 尝试终止，只是尝试而已，如果是RUNNING，直接return。和线程池shutdown等方法有关，有兴趣的读者可以自行看下。
//        tryTerminate();
//
//        int c = ctl.get();
//
//        // 如果是RUNNING 或者 SHUTDOWN状态
//        if (runStateLessThan(c, STOP)) {
//            // 不是因为异常退出的（就是返回null的）
//            if (!completedAbruptly) {
//                // min值 最小线程数的check
//                // allowCoreThreadTimeOut为true的话是0，否则是corePoolSize，
//                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
//
//                // min = 0，当前任务队列不是空的话 0->1
//                if (min == 0 && ! workQueue.isEmpty())
//                    min = 1;
//
//                // 如果当前work >= min 返回
//                // 如果此时 min=1 ，worker数量为0，就会走到下面加一个worker
//                if (workerCountOf(c) >= min)
//                    return; // replacement not needed
//            }
//
//            //异常退出的 直接加一个worker
//            addWorker(null, false);
//        }
//    }
//
//    /**
//     * Performs blocking or timed wait for a task, depending on
//     * current configuration settings, or returns null if this worker
//     * must exit because of any of:
//     * 1. There are more than maximumPoolSize workers (due to
//     *    a call to setMaximumPoolSize).
//     * 2. The pool is stopped.
//     * 3. The pool is shutdown and the queue is empty.
//     * 4. This worker timed out waiting for a task, and timed-out
//     *    workers are subject to termination (that is,
//     *    {@code allowCoreThreadTimeOut || workerCount > corePoolSize})
//     *    both before and after the timed wait, and if the queue is
//     *    non-empty, this worker is not the last thread in the pool.
//     *
//     * @return task, or null if the worker must exit, in which case
//     *         workerCount is decremented
//     */
//    // 这个方法有以下情况  前4种都是返回null让worker退出
//    // 1. 通过调用setMaximumPoolSize使超出了maximumPoolSize
//    // 2. 线程池STOP状态，STOP状态任务队列里的也要丢弃
//    // 3. SHUTDOWN且queue是空的，这个应该懂了吧！
//    // 4. 超时退出。allowCoreThreadTimeOut || workerCount > corePoolSize
//    // 5. 阻塞直到获取到任务返回。< corePoolSize
//    private Runnable getTask() {
//        boolean timedOut = false; // Did the last poll() time out?
//
//
//        //无限循环
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // Check if queue empty only if necessary.
//            // 这里是上面的2和3
//            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
//                //CAS 减少worker数量，是改的ctl，返回null后，worker会执行exit方法
//                decrementWorkerCount();
//                return null;
//            }
//            // 获取worker数量
//            int wc = workerCountOf(c);
//            // worker是否可以超过， set allowCoreThreadTimeOut=true 或者 当前worker > corePoolSize 都可以超时
//            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
//
//            // 这里是1和4
//            // 第二个条件是什么意思呢？就是如果你当前worker数量=0，任务队列还不是空的话，是不能返回null的。
//            // timedOut的值，最开始设置了是false，所以就算timed=true，第一次进来是直接跳过这里的
//            if ((wc > maximumPoolSize || (timed && timedOut))
//                && (wc > 1 || workQueue.isEmpty())) {
//                if (compareAndDecrementWorkerCount(c))
//                    return null;
//                //这里是CAS减少worker数量失败了的重试
//                continue;
//            }
//
//            try {
//                // 这里就是根据是否允许超时，选用两个方法
//                // 允许超时，则用keepAliveTime做超时时间，如果当前任务队列没有任务，超过这个时间就返回null了
//                // 不允许超时，则当前worker数量< corePoolSize take会一直阻塞住，直到拿到runnable
//                Runnable r = timed ?
//                    workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
//                    workQueue.take();
//                // 表明拿到了runnable，返回执行runTask
//                if (r != null)
//                    return r;
//                // 表明是发生了超时返回的null 进入下一次循环
//                timedOut = true;
//            } catch (InterruptedException retry) {
//                // 表明线程被中断了，把timedOut设置为false重试
//                timedOut = false;
//            }
//        }
//    }
//
//    /**
//     * Main worker run loop.  Repeatedly gets tasks from queue and
//     * executes them, while coping with a number of issues:
//     *
//     * 1. We may start out with an initial task, in which case we
//     * don't need to get the first one. Otherwise, as long as pool is
//     * running, we get tasks from getTask. If it returns null then the
//     * worker exits due to changed pool state or configuration
//     * parameters.  Other exits result from exception throws in
//     * external code, in which case completedAbruptly holds, which
//     * usually leads processWorkerExit to replace this thread.
//     *
//     * 2. Before running any task, the lock is acquired to prevent
//     * other pool interrupts while the task is executing, and then we
//     * ensure that unless pool is stopping, this thread does not have
//     * its interrupt set.
//     *
//     * 3. Each task run is preceded by a call to beforeExecute, which
//     * might throw an exception, in which case we cause thread to die
//     * (breaking loop with completedAbruptly true) without processing
//     * the task.
//     *
//     * 4. Assuming beforeExecute completes normally, we run the task,
//     * gathering any of its thrown exceptions to send to afterExecute.
//     * We separately handle RuntimeException, Error (both of which the
//     * specs guarantee that we trap) and arbitrary Throwables.
//     * Because we cannot rethrow Throwables within Runnable.run, we
//     * wrap them within Errors on the way out (to the thread's
//     * UncaughtExceptionHandler).  Any thrown exception also
//     * conservatively causes thread to die.
//     *
//     * 5. After task.run completes, we call afterExecute, which may
//     * also throw an exception, which will also cause thread to
//     * die. According to JLS Sec 14.20, this exception is the one that
//     * will be in effect even if task.run throws.
//     *
//     * The net effect of the exception mechanics is that afterExecute
//     * and the thread's UncaughtExceptionHandler have as accurate
//     * information as we can provide about any problems encountered by
//     * user code.
//     *
//     * @param w the worker
//     */
//    final void runWorker(Worker w) {
//        Thread wt = Thread.currentThread();
//        Runnable task = w.firstTask;
//        //help gc
//        w.firstTask = null;
//        // 这里unlock是因为，new的Worker AQS state=-1，前面讲了必须是0才能加锁，而且0也可以被中断。
//        w.unlock(); // allow interrupts
//        // 代表被因为异常退出的
//        boolean completedAbruptly = true;
//        try {
//            //无限循环 ，task 不为null则run task，如果task是null则调用 getTask 获取任务
//            while (task != null || (task = getTask()) != null) {
//                w.lock();
//
//                // 如果线程池>=STOP，确保线程被中断。如果不是，确保没有被中断。
//                // 第二个条件要重新判断中断状态，处理shutdownNow清理中断的race
//                if ((runStateAtLeast(ctl.get(), STOP) ||
//                     (Thread.interrupted() &&
//                      runStateAtLeast(ctl.get(), STOP))) &&
//                    !wt.isInterrupted())
//                    wt.interrupt();
//                try {
//                    // 空实现，在runTask前，用于继承时自己实现
//                    beforeExecute(wt, task);
//                    // 捕捉的run方法的Throwable
//                    Throwable thrown = null;
//                    try {
//                        task.run();
//                    } catch (RuntimeException x) {
//                        thrown = x; throw x;
//                    } catch (Error x) {
//                        thrown = x; throw x;
//                    } catch (Throwable x) {
//                        thrown = x; throw new Error(x);
//                    } finally {
//                        // 也是空实现，在runTask后
//                        afterExecute(task, thrown);
//                    }
//                } finally {
//                    // task置null，继续下次循环
//                    task = null;
//                    w.completedTasks++;
//                    w.unlock();
//                }
//            }
//
//            // 执行到这里说明不是因为发生异常执行的finally
//            completedAbruptly = false;
//        } finally {
//
//            //执行到finally 说明
//            // 1. getTask返回null了 退出了循环
//            // 2. runTask的时候发生异常被中断
//            processWorkerExit(w, completedAbruptly);
//        }
//    }
//
//    // Public constructors and methods
//
//    /**
//     * Creates a new {@code ThreadPoolExecutor} with the given initial
//     * parameters and default thread factory and rejected execution handler.
//     * It may be more convenient to use one of the {@link Executors} factory
//     * methods instead of this general purpose constructor.
//     *
//     * @param corePoolSize the number of threads to keep in the pool, even
//     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
//     * @param maximumPoolSize the maximum number of threads to allow in the
//     *        pool
//     * @param keepAliveTime when the number of threads is greater than
//     *        the core, this is the maximum time that excess idle threads
//     *        will wait for new tasks before terminating.
//     * @param unit the time unit for the {@code keepAliveTime} argument
//     * @param workQueue the queue to use for holding tasks before they are
//     *        executed.  This queue will hold only the {@code Runnable}
//     *        tasks submitted by the {@code execute} method.
//     * @throws IllegalArgumentException if one of the following holds:<br>
//     *         {@code corePoolSize < 0}<br>
//     *         {@code keepAliveTime < 0}<br>
//     *         {@code maximumPoolSize <= 0}<br>
//     *         {@code maximumPoolSize < corePoolSize}
//     * @throws NullPointerException if {@code workQueue} is null
//     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//             Executors.defaultThreadFactory(), defaultHandler);
//    }
//
//    /**
//     * Creates a new {@code ThreadPoolExecutor} with the given initial
//     * parameters and default rejected execution handler.
//     *
//     * @param corePoolSize the number of threads to keep in the pool, even
//     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
//     * @param maximumPoolSize the maximum number of threads to allow in the
//     *        pool
//     * @param keepAliveTime when the number of threads is greater than
//     *        the core, this is the maximum time that excess idle threads
//     *        will wait for new tasks before terminating.
//     * @param unit the time unit for the {@code keepAliveTime} argument
//     * @param workQueue the queue to use for holding tasks before they are
//     *        executed.  This queue will hold only the {@code Runnable}
//     *        tasks submitted by the {@code execute} method.
//     * @param threadFactory the factory to use when the executor
//     *        creates a new thread
//     * @throws IllegalArgumentException if one of the following holds:<br>
//     *         {@code corePoolSize < 0}<br>
//     *         {@code keepAliveTime < 0}<br>
//     *         {@code maximumPoolSize <= 0}<br>
//     *         {@code maximumPoolSize < corePoolSize}
//     * @throws NullPointerException if {@code workQueue}
//     *         or {@code threadFactory} is null
//     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//             threadFactory, defaultHandler);
//    }
//
//    /**
//     * Creates a new {@code ThreadPoolExecutor} with the given initial
//     * parameters and default thread factory.
//     *
//     * @param corePoolSize the number of threads to keep in the pool, even
//     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
//     * @param maximumPoolSize the maximum number of threads to allow in the
//     *        pool
//     * @param keepAliveTime when the number of threads is greater than
//     *        the core, this is the maximum time that excess idle threads
//     *        will wait for new tasks before terminating.
//     * @param unit the time unit for the {@code keepAliveTime} argument
//     * @param workQueue the queue to use for holding tasks before they are
//     *        executed.  This queue will hold only the {@code Runnable}
//     *        tasks submitted by the {@code execute} method.
//     * @param handler the handler to use when execution is blocked
//     *        because the thread bounds and queue capacities are reached
//     * @throws IllegalArgumentException if one of the following holds:<br>
//     *         {@code corePoolSize < 0}<br>
//     *         {@code keepAliveTime < 0}<br>
//     *         {@code maximumPoolSize <= 0}<br>
//     *         {@code maximumPoolSize < corePoolSize}
//     * @throws NullPointerException if {@code workQueue}
//     *         or {@code handler} is null
//     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              RejectedExecutionHandler handler) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//             Executors.defaultThreadFactory(), handler);
//    }
//
//    /**
//     * Creates a new {@code ThreadPoolExecutor} with the given initial
//     * parameters.
//     *
//     * @param corePoolSize the number of threads to keep in the pool, even
//     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
//     * @param maximumPoolSize the maximum number of threads to allow in the
//     *        pool
//     * @param keepAliveTime when the number of threads is greater than
//     *        the core, this is the maximum time that excess idle threads
//     *        will wait for new tasks before terminating.
//     * @param unit the time unit for the {@code keepAliveTime} argument
//     * @param workQueue the queue to use for holding tasks before they are
//     *        executed.  This queue will hold only the {@code Runnable}
//     *        tasks submitted by the {@code execute} method.
//     * @param threadFactory the factory to use when the executor
//     *        creates a new thread
//     * @param handler the handler to use when execution is blocked
//     *        because the thread bounds and queue capacities are reached
//     * @throws IllegalArgumentException if one of the following holds:<br>
//     *         {@code corePoolSize < 0}<br>
//     *         {@code keepAliveTime < 0}<br>
//     *         {@code maximumPoolSize <= 0}<br>
//     *         {@code maximumPoolSize < corePoolSize}
//     * @throws NullPointerException if {@code workQueue}
//     *         or {@code threadFactory} or {@code handler} is null
//     */
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler) {
//        if (corePoolSize < 0 ||
//            maximumPoolSize <= 0 ||
//            maximumPoolSize < corePoolSize ||
//            keepAliveTime < 0)
//            throw new IllegalArgumentException();
//        if (workQueue == null || threadFactory == null || handler == null)
//            throw new NullPointerException();
//        this.corePoolSize = corePoolSize;
//        this.maximumPoolSize = maximumPoolSize;
//        this.workQueue = workQueue;
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        this.threadFactory = threadFactory;
//        this.handler = handler;
//    }
//
//    /**
//     * Executes the given task sometime in the future.  The task
//     * may execute in a new thread or in an existing pooled thread.
//     *
//     * If the task cannot be submitted for execution, either because this
//     * executor has been shutdown or because its capacity has been reached,
//     * the task is handled by the current {@code RejectedExecutionHandler}.
//     *
//     * @param command the task to execute
//     * @throws RejectedExecutionException at discretion of
//     *         {@code RejectedExecutionHandler}, if the task
//     *         cannot be accepted for execution
//     * @throws NullPointerException if {@code command} is null
//     */
//    public void execute(Runnable command) {
//        if (command == null)
//            throw new NullPointerException();
//        /*
//         * Proceed in 3 steps:
//         *
//         * 1. If fewer than corePoolSize threads are running, try to
//         * start a new thread with the given command as its first
//         * task.  The call to addWorker atomically checks runState and
//         * workerCount, and so prevents false alarms that would add
//         * threads when it shouldn't, by returning false.
//         *
//         * 2. If a task can be successfully queued, then we still need
//         * to double-check whether we should have added a thread
//         * (because existing ones died since last checking) or that
//         * the pool shut down since entry into this method. So we
//         * recheck state and if necessary roll back the enqueuing if
//         * stopped, or start a new thread if there are none.
//         *
//         * 3. If we cannot queue task, then we try to add a new
//         * thread.  If it fails, we know we are shut down or saturated
//         * and so reject the task.
//         */
//        // 前面讲了，可以得到线程池状态以及worker数量
//        int c = ctl.get();
//        // 当前worker数量小于corePoolSize，则执行addWorker增加核心worker线程执行runnable
//        // 传入的runnable作为worker的firstTask，添加成功则worker +1 ，并返回。
//        // 如果添加失败，addWorker返回了fase，表明可能已经被其他线程增加到corePoolSize了，也可能是没有start成功，以及线程池SHUTDOWN了等，则继续往下执行。
//        if (workerCountOf(c) < corePoolSize) {
//            if (addWorker(command, true))
//                return;
//            c = ctl.get();
//        }
//        // 到这里表明增加核心线程失败了
//
//        // 如果线程池是RUNNING状态，则把这个runnable放入阻塞任务队列
//        // （只有RUNNING状态，才能提交新任务入队列）
//        if (isRunning(c) && workQueue.offer(command)) {
//            // 到这里是添加到任务队列成功，表明任务队列还没满
//            int recheck = ctl.get();
//
//            // 线程池状态的double check，如果线程池不是RUNNING状态则把这个task从任务队列移除
//            // 并执行拒绝策略
//            if (! isRunning(recheck) && remove(command))
//                reject(command);
//            // 线程数的double check，如果当前线程数为0，则addWorker
//            // 什么时候为0呢，两种情况，corePoolSize=0 或 allowCoreThreadTimeOut = true
//            // 可以将上面例子的corePoolSize改为0，你会发现main执行完就退出了
//            // (看完全文思考为什么corePoolSize>0 的时候，main不会退出)
//            else if (workerCountOf(recheck) == 0)
//                addWorker(null, false);
//        }
//
//        // 到这里是因为上面任务队列满了添加失败了，执行添加非核心的worker
//        // 如果添加失败，则是因为达到了maximumPoolSize，则执行拒绝策略
//        // 拒绝策略就是我们在构造器传入的handler，我们例子do nothing 其实就是线程池默认的拒绝策略
//        // 拒绝策略的内容就不细讲了，只是几个内部类
//        else if (!addWorker(command, false))
//            reject(command);
//    }
//
//    /**
//     * Initiates an orderly shutdown in which previously submitted
//     * tasks are executed, but no new tasks will be accepted.
//     * Invocation has no additional effect if already shut down.
//     *
//     * <p>This method does not wait for previously submitted tasks to
//     * complete execution.  Use {@link #awaitTermination awaitTermination}
//     * to do that.
//     *
//     * @throws SecurityException {@inheritDoc}
//     */
//    public void shutdown() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            checkShutdownAccess();
//            advanceRunState(SHUTDOWN);
//            interruptIdleWorkers();
//            onShutdown(); // hook for ScheduledThreadPoolExecutor
//        } finally {
//            mainLock.unlock();
//        }
//        tryTerminate();
//    }
//
//    /**
//     * Attempts to stop all actively executing tasks, halts the
//     * processing of waiting tasks, and returns a list of the tasks
//     * that were awaiting execution. These tasks are drained (removed)
//     * from the task queue upon return from this method.
//     *
//     * <p>This method does not wait for actively executing tasks to
//     * terminate.  Use {@link #awaitTermination awaitTermination} to
//     * do that.
//     *
//     * <p>There are no guarantees beyond best-effort attempts to stop
//     * processing actively executing tasks.  This implementation
//     * cancels tasks via {@link Thread#interrupt}, so any task that
//     * fails to respond to interrupts may never terminate.
//     *
//     * @throws SecurityException {@inheritDoc}
//     */
//    public List<Runnable> shutdownNow() {
//        List<Runnable> tasks;
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            checkShutdownAccess();
//            advanceRunState(STOP);
//            interruptWorkers();
//            tasks = drainQueue();
//        } finally {
//            mainLock.unlock();
//        }
//        tryTerminate();
//        return tasks;
//    }
//
//    public boolean isShutdown() {
//        return ! isRunning(ctl.get());
//    }
//
//    /**
//     * Returns true if this executor is in the process of terminating
//     * after {@link #shutdown} or {@link #shutdownNow} but has not
//     * completely terminated.  This method may be useful for
//     * debugging. A return of {@code true} reported a sufficient
//     * period after shutdown may indicate that submitted tasks have
//     * ignored or suppressed interruption, causing this executor not
//     * to properly terminate.
//     *
//     * @return {@code true} if terminating but not yet terminated
//     */
//    public boolean isTerminating() {
//        int c = ctl.get();
//        return ! isRunning(c) && runStateLessThan(c, TERMINATED);
//    }
//
//    public boolean isTerminated() {
//        return runStateAtLeast(ctl.get(), TERMINATED);
//    }
//
//    public boolean awaitTermination(long timeout, TimeUnit unit)
//        throws InterruptedException {
//        long nanos = unit.toNanos(timeout);
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (;;) {
//                if (runStateAtLeast(ctl.get(), TERMINATED))
//                    return true;
//                if (nanos <= 0)
//                    return false;
//                nanos = termination.awaitNanos(nanos);
//            }
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Invokes {@code shutdown} when this executor is no longer
//     * referenced and it has no threads.
//     */
//    protected void finalize() {
//        shutdown();
//    }
//
//    /**
//     * Sets the thread factory used to create new threads.
//     *
//     * @param threadFactory the new thread factory
//     * @throws NullPointerException if threadFactory is null
//     * @see #getThreadFactory
//     */
//    public void setThreadFactory(ThreadFactory threadFactory) {
//        if (threadFactory == null)
//            throw new NullPointerException();
//        this.threadFactory = threadFactory;
//    }
//
//    /**
//     * Returns the thread factory used to create new threads.
//     *
//     * @return the current thread factory
//     * @see #setThreadFactory(ThreadFactory)
//     */
//    public ThreadFactory getThreadFactory() {
//        return threadFactory;
//    }
//
//    /**
//     * Sets a new handler for unexecutable tasks.
//     *
//     * @param handler the new handler
//     * @throws NullPointerException if handler is null
//     * @see #getRejectedExecutionHandler
//     */
//    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
//        if (handler == null)
//            throw new NullPointerException();
//        this.handler = handler;
//    }
//
//    /**
//     * Returns the current handler for unexecutable tasks.
//     *
//     * @return the current handler
//     * @see #setRejectedExecutionHandler(RejectedExecutionHandler)
//     */
//    public RejectedExecutionHandler getRejectedExecutionHandler() {
//        return handler;
//    }
//
//    /**
//     * Sets the core number of threads.  This overrides any value set
//     * in the constructor.  If the new value is smaller than the
//     * current value, excess existing threads will be terminated when
//     * they next become idle.  If larger, new threads will, if needed,
//     * be started to execute any queued tasks.
//     *
//     * @param corePoolSize the new core size
//     * @throws IllegalArgumentException if {@code corePoolSize < 0}
//     * @see #getCorePoolSize
//     */
//    public void setCorePoolSize(int corePoolSize) {
//        if (corePoolSize < 0)
//            throw new IllegalArgumentException();
//        int delta = corePoolSize - this.corePoolSize;
//        this.corePoolSize = corePoolSize;
//        if (workerCountOf(ctl.get()) > corePoolSize)
//            interruptIdleWorkers();
//        else if (delta > 0) {
//            // We don't really know how many new threads are "needed".
//            // As a heuristic, prestart enough new workers (up to new
//            // core size) to handle the current number of tasks in
//            // queue, but stop if queue becomes empty while doing so.
//            int k = Math.min(delta, workQueue.size());
//            while (k-- > 0 && addWorker(null, true)) {
//                if (workQueue.isEmpty())
//                    break;
//            }
//        }
//    }
//
//    /**
//     * Returns the core number of threads.
//     *
//     * @return the core number of threads
//     * @see #setCorePoolSize
//     */
//    public int getCorePoolSize() {
//        return corePoolSize;
//    }
//
//    /**
//     * Starts a core thread, causing it to idly wait for work. This
//     * overrides the default policy of starting core threads only when
//     * new tasks are executed. This method will return {@code false}
//     * if all core threads have already been started.
//     *
//     * @return {@code true} if a thread was started
//     */
//    public boolean prestartCoreThread() {
//        return workerCountOf(ctl.get()) < corePoolSize &&
//            addWorker(null, true);
//    }
//
//    /**
//     * Same as prestartCoreThread except arranges that at least one
//     * thread is started even if corePoolSize is 0.
//     */
//    void ensurePrestart() {
//        int wc = workerCountOf(ctl.get());
//        if (wc < corePoolSize)
//            addWorker(null, true);
//        else if (wc == 0)
//            addWorker(null, false);
//    }
//
//    /**
//     * Starts all core threads, causing them to idly wait for work. This
//     * overrides the default policy of starting core threads only when
//     * new tasks are executed.
//     *
//     * @return the number of threads started
//     */
//    public int prestartAllCoreThreads() {
//        int n = 0;
//        while (addWorker(null, true))
//            ++n;
//        return n;
//    }
//
//    /**
//     * Returns true if this pool allows core threads to time out and
//     * terminate if no tasks arrive within the keepAlive time, being
//     * replaced if needed when new tasks arrive. When true, the same
//     * keep-alive policy applying to non-core threads applies also to
//     * core threads. When false (the default), core threads are never
//     * terminated due to lack of incoming tasks.
//     *
//     * @return {@code true} if core threads are allowed to time out,
//     *         else {@code false}
//     *
//     * @since 1.6
//     */
//    public boolean allowsCoreThreadTimeOut() {
//        return allowCoreThreadTimeOut;
//    }
//
//    /**
//     * Sets the policy governing whether core threads may time out and
//     * terminate if no tasks arrive within the keep-alive time, being
//     * replaced if needed when new tasks arrive. When false, core
//     * threads are never terminated due to lack of incoming
//     * tasks. When true, the same keep-alive policy applying to
//     * non-core threads applies also to core threads. To avoid
//     * continual thread replacement, the keep-alive time must be
//     * greater than zero when setting {@code true}. This method
//     * should in general be called before the pool is actively used.
//     *
//     * @param value {@code true} if should time out, else {@code false}
//     * @throws IllegalArgumentException if value is {@code true}
//     *         and the current keep-alive time is not greater than zero
//     *
//     * @since 1.6
//     */
//    public void allowCoreThreadTimeOut(boolean value) {
//        if (value && keepAliveTime <= 0)
//            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
//        if (value != allowCoreThreadTimeOut) {
//            allowCoreThreadTimeOut = value;
//            if (value)
//                interruptIdleWorkers();
//        }
//    }
//
//    /**
//     * Sets the maximum allowed number of threads. This overrides any
//     * value set in the constructor. If the new value is smaller than
//     * the current value, excess existing threads will be
//     * terminated when they next become idle.
//     *
//     * @param maximumPoolSize the new maximum
//     * @throws IllegalArgumentException if the new maximum is
//     *         less than or equal to zero, or
//     *         less than the {@linkplain #getCorePoolSize core pool size}
//     * @see #getMaximumPoolSize
//     */
//    public void setMaximumPoolSize(int maximumPoolSize) {
//        if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize)
//            throw new IllegalArgumentException();
//        this.maximumPoolSize = maximumPoolSize;
//        if (workerCountOf(ctl.get()) > maximumPoolSize)
//            interruptIdleWorkers();
//    }
//
//    /**
//     * Returns the maximum allowed number of threads.
//     *
//     * @return the maximum allowed number of threads
//     * @see #setMaximumPoolSize
//     */
//    public int getMaximumPoolSize() {
//        return maximumPoolSize;
//    }
//
//    /**
//     * Sets the time limit for which threads may remain idle before
//     * being terminated.  If there are more than the core number of
//     * threads currently in the pool, after waiting this amount of
//     * time without processing a task, excess threads will be
//     * terminated.  This overrides any value set in the constructor.
//     *
//     * @param time the time to wait.  A time value of zero will cause
//     *        excess threads to terminate immediately after executing tasks.
//     * @param unit the time unit of the {@code time} argument
//     * @throws IllegalArgumentException if {@code time} less than zero or
//     *         if {@code time} is zero and {@code allowsCoreThreadTimeOut}
//     * @see #getKeepAliveTime(TimeUnit)
//     */
//    public void setKeepAliveTime(long time, TimeUnit unit) {
//        if (time < 0)
//            throw new IllegalArgumentException();
//        if (time == 0 && allowsCoreThreadTimeOut())
//            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
//        long keepAliveTime = unit.toNanos(time);
//        long delta = keepAliveTime - this.keepAliveTime;
//        this.keepAliveTime = keepAliveTime;
//        if (delta < 0)
//            interruptIdleWorkers();
//    }
//
//    /**
//     * Returns the thread keep-alive time, which is the amount of time
//     * that threads in excess of the core pool size may remain
//     * idle before being terminated.
//     *
//     * @param unit the desired time unit of the result
//     * @return the time limit
//     * @see #setKeepAliveTime(long, TimeUnit)
//     */
//    public long getKeepAliveTime(TimeUnit unit) {
//        return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
//    }
//
//    /* User-level queue utilities */
//
//    /**
//     * Returns the task queue used by this executor. Access to the
//     * task queue is intended primarily for debugging and monitoring.
//     * This queue may be in active use.  Retrieving the task queue
//     * does not prevent queued tasks from executing.
//     *
//     * @return the task queue
//     */
//    public BlockingQueue<Runnable> getQueue() {
//        return workQueue;
//    }
//
//    /**
//     * Removes this task from the executor's internal queue if it is
//     * present, thus causing it not to be run if it has not already
//     * started.
//     *
//     * <p>This method may be useful as one part of a cancellation
//     * scheme.  It may fail to remove tasks that have been converted
//     * into other forms before being placed on the internal queue. For
//     * example, a task entered using {@code submit} might be
//     * converted into a form that maintains {@code Future} status.
//     * However, in such cases, method {@link #purge} may be used to
//     * remove those Futures that have been cancelled.
//     *
//     * @param task the task to remove
//     * @return {@code true} if the task was removed
//     */
//    public boolean remove(Runnable task) {
//        boolean removed = workQueue.remove(task);
//        tryTerminate(); // In case SHUTDOWN and now empty
//        return removed;
//    }
//
//    /**
//     * Tries to remove from the work queue all {@link Future}
//     * tasks that have been cancelled. This method can be useful as a
//     * storage reclamation operation, that has no other impact on
//     * functionality. Cancelled tasks are never executed, but may
//     * accumulate in work queues until worker threads can actively
//     * remove them. Invoking this method instead tries to remove them now.
//     * However, this method may fail to remove tasks in
//     * the presence of interference by other threads.
//     */
//    public void purge() {
//        final BlockingQueue<Runnable> q = workQueue;
//        try {
//            Iterator<Runnable> it = q.iterator();
//            while (it.hasNext()) {
//                Runnable r = it.next();
//                if (r instanceof Future<?> && ((Future<?>)r).isCancelled())
//                    it.remove();
//            }
//        } catch (ConcurrentModificationException fallThrough) {
//            // Take slow path if we encounter interference during traversal.
//            // Make copy for traversal and call remove for cancelled entries.
//            // The slow path is more likely to be O(N*N).
//            for (Object r : q.toArray())
//                if (r instanceof Future<?> && ((Future<?>)r).isCancelled())
//                    q.remove(r);
//        }
//
//        tryTerminate(); // In case SHUTDOWN and now empty
//    }
//
//    /* Statistics */
//
//    /**
//     * Returns the current number of threads in the pool.
//     *
//     * @return the number of threads
//     */
//    public int getPoolSize() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            // Remove rare and surprising possibility of
//            // isTerminated() && getPoolSize() > 0
//            return runStateAtLeast(ctl.get(), TIDYING) ? 0
//                : workers.size();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Returns the approximate number of threads that are actively
//     * executing tasks.
//     *
//     * @return the number of threads
//     */
//    public int getActiveCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            int n = 0;
//            for (Worker w : workers)
//                if (w.isLocked())
//                    ++n;
//            return n;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Returns the largest number of threads that have ever
//     * simultaneously been in the pool.
//     *
//     * @return the number of threads
//     */
//    public int getLargestPoolSize() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            return largestPoolSize;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Returns the approximate total number of tasks that have ever been
//     * scheduled for execution. Because the states of tasks and
//     * threads may change dynamically during computation, the returned
//     * value is only an approximation.
//     *
//     * @return the number of tasks
//     */
//    public long getTaskCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            long n = completedTaskCount;
//            for (Worker w : workers) {
//                n += w.completedTasks;
//                if (w.isLocked())
//                    ++n;
//            }
//            return n + workQueue.size();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Returns the approximate total number of tasks that have
//     * completed execution. Because the states of tasks and threads
//     * may change dynamically during computation, the returned value
//     * is only an approximation, but one that does not ever decrease
//     * across successive calls.
//     *
//     * @return the number of tasks
//     */
//    public long getCompletedTaskCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            long n = completedTaskCount;
//            for (Worker w : workers)
//                n += w.completedTasks;
//            return n;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Returns a string identifying this pool, as well as its state,
//     * including indications of run state and estimated worker and
//     * task counts.
//     *
//     * @return a string identifying this pool, as well as its state
//     */
//    public String toString() {
//        long ncompleted;
//        int nworkers, nactive;
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            ncompleted = completedTaskCount;
//            nactive = 0;
//            nworkers = workers.size();
//            for (Worker w : workers) {
//                ncompleted += w.completedTasks;
//                if (w.isLocked())
//                    ++nactive;
//            }
//        } finally {
//            mainLock.unlock();
//        }
//        int c = ctl.get();
//        String rs = (runStateLessThan(c, SHUTDOWN) ? "Running" :
//                     (runStateAtLeast(c, TERMINATED) ? "Terminated" :
//                      "Shutting down"));
//        return super.toString() +
//            "[" + rs +
//            ", pool size = " + nworkers +
//            ", active threads = " + nactive +
//            ", queued tasks = " + workQueue.size() +
//            ", completed tasks = " + ncompleted +
//            "]";
//    }
//
//    /* Extension hooks */
//
//    /**
//     * Method invoked prior to executing the given Runnable in the
//     * given thread.  This method is invoked by thread {@code t} that
//     * will execute task {@code r}, and may be used to re-initialize
//     * ThreadLocals, or to perform logging.
//     *
//     * <p>This implementation does nothing, but may be customized in
//     * subclasses. Note: To properly nest multiple overridings, subclasses
//     * should generally invoke {@code super.beforeExecute} at the end of
//     * this method.
//     *
//     * @param t the thread that will run task {@code r}
//     * @param r the task that will be executed
//     */
//    protected void beforeExecute(Thread t, Runnable r) { }
//
//    /**
//     * Method invoked upon completion of execution of the given Runnable.
//     * This method is invoked by the thread that executed the task. If
//     * non-null, the Throwable is the uncaught {@code RuntimeException}
//     * or {@code Error} that caused execution to terminate abruptly.
//     *
//     * <p>This implementation does nothing, but may be customized in
//     * subclasses. Note: To properly nest multiple overridings, subclasses
//     * should generally invoke {@code super.afterExecute} at the
//     * beginning of this method.
//     *
//     * <p><b>Note:</b> When actions are enclosed in tasks (such as
//     * {@link FutureTask}) either explicitly or via methods such as
//     * {@code submit}, these task objects catch and maintain
//     * computational exceptions, and so they do not cause abrupt
//     * termination, and the internal exceptions are <em>not</em>
//     * passed to this method. If you would like to trap both kinds of
//     * failures in this method, you can further probe for such cases,
//     * as in this sample subclass that prints either the direct cause
//     * or the underlying exception if a task has been aborted:
//     *
//     *  <pre> {@code
//     * class ExtendedExecutor extends ThreadPoolExecutor {
//     *   // ...
//     *   protected void afterExecute(Runnable r, Throwable t) {
//     *     super.afterExecute(r, t);
//     *     if (t == null && r instanceof Future<?>) {
//     *       try {
//     *         Object result = ((Future<?>) r).get();
//     *       } catch (CancellationException ce) {
//     *           t = ce;
//     *       } catch (ExecutionException ee) {
//     *           t = ee.getCause();
//     *       } catch (InterruptedException ie) {
//     *           Thread.currentThread().interrupt(); // ignore/reset
//     *       }
//     *     }
//     *     if (t != null)
//     *       System.out.println(t);
//     *   }
//     * }}</pre>
//     *
//     * @param r the runnable that has completed
//     * @param t the exception that caused termination, or null if
//     * execution completed normally
//     */
//    protected void afterExecute(Runnable r, Throwable t) { }
//
//    /**
//     * Method invoked when the Executor has terminated.  Default
//     * implementation does nothing. Note: To properly nest multiple
//     * overridings, subclasses should generally invoke
//     * {@code super.terminated} within this method.
//     */
//    protected void terminated() { }
//
//    /* Predefined RejectedExecutionHandlers */
//
//    /**
//     * A handler for rejected tasks that runs the rejected task
//     * directly in the calling thread of the {@code execute} method,
//     * unless the executor has been shut down, in which case the task
//     * is discarded.
//     */
//    public static class CallerRunsPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code CallerRunsPolicy}.
//         */
//        public CallerRunsPolicy() { }
//
//        /**
//         * Executes task r in the caller's thread, unless the executor
//         * has been shut down, in which case the task is discarded.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            if (!e.isShutdown()) {
//                r.run();
//            }
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that throws a
//     * {@code RejectedExecutionException}.
//     */
//    public static class AbortPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates an {@code AbortPolicy}.
//         */
//        public AbortPolicy() { }
//
//        /**
//         * Always throws RejectedExecutionException.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         * @throws RejectedExecutionException always
//         */
//        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            throw new RejectedExecutionException("Task " + r.toString() +
//                                                 " rejected from " +
//                                                 e.toString());
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that silently discards the
//     * rejected task.
//     */
//    public static class DiscardPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code DiscardPolicy}.
//         */
//        public DiscardPolicy() { }
//
//        /**
//         * Does nothing, which has the effect of discarding task r.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that discards the oldest unhandled
//     * request and then retries {@code execute}, unless the executor
//     * is shut down, in which case the task is discarded.
//     */
//    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code DiscardOldestPolicy} for the given executor.
//         */
//        public DiscardOldestPolicy() { }
//
//        /**
//         * Obtains and ignores the next task that the executor
//         * would otherwise execute, if one is immediately available,
//         * and then retries execution of task r, unless the executor
//         * is shut down, in which case task r is instead discarded.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            if (!e.isShutdown()) {
//                e.getQueue().poll();
//                e.execute(r);
//            }
//        }
//    }
//}